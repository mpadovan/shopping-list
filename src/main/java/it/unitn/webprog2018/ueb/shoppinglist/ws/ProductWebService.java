/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CustomGsonBuilder;
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.Authentication;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service for Products and Public Products. Allows to search them by
 * queries and, for logged users to rapidly create a new one.
 *
 * @author Giulia Carocari
 */
@Path("products")
public class ProductWebService {

	@Context
	private ServletContext servletContext;
	@Context
	private HttpServletResponse response;

	/**
	 * Creates a new instance of ProductWebService
	 */
	public ProductWebService() {
	}

// -------------------------------------------------------------------------- //
////////////////////////////// COMMON METHODS //////////////////////////////////
// -------------------------------------------------------------------------- //
	/**
	 * Retrieves a list of public products that match the query, if it is
	 * present, all products otherwise.
	 *
	 * @param search Parameter to filter the results by name
	 * @param compact Parameter to obtain only name and id field of the object
	 * @return json representation of a {@link java.util.list} containing the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct}
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPublicProducts(@QueryParam("search") String search,
			@QueryParam("compact") String compact) {

		String query = HttpErrorHandler.getQuery(search);

		PublicProductDAO publicProductDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getPublicProductDAO();
		List<PublicProduct> publicProducts = null;
		if (query.equals("")) {
			try {
				publicProducts = publicProductDAO.getAll();
			} catch (DaoException ex) {
				Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
		} else {
			try {
				publicProducts = publicProductDAO.getFromQuery(query);
			} catch (DaoException ex) {
				Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
		}
		Gson gson = CustomGsonBuilder.create(compact != null && compact.equals("true"));
		try {
			return (publicProducts == null ? "[]" : gson.toJson(publicProducts));
		} catch (JsonException ex) {
			Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.sendError500(response);
			return null;
		}
	}

	/**
	 * Retrieves representations of product categories used for grouping and
	 * sorting purposes.
	 *
	 * @param search Parameter to filter the results by name
	 * @param compact Parameter to obtain only name and id field of the object
	 *
	 * @return json representation of a {@link java.util.list} containing
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory}
	 */
	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProductCategories(@QueryParam("search") String search,
			@QueryParam("compact") String compact) {

		String query = HttpErrorHandler.getQuery(search);

		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductsCategoryDAO();

		List<ProductsCategory> productsCategories = null;
		try {
			productsCategories = productsCategoryDAO.getFromQuery(query);
		} catch (DaoException ex) {
			Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
		Gson gson = CustomGsonBuilder.create(compact != null && compact.equals("true"));
		try {
			return productsCategories == null ? "{[]}" : gson.toJson(productsCategories);
		} catch (JsonSyntaxException ex) {
			Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.sendError500(response);
			return null;
		}
	}

// -------------------------------------------------------------------------- //
/////////////////////////// LOGGED USER METHODS ////////////////////////////////
// -------------------------------------------------------------------------- //
	/**
	 * Retrieves representation of instances of
	 * <code>it.unitn.webprog2018.ueb.shoppinglist.entities.Product</code> whose
	 * name matches the query (all instances if no query is specified).
	 *
	 * @param userHash encrypted unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 * @param search Parameter to filter the results by name
	 * @param compact Parameter to obtain only name and id field of the object
	 * @param privateOnly if value is "true" then returns only custom products
	 *
	 * @return json representation of a {@link java.util.list} containing the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct} (if
	 * required) and
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.Product}
	 */
	@GET
	@Path("/restricted/{userHash}")
	@Produces(MediaType.APPLICATION_JSON)
	@Authentication
	public String getProducts(@PathParam("userHash") String userHash, @QueryParam("search") String search,
			@QueryParam("compact") String compact, @QueryParam("privateOnly") String privateOnly) {
		int userId = User.getDecryptedId(userHash);
		try {
			String query = HttpErrorHandler.getQuery(search);

			ProductDAO productDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductDAO();
			List<Product> products;
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
			} catch (JsonSyntaxException ex) {
				Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
				return null;
			}
		} catch (DaoException ex) {
			Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
		return null;
	}

	/**
	 * Creates a new personal object for the specified user and adds it to the
	 * specified list.
	 *
	 * @param content String in JSON format, built as: { "name" : __name }
	 *
	 * @param listHash	unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.List}
	 * @param userHash	unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 */
	@POST
	@Path("restricted/{userHash}/{listHash}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authentication
	public void addProduct(String content, @PathParam("listHash") String listHash, 
			@PathParam("userHash") String userHash) {
		int listId = it.unitn.webprog2018.ueb.shoppinglist.entities.List.getDecryptedId(listHash);
		int userId = User.getDecryptedId(userHash);
		if (checkAddDeletePermission(listId, userId)) {
			try {
				ProductDAO productDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductDAO();
				ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
				ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductsCategoryDAO();
				Gson gson = new Gson();
				Product product = gson.fromJson(content, Product.class);
				product.setOwner(new User());
				product.setNote("");
				product.getOwner().setId(userId);
				product.setCategory(productsCategoryDAO.getDefault());
				try {
					if (product.isVaildOnCreate((DAOFactory) servletContext.getAttribute("daoFactory"))
							&& productDAO.addProduct(product)) {
						listDAO.addProduct(listId, product);
					}
				} catch (DaoException ex) {
					Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
					HttpErrorHandler.handleDAOException(ex, response);
				}
			} catch (JsonSyntaxException ex) {
				Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.sendError500(response);
			}
		}
	}

// -------------------------------------------------------------------------- //
////////////////////////////// PRIVATE METHODS /////////////////////////////////
// -------------------------------------------------------------------------- //
	private boolean checkAddDeletePermission(int listId, int userId) {
		try {
			ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
			it.unitn.webprog2018.ueb.shoppinglist.entities.List list = listDAO.getList(listId);

			if (list.getOwner().getId().equals(userId)
					|| listDAO.hasAddDeletePermission(listId, userId)) {
				return true;
			} else {
				HttpErrorHandler.sendError401(response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
		return false;
	}

}
