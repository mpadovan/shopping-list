/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.Authentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author giulia
 */
@Path("/geolocation")
public class GeolocationWebService {

	@Context
	private UriInfo context;
	@Context
	private ServletContext servletContext;

	private static final String APP_ID = "325670077977422";
	private static final String SECRET_KEY = "1525571cadb3b95afe522d7716ab731a";
	private static final String RADIUS = "2000";

	/**
	 * Creates a new instance of GeolocationWebService
	 */
	public GeolocationWebService() {
	}

	/**
	 * Retrieves representation of an instance of
	 * it.unitn.webprobramming.geolocationtest.entities.Shop
	 *
	 * @param userId
	 * @param location
	 * @param category
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("/restricted/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Authentication
	public String getShopsByUser(@PathParam("userId") int userId,
			@QueryParam("location") String location) throws DaoException {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();

		Set<String> categories = new HashSet<>();
		java.util.List<List> lists = listDAO.getByUser(userId);
		for (List l : lists) {
			categories.add(l.getCategory().getName());
		}
		Client client = ClientBuilder.newClient();
		String json = "[";
		URI uri = null;
		for (String cat : categories) {
			String response = "";
			try {
				uri = new URI("https", "graph.facebook.com", "/search", "type=place&fields=name&"
						+ "center=" + location + "&distance" + RADIUS + "&"
						+ "access_token=" + APP_ID + "|" + SECRET_KEY + "&q=" + cat.toLowerCase(), null);
			} catch (URISyntaxException ex) {
				Logger.getLogger(GeolocationWebService.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (uri != null) {
				response = client.target(uri).
						request().
						accept(MediaType.APPLICATION_JSON).
						get(String.class);
				json += "{ \"category\":" + "\"" + cat + "\"," + "\"response\":" + response + "},";
			}
		}
		if (json.endsWith(",")) {
			char[] tmp = json.toCharArray();
			tmp[json.lastIndexOf(",")] = ']';
			json = new String(tmp);
		} else {
			json += "]";
		}
		return json;
	}

	/**
	 * Retrieves representation of an instance of
	 * it.unitn.webprobramming.geolocationtest.entities.Shop
	 *
	 * @param location
	 * @param category
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getShops(@PathParam("category") String category,
			@QueryParam("location") String location) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();

		Client client = ClientBuilder.newClient();
		String json = "[";
		URI uri = null;
		String response = "";
		try {
			uri = new URI("https", "graph.facebook.com", "/search", "type=place&fields=name&"
					+ "center=" + location + "&distance" + RADIUS + "&"
					+ "access_token=" + APP_ID + "|" + SECRET_KEY + "&q=" + category.toLowerCase(), null);
		} catch (URISyntaxException ex) {
			Logger.getLogger(GeolocationWebService.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (uri != null) {
			response = client.target(uri).
					request().
					accept(MediaType.APPLICATION_JSON).
					get(String.class);
			json += "{ \"category\":" + "\"" + category + "\"," + "\"response\":" + response + "},";
		}

		if (json.endsWith(
				",")) {
			char[] tmp = json.toCharArray();
			tmp[json.lastIndexOf(",")] = ']';
			json = new String(tmp);
		} else {
			json += "]";
		}
		return json;
	}
}
