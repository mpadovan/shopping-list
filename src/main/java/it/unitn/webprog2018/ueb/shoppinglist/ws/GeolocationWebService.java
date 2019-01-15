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
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
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
 * REST Web Service to handle shopping suggestions to both anonymous and logged
 * users. Up to this version, the Facebook graphs web service is used to
 * retrieve data.
 *
 * @author Giulia Carocari
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

	
// -------------------------------------------------------------------------- //
/////////////////////////// LOGGED USER METHODS ////////////////////////////////
// -------------------------------------------------------------------------- //
	
	/**
	 * Retrieves shops in a 2 km radius that match the categories of the lists
	 * owned by and shared with the user.
	 *
	 * @param userHash encrypted identifier of the user
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 * @param location	coordinates of the user in the form
	 * <code>latitude,longitude</code>
	 *
	 * @return a Json representation of the mapping between the list categories
	 * and the relative shops (names and addresses)
	 */
	@GET
	@Path("/restricted/{userHash}")
	@Produces(MediaType.APPLICATION_JSON)
	@Authentication
	public String getShopsByUser(@PathParam("userHash") String userHash,
			@QueryParam("location") String location) throws DaoException {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();

		Set<String> categories = new HashSet<>();
		java.util.List<List> lists = listDAO.getByUser(User.getDecryptedId(userHash));
		for (List l : lists) {
			categories.add(l.getCategory().getName());
		}
		Client client = ClientBuilder.newClient();
		String json = "[";
		URI uri = null;
		for (String cat : categories) {
			String response = "";
			try {
				uri = new URI("https", "graph.facebook.com", "/search",
						"type=place&fields=name,single_line_address,website,cover&"
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
	 * Retrieves shops in a 2 km radius that match the category of the anonymous list.
	 *
	 * @param location	coordinates of the user in the form
	 * <code>latitude,longitude</code>
	 * @param category	name of the list category associated with the anonymous list
	 * @return a Json representation of the mapping between the list category
	 * and the relative shops (names and addresses)
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
			uri = new URI("https", "graph.facebook.com", "/search", "type=place&fields=name,single_line_address,website,cover&"
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
