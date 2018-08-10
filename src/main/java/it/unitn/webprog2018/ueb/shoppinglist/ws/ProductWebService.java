/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import com.google.gson.Gson;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.List;
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
	 * Retrieves representation of an instance of <code>it.unitn.webprog2018.ueb.shoppinglist.entities.Product</code>
	 * @param email Email of the <code>it.unitn.webprog2018.ueb.shoppinglist.entities.User</code> that owns the products
	 * @param search Search string to filter results
	 * @return an instance of java.lang.String that represents the products in json format
	 */
	@GET
	@Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
	public String getProducts(@PathParam("email") String email, @QueryParam("search") String search) {
		List<Product> products = null;
		if (!email.equals("null")) {
			DAOFactory factory = (DAOFactory) servletContext.getAttribute("daoFactory");
			ProductDAO productDAO = factory.getProductDAO();
			products = productDAO.getByUser(email);
		}
		PublicProductDAO publicProductDAO = ((DAOFactory)servletContext.getAttribute("daoFactory")).getPublicProductDAO();
		List<PublicProduct> publicProducts = publicProductDAO.getAll();
		
		Gson gson = new Gson();
		try {
			return gson.toJson(products);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public String getProducts() {
		DAOFactory factory = (DAOFactory) servletContext.getAttribute("daoFactory");
		ProductDAO productDAO = factory.getProductDAO();
		List<Product> products = productDAO.getAll();
		Gson gson = new Gson();
		try {
			return gson.toJson(products);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * PUT method for updating or creating an instance of ProductWebService
	 * @param content representation for the resource
	 */
	@PUT
	@Path("/private")
    @Consumes(MediaType.APPLICATION_JSON)
	public void putProduct(String content) {
		DAOFactory factory = (DAOFactory) servletContext.getAttribute("daoFactory");
		ProductDAO productDAO = factory.getProductDAO();
		Gson gson = new Gson();
		Product product = new Product();
		product = gson.fromJson(content, product.getClass());
		productDAO.updateProduct(product);
	}
}