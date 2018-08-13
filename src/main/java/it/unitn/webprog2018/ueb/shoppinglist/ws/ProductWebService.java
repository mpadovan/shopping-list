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
import java.util.LinkedList;
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
	 * Retrieves representation of instances of <code>it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct</code>.
	 * @param search Search query to filter results
	 * @param compact Parameter that indicates if the result to be returned
	 * has to contain certain fields of the object (i.e. the product name and ID)
	 *					
	 * @return an instance of java.lang.String that represents the products in Json format
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public String getPublicProducts(@QueryParam("search") String search,
			@QueryParam("compact") String compact) {
		
		StringTokenizer filter = null;
		String query = "";
		if (search != null) {
			filter = new StringTokenizer(search, "-");
			while (filter.hasMoreTokens()) {
				// TODO add sql wildcard
				query += filter.nextToken();
			}
		}
		
		PublicProductDAO publicProductDAO = ((DAOFactory)servletContext.getAttribute("daoFactory")).getPublicProductDAO();
		List<PublicProduct> publicProducts = publicProductDAO.getFromQuery(query);
		
		Gson gson = null;
		if(compact != null && compact.equals("true")) {
			GsonBuilder builder = new GsonBuilder();
			gson = builder.excludeFieldsWithoutExposeAnnotation().create();
		} else {
			gson = new Gson();
		}
		try {
			return gson.toJson(publicProducts);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}