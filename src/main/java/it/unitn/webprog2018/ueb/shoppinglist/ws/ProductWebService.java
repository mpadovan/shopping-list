/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import com.google.gson.Gson;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CustomGsonBuilder;
import it.unitn.webprog2018.ueb.shoppinglist.utils.ServiceUtils;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.Authentication;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service for Products and Public Products
 *
 * @author Giulia Carocari
 */
@Path("products")
public class ProductWebService {

	@Context
	private UriInfo context;
	@Context
	private ServletContext servletContext;
	@Context
	private HttpServletResponse response;

	/**
	 * Creates a new instance of ProductWebService
	 */
	public ProductWebService() {
	}

	/**
	 * Retrieves representation of instances of
	 * <code>it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct</code>.
	 *
	 * @param search Parameter to filter the results by name
	 * @param compact Parameter to obtain only name and id field of the object
	 * @return an instance of java.lang.String that represents the products in
	 * Json format
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPublicProducts(@QueryParam("search") String search,
			@QueryParam("compact") String compact) {

		String query = ServiceUtils.getQuery(search);
		
		PublicProductDAO publicProductDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getPublicProductDAO();
		List<PublicProduct> publicProducts = null;
		if (query.equals("")) {
			try {
				publicProducts = publicProductDAO.getAll();
			} catch (DaoException ex) {
				ServiceUtils.handleDAOException(ex, response);
			}
		} else {
			try {
				publicProducts = publicProductDAO.getFromQuery(query);
			} catch (DaoException ex) {
				ServiceUtils.handleDAOException(ex, response);
			}
		}
		Gson gson = CustomGsonBuilder.create(compact != null && compact.equals("true"));
		try {
			return (publicProducts == null ? "[]" : gson.toJson(publicProducts));
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves representation of instances of
	 * <code>it.unitn.webprog2018.ueb.shoppinglist.entities.Product</code>.
	 *
	 * @param userId Id of the user that wants to access his custom products
	 * @param search Parameter to filter the results by name
	 * @param compact Parameter to obtain only name and id field of the object
	 * @param privateOnly if value is "true" then returns only custom products
	 *
	 * @return an instance of java.lang.String that represents the products in
	 * Json format
	 */
	@GET
	@Path("/restricted/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Authentication
	public String getProducts(@PathParam("userId") int userId, @QueryParam("search") String search,
			@QueryParam("compact") String compact, @QueryParam("privateOnly") String privateOnly) {

		try {
			String query = ServiceUtils.getQuery(search);

			ProductDAO productDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductDAO();
			List<Product> products = null;
			if (query.equals("")) {
				products = productDAO.getByUser(userId);
			} else {
				products = productDAO.getByUser(userId, query);
			}
			Gson gson = CustomGsonBuilder.create(compact != null && compact.equals("true"));

			try {
				if (privateOnly != null && privateOnly.equals("true")) {
					return gson.toJson(products);
				} else {
					return "{ \"publicProducts\" :" + getPublicProducts(search, compact)
							+ ", \"products\" : " + (products == null ? "[]" : gson.toJson(products)) + "}";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
		return null;
	}

	/**
	 * Retrieves representation of instances of
	 * <code>it.unitn.webprog2018.ueb.shoppinglist.entities.Product</code>.
	 *
	 * @param search Parameter to filter the results by name
	 * @param compact Parameter to obtain only name and id field of the object
	 *
	 * @return an instance of java.lang.String that represents the products in
	 * Json format
	 */
	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProductCategories(@QueryParam("search") String search,
			@QueryParam("compact") String compact) {

		String query = ServiceUtils.getQuery(search);

		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductsCategoryDAO();

		List<ProductsCategory> productsCategories = null;
		try {
			productsCategories = productsCategoryDAO.getFromQuery(query);
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
		Gson gson = CustomGsonBuilder.create(compact != null && compact.equals("true"));
		try {
			return productsCategories == null ? "{[]}" : gson.toJson(productsCategories);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Creates a new personal object for the specified user
	 *
	 * @param content String in JSON format that contains the field "name"
	 * @param userId Id of the owner of the product
	 * @throws it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException
	 */
	@POST
	@Path("restricted/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authentication
	public void addProduct(String content, @PathParam("userId") Integer userId) {
		Product product = null;
		try {
			Gson gson = new Gson();
			product = gson.fromJson(content, Product.class);
			product.setOwner(new User());
			product.getOwner().setId(userId);
			ProductsCategory productsCategory = new ProductsCategory();
			product.setCategory(productsCategory);
			product.getCategory().setId(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ProductDAO productDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductDAO();
		try {
			productDAO.addProduct(product);
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
	}

}
