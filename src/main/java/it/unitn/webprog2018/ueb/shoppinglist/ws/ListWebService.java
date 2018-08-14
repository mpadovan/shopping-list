/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
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
	public String getCategories(@QueryParam("search") String search,
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
		if (listsCategories == null || listsCategories.isEmpty()) {
			return "{[]}";
		}
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
	 * Retrieves the products in a personal list
	 *
	 * @param listId
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("/restricted/{user_id}/personal/{listId}/products")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProductsOnPersonalList(@PathParam("listId") int listId) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		Map<PublicProduct, Integer> publicProductsOnList = listDAO.getPublicProductsOnList(listId);
		if (publicProductsOnList == null || publicProductsOnList.isEmpty()) {
			return "{[]}";
		}
		Gson gson = new Gson();
		String json = "{[";
		for (Map.Entry<PublicProduct, Integer> entry : publicProductsOnList.entrySet()) {
			try {
				json += "{\"product\":" + gson.toJson(entry.getKey()) + ","
						+ "\"quantity\":" + entry.getValue().toString() + "},";
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		char[] tmp = json.toCharArray();
		tmp[json.lastIndexOf(",")] = ']';
		json = new String(tmp);
		json += "}";
		return json;
	}
}
