/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import com.google.gson.Gson;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Shop;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.*;

/**
 * REST Web Service
 *
 * @author giulia
 */
@Path("geolocation")
public class GeolocationWebService {

	@Context
	private UriInfo context;

	private static String APP_ID = "325670077977422";
	private static String SECRET_KEY = "1525571cadb3b95afe522d7716ab731a";
	private static String RADIUS = "2000";

	/**
	 * Creates a new instance of GeolocationWebService
	 */
	public GeolocationWebService() {
	}

	/**
	 * Retrieves representation of an instance of
	 * it.unitn.webprobramming.geolocationtest.entities.Shop
	 *
	 * @param userID
	 * @param listId
	 * @param location
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("/{userID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getShops(@PathParam("userId") int userID,
			@QueryParam("location") String location) {
		String category = "fioreria";
		Client client = ClientBuilder.newClient();
		WebTarget target
				= client.target("https://graph.facebook.com");
		
		String response = target.path("/search" + '?' + "type=place&fields=name,location&" +
								"center=" + location + "&distance" + RADIUS + "&" +
								"access_token=" + APP_ID + "|" + SECRET_KEY + "&q=" + category.toLowerCase()).
                            request().
                            accept(MediaType.APPLICATION_JSON).
                            get(Response.class)
                            .toString();
		/*
		WebTarget placesTarget
				= webTarget.path("/search?type=place&fields=name,location&"
						+ "center=" + location + "&distance" + RADIUS + "&"
						+ "access_token=" + APP_ID + "|" + SECRET_KEY + " &q=" + category.toLowerCase());

		Invocation.Builder invocationBuilder
				= placesTarget.request(MediaType.APPLICATION_JSON);
		ClientResponse response = invocationBuilder.get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			try {
				throw new Exception();
			} catch (Exception ex) {
				return "{ \"status\" : " + response.getStatus() + "}";
			}
		}
		String output = (String)response.getEntity();
		*/
		return response;
		// if (shops == null || shops.isEmpty()) {
		// 	return "No shops found";
		// }
		// Gson gson = new Gson();
		// return "some shops found but i can't represent them in json";
		// return gson.toJson(shops);
	}
}
