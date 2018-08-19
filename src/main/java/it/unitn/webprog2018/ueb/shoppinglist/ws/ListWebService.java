/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.AddDeletePermission;
import com.google.gson.Gson;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CustomGsonBuilder;
import it.unitn.webprog2018.ueb.shoppinglist.utils.ServiceUtils;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.Authentication;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.ProductPermission;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.ViewPermission;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
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
 * @author Giulia Carocari
 */
@Path("lists")
public class ListWebService {

	@Context
	private UriInfo context;
	@Context
	private ServletContext servletContext;
	@Context
	private HttpServletResponse response;

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
	 * @throws java.io.IOException
	 */
	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategories(@QueryParam("search") String search,
			@QueryParam("compact") String compact) throws IOException {

		ListsCategoryDAO listsCategoryDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListsCategoryDAO();

		String query = ServiceUtils.getQuery(search);
		List<ListsCategory> listsCategories = null;
		try {
			if (query.equals("")) {
				listsCategories = listsCategoryDAO.getAll();
			} else {
				listsCategories = listsCategoryDAO.getFromQuery(query);
			}
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
			return null;
		}
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
	 * Retrieves all the products in a list
	 *
	 * @param listId
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("/restricted/{userId}/permission/{listId}/products")
	@Produces(MediaType.APPLICATION_JSON)
	@Authentication
	@ViewPermission
	public String getProductsOnList(@PathParam("listId") int listId) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		Map<PublicProduct, Integer> publicProductsOnList = null;
		String json = "{ \"publicProducts\" : [";
		try {
			publicProductsOnList = listDAO.getPublicProductsOnList(listId);
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
		if (publicProductsOnList == null || publicProductsOnList.isEmpty()) {
			json += "]";
		} else {
			Gson gson = CustomGsonBuilder.create(false);

			for (Map.Entry<PublicProduct, Integer> entry : publicProductsOnList.entrySet()) {
				try {
					json += "{\"product\":" + gson.toJson(entry.getKey()) + ","
							+ "\"amount\":" + entry.getValue().toString() + "},";
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				}
			}
			if (json.endsWith(",")) {
				char[] tmp = json.toCharArray();
				tmp[json.lastIndexOf(",")] = ']';
				json = new String(tmp);
			}
		}
		json += ",\"products\" : [";
		Map<Product, Integer> productsOnList = null;
		try {
			productsOnList = listDAO.getProductsOnList(listId);
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
		if (productsOnList == null || productsOnList.isEmpty()) {
			json += "]";
		} else {
			Gson gson = CustomGsonBuilder.create(false);
			for (Map.Entry<Product, Integer> entry : productsOnList.entrySet()) {
				try {
					json += "{\"product\":" + gson.toJson(entry.getKey()) + ","
							+ "\"amount\":" + entry.getValue().toString() + "},";
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				}
			}
			if (json.endsWith(",")) {
				char[] tmp = json.toCharArray();
				tmp[json.lastIndexOf(",")] = ']';
				json = new String(tmp);
			}
		}
		json += "}";
		return json;
	}

	/**
	 * Adds a PUBLIC product to a list. If the product is already present, its
	 * amount on the list is incremented by 1.
	 *
	 * @param listId
	 * @param content body of the request
	 */
	@POST
	@Path("/restricted/{userId}/permission/{listId}/products/public")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authentication
	@AddDeletePermission
	public void addPublicProductOnList(@PathParam("listId") int listId, String content) {
		try {
			PublicProduct product = null;
			try {
				Gson gson = new Gson();
				product = gson.fromJson(content, PublicProduct.class);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
			if (!listDAO.isOnList(listId, product)) {
				listDAO.addPublicProduct(listId, product);
			} else {
				listDAO.updateAmount(listId, product);
			}
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
	}

	/**
	 * Adds a PERSONAL product to a	list. If the product is already present, its
	 * amount on the list is incremented by 1.
	 *
	 * @param listId id of the list that is to be modified
	 * @param userId id of the user making the request
	 * @param content body of the request in the form of { "id" : product_id }
	 * (more information may be provided but is not relevant)
	 */
	@POST
	@Path("/restricted/{userId}/permission/{listId}/products/personal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authentication
	@AddDeletePermission
	public void addProductOnList(@PathParam("listId") int listId,
			@PathParam("userId") int userId, String content) {
		Product product = null;
		try {
			Gson gson = new Gson();
			product = gson.fromJson(content, Product.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ProductDAO productDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductDAO();
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		if (product != null) {
			try {
				if (!productDAO.getProduct(product.getId()).getOwner().getId().equals(userId)) {
					if (!response.isCommitted()) {
						response.sendError(401, "The product you are trying to use does not belong to you, you thief!");
					}
				} else {
					if (listDAO.isOnList(listId, product)) {
						listDAO.updateAmount(listId, product);
					} else {
						listDAO.addProduct(listId, product);
					}
				}
			} catch (DaoException ex) {
				ServiceUtils.handleDAOException(ex, response);
			} catch (IOException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * Edits the amount of a PERSONAL product on a list. If the product is
	 * already present, its amount on the list is incremented by 1.
	 *
	 * @param listId
	 * @param productId
	 * @param content body of the request in the form of { "id" : product_id }
	 * (more information may be provided but is not relevant)
	 */
	@PUT
	@Path("/restricted/{userId}/permission/{listId}/products/personal/{productId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authentication
	@AddDeletePermission
	public void editProductOnList(@PathParam("listId") int listId,
			@PathParam("productId") int productId, String content) {
		Integer newAmount = -1;
		try {
			Gson gson = new Gson();
			newAmount = gson.fromJson(content, Integer.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		try {
			Product p = new Product();
			p.setId(productId);
			if (listDAO.isOnList(listId, p)) {
				if (newAmount > 0) {
					listDAO.updateAmount(listId, p, newAmount);
				} else if (newAmount == 0) {
					listDAO.deleteFromList(listId, p);
				}
			} else {

			}
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
	}

	/**
	 * Edits the amount of a PUBLIC product on a list. If the product is already
	 * present, its amount on the list is incremented by 1.
	 *
	 * @param listId
	 * @param productId
	 * @param content body of the request in the form of { "id" : product_id }
	 * (more information may be provided but is not relevant)
	 */
	@PUT
	@Path("/restricted/{userId}/permission/{listId}/products/public/{productId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authentication
	@AddDeletePermission
	public void editPublicProductOnList(@PathParam("listId") int listId,
			@PathParam("productId") int productId, String content) {
		Integer newAmount = -1;
		try {
			Gson gson = new Gson();
			newAmount = gson.fromJson(content, Integer.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		try {
			PublicProduct p = new PublicProduct();
			p.setId(productId);
			if (listDAO.isOnList(listId, p)) {
				if (newAmount > 0) {
					listDAO.updateAmount(listId, p, newAmount);
				} else if (newAmount == 0) {
					listDAO.deleteFromList(listId, p);
				}
			} else {

			}
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
	}

	/**
	 * Removes a PERSONAL product from a list.
	 *
	 * @param listId id of the list to be edited
	 * @param productId	id of the product to be deleted
	 */
	@DELETE
	@Path("/restricted/{userId}/permission/{listId}/products/personal/{productId}")
	@Authentication
	@AddDeletePermission
	public void deleteProductOnList(@PathParam("listId") int listId, @PathParam("productId") int productId) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		try {
			Product p = new Product();
			p.setId(productId);
			listDAO.deleteFromList(listId, p);
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
	}

	/**
	 * Removes a public product from a list.
	 *
	 * @param listId
	 * @param productId
	 */
	@DELETE
	@Path("/restricted/{userId}/permission/{listId}/products/public/{productId}")
	@Authentication
	@AddDeletePermission
	public void deletePublicProductOnList(@PathParam("listId") int listId, @PathParam("productId") int productId) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		try {
			PublicProduct p = new PublicProduct();
			p.setId(productId);
			listDAO.deleteFromList(listId, p);
		} catch (DaoException ex) {
			ServiceUtils.handleDAOException(ex, response);
		}
	}
}
