/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Giulia Carocari
 */
@Path("products")
public class ProductWebService {

	@Context
	private UriInfo context;
	@Context
	private ServletContext servletContext;
	
	/**
	 * Creates a new instance of ProductWebService
	 */
	public ProductWebService() {
	}
	
	/**
	 * Retrieves representation of instances of <code>it.unitn.webprog2018.ueb.shoppinglist.entities.Product</code>
	 * and <code>it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct</code> accessible to the specified user.
	 * @param email Email of the <code>User</code> that owns the products;
	 * if "null" then considers only <code>PublicProducts</code> as it is an anonymous user.
	 * @param search Search query to filter results
	 * @param compact Parameter that indicates if the result to be returned
	 * has to contain only the primary key of the object (i.e. the product name
	 * @param sortBy Indicates if the results should be sorted by category and/or by name
	 * Usage: <code>products/_username_?sortBy="what-to-sort-by"</code>, where "what-to-sort-by" is any combination of:
	 * <code>+category</code> (category ascending), <code>-category</code> (category descending),
	 * <code>+name</code> (name ascending), <code>-name</code> (name descending).
	 * If both of the sorting options are present, then they should be separated by comma (<code>,</code>).
	 *					
	 * @return an instance of java.lang.String that represents the products in Json format
	 */
	@GET
	@Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
	public String getProducts(@PathParam("email") String email, @QueryParam("search") String search,
			@QueryParam("compact") String compact, @QueryParam("sortBy") String sortBy) {
		List<Product> products = null;
		StringTokenizer filter = null;
		StringTokenizer sortByTok = null;
		if (search != null)
			filter = new StringTokenizer(search, "-");
		if (sortBy != null)
			sortByTok = new StringTokenizer(sortBy, ",");
		if (!email.equals("null")) {
			DAOFactory factory = (DAOFactory) servletContext.getAttribute("daoFactory");
			ProductDAO productDAO = factory.getProductDAO();
			products = productDAO.getByUser(email, filter, sortBy);
		}
		
		PublicProductDAO publicProductDAO = ((DAOFactory)servletContext.getAttribute("daoFactory")).getPublicProductDAO();
		List<PublicProduct> publicProducts = publicProductDAO.getFromQuery(filter, sortByTok);
		
		Gson gson = null;
		if(compact != null && compact.equals("true")) {
			GsonBuilder builder = new GsonBuilder();
			gson = builder.excludeFieldsWithoutExposeAnnotation().create();
		} else {
			gson = new Gson();
		}
		try {
			return "{ \"publicProducts\" : " + gson.toJson(publicProducts) +
					", \"products\" : " + gson.toJson(products) + "}";
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/*
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public String getProducts() {
		DAOFactory factory = (DAOFactory) servletContext.getAttribute("daoFactory");
		PublicProductDAO publicProductDAO = factory.getPublicProductDAO();
		List<PublicProduct> products = publicProductDAO.getAll();
		Gson gson = new Gson();
		try {
			return gson.toJson(products);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	*/
	/**
	 * PUT method for updating or creating an instance of ProductWebService
	 * @param content representation for the resource
	 */
	@PUT
	@Path("/private/{product_name}")
    @Consumes(MediaType.APPLICATION_JSON)
	public void putCustomProduct(String content) {
		DAOFactory factory = (DAOFactory) servletContext.getAttribute("daoFactory");
		ProductDAO productDAO = factory.getProductDAO();
		Gson gson = new Gson();
		Product product = new Product();
		product = gson.fromJson(content, product.getClass());
		productDAO.updateProduct(product);
	}
	
	/**
	 * PUT method for updating or creating an instance of ProductWebService
	 * @param content representation for the resource
	 */
	@PUT
	@Path("/public/{product_name}")
    @Consumes(MediaType.APPLICATION_JSON)
	public void putPublicProduct(String content) {
		DAOFactory factory = (DAOFactory) servletContext.getAttribute("daoFactory");
		PublicProductDAO productDAO = factory.getPublicProductDAO();
		Gson gson = new Gson();
		PublicProduct product = new PublicProduct();
		product = gson.fromJson(content, product.getClass());
		productDAO.updateProduct(product);
	}
}