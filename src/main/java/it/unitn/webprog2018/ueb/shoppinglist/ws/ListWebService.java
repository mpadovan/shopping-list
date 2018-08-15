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
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CustomGsonBuilder;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	 * Retrieves representation of the possible categories of a list
	 *
	 * @param search Parameter to filter the results by name
	 * @param compact Parameter to obtain only name and id field of the object
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategories(@QueryParam("search") String search,
			@QueryParam("compact") String compact) {
		ListsCategoryDAO listsCategoryDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListsCategoryDAO();

		String query = ProductWebService.getQuery(search);

		List<ListsCategory> listsCategories = listsCategoryDAO.getFromQuery(query);
		if (listsCategories == null || listsCategories.isEmpty()) {
			return "{[]}";
		}
		Gson gson = CustomGsonBuilder.create(compact != null && compact.equals("true"));
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
	@Path("/restricted/{userId}/personal/{listId}/products")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProductsOnPersonalList(@PathParam("listId") int listId) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		Map<PublicProduct, Integer> publicProductsOnList = listDAO.getPublicProductsOnList(listId);
		if (publicProductsOnList == null || publicProductsOnList.isEmpty()) {
			return "{[]}";
		}
		Gson gson = CustomGsonBuilder.create(false);
		String json = "{[";
		for (Map.Entry<PublicProduct, Integer> entry : publicProductsOnList.entrySet()) {
			try {
				json += "{\"product\":" + gson.toJson(entry.getKey()) + ","
						+ "\"amount\":" + entry.getValue().toString() + "},";
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

	/**
	 * Adds a PUBLIC product to a personal list
	 *
	 * @param listId
	 * @param content body of the request
	 */
	@PUT
	@Path("/restricted/{userId}/personal/{listId}/products/public")
	@Produces(MediaType.APPLICATION_JSON)
	public void addPublicProductOnPersonalList(@PathParam("listId") int listId, String content) {
		PublicProduct product = null;
		try {
			Gson gson = new Gson();
			product = gson.fromJson(content, PublicProduct.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		if (!listDAO.isOnList(listId, product)) {
			if (listDAO.addPublicProduct(listId, product)) {
				System.out.println("Added new product");
			} else {
				try {
					throw new RuntimeException();
				} catch (RuntimeException ex) {
					System.err.println("List with given id does not exist");
				}
			}
		} else {
			if(listDAO.updateAmount(listId,product)) {
				System.out.println("Added new product");
			} else {
				try {
					throw new RuntimeException();
				} catch (RuntimeException ex) {
					System.err.println("List with given id does not exist");
				}
			}
		}
	}

	/**
	 * Adds a PERSONAL product to a personal list
	 *
	 * @param listId
	 * @param content body of the request
	 */
	@PUT
	@Path("/restricted/{userId}/personal/{listId}/products/personal")
	@Produces(MediaType.APPLICATION_JSON)
	public void addProductOnPersonalList(@PathParam("listId") int listId, String content) {
		Product product = null;
		try {
			Gson gson = new Gson();
			product = gson.fromJson(content, Product.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		if (listDAO.addProduct(listId, product)) {
			System.out.println("Added new product");
		} else {
			try {
				throw new RuntimeException();
			} catch (RuntimeException ex) {
				System.err.println("Could not persist this product");
			}
		}
	}

}
