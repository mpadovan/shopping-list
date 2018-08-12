/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author giulia
 */
@Path("lists")
public class ListWebService {

	@Context
	private UriInfo context;
	@Context
	private ServletContext servletContext;

	/**
	 * Creates a new instance of ListWebService
	 */
	public ListWebService() {
	}

	/**
	 * Retrieves representation of an instance of
	 * it.unitn.webprog2018.ueb.shoppinglist.ws.ListWebService
	 *
	 * @param search
	 * @param compact
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJson(@QueryParam("search") String search,
			@QueryParam("compact") String compact) {
		ListsCategoryDAO listsCategoryDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListsCategoryDAO();

		StringTokenizer filter = null;
		String query = "";
		if (search != null) {
			filter = new StringTokenizer(search, "-");
			while (filter.hasMoreTokens()) {
				// ADD WILDCARD
				query += filter.nextToken();
			}
		}
		List<ListsCategory> listsCategories = listsCategoryDAO.getFromQuery(query);
		
		Gson gson = null;
		System.out.println(compact);
		if (compact != null && compact.equals("true")) {
			GsonBuilder builder = new GsonBuilder();
			gson = builder.excludeFieldsWithoutExposeAnnotation().create();
		} else {
			gson = new Gson();
		}
		try {
			return gson.toJson(listsCategories);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * PUT method for updating or creating an instance of ListWebService
	 *
	 * @param content representation for the resource
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void putJson(String content) {
	}
}
