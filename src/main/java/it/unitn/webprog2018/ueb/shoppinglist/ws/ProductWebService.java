/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author giulia
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
	 * Retrieves representation of an instance of it.unitn.webprog2018.ueb.shoppinglist.ws.ProductWebService
	 * @return an instance of java.lang.String
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public String getJson() {
		DAOFactory factory = (DAOFactory) servletContext.getAttribute("daoFactory");
		UserDAO userDAO = factory.getUserDAO();
		return null;	
	}

	/**
	 * PUT method for updating or creating an instance of ProductWebService
	 * @param content representation for the resource
	 */
	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
	public void putJson(String content) {
	}
}
