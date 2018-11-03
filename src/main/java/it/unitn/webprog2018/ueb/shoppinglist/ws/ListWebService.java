/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CustomGsonBuilder;
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.Authentication;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
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
	private static ServletContext servletContext;
	@Context
	private static HttpServletResponse response;

	/**
	 * Creates a new instance of ListWebService
	 */
	public ListWebService() {
	}

	// ---------------------------------------------------------------------- //
	//////////////////////////// COMMON METHODS ////////////////////////////////
	// ---------------------------------------------------------------------- //
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

		String query = HttpErrorHandler.getQuery(search);
		List<ListsCategory> listsCategories;
		try {
			if (query.equals("")) {
				listsCategories = listsCategoryDAO.getAll();
			} else {
				listsCategories = listsCategoryDAO.getFromQuery(query);
			}
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
			return null;
		}
		if (listsCategories == null || listsCategories.isEmpty()) {
			return "{[]}";
		}
		Gson gson = CustomGsonBuilder.create(compact != null && compact.equals("true"));
		try {
			return gson.toJson(listsCategories);
		} catch (JsonSyntaxException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.sendError500(response);
		}
		return null;
	}

	// ---------------------------------------------------------------------- //
	///////////////////////// LOGGED USER METHODS //////////////////////////////
	// ---------------------------------------------------------------------- //
	/**
	 * Retrieves all the products in a list
	 *
	 * @param listId
	 * @param userId
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("/restricted/{userId}/permission/{listId}/products")
	@Produces(MediaType.APPLICATION_JSON)
	@Authentication
	public String getProductsOnList(@PathParam("listId") int listId, @PathParam("userId") Integer userId) {
		String json = null;
		if (checkViewPermission(listId, userId)) {
			ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
			Map<PublicProduct, Integer> publicProductsOnList = null;
			json = "{ \"publicProducts\" : [";
			try {
				publicProductsOnList = listDAO.getPublicProductsOnList(listId);
			} catch (DaoException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
			if (publicProductsOnList == null || publicProductsOnList.isEmpty()) {
				json += "]";
			} else {
				Gson gson = CustomGsonBuilder.create(false);

				for (Map.Entry<PublicProduct, Integer> entry : publicProductsOnList.entrySet()) {
					try {
						json += "{\"product\":" + gson.toJson(entry.getKey()) + ","
								+ "\"amount\":" + entry.getValue().toString() + "},";
					} catch (JsonSyntaxException ex) {
						Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
						HttpErrorHandler.sendError500(response);
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
				Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
			if (productsOnList == null || productsOnList.isEmpty()) {
				json += "]";
			} else {
				Gson gson = CustomGsonBuilder.create(false);
				for (Map.Entry<Product, Integer> entry : productsOnList.entrySet()) {
					try {
						json += "{\"product\":" + gson.toJson(entry.getKey()) + ","
								+ "\"amount\":" + entry.getValue().toString() + "},";
					} catch (JsonException ex) {
						Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
						HttpErrorHandler.sendError500(response);
						return null;
					}
				}
				// Closes json with correct syntax
				if (json.endsWith(",")) {
					char[] tmp = json.toCharArray();
					tmp[json.lastIndexOf(",")] = ']';
					json = new String(tmp);
				}
			}
			try {
				json += ", \"editList\":";
				if (listDAO.getList(listId).getOwner().getId().equals(userId) || listDAO.hasAddDeletePermission(listId, userId)) {
					json += "true";
				} else {
					json += "false";
				}
				json += "}";
			} catch (DaoException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
		}
		return json;
	}

	/**
	 * Adds a PUBLIC product to a list. If the product is already present, its
	 * amount on the list is incremented by 1.
	 *
	 * @param userId
	 * @param listId
	 * @param content body of the request
	 */
	@POST
	@Path("/restricted/{userId}/permission/{listId}/products/public")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authentication
	public void addPublicProductOnList(@PathParam("userId") int userId, @PathParam("listId") int listId, String content) {
		if (checkAddDeletePermission(listId, userId)) {
			try {
				PublicProduct product = null;
				try {
					Gson gson = new Gson();
					product = gson.fromJson(content, PublicProduct.class);
				} catch (JsonSyntaxException ex) {
					Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
					HttpErrorHandler.sendError500(response);
				}
				ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
				if (!listDAO.isOnList(listId, product)) {
					listDAO.addPublicProduct(listId, product);
				} else {
					listDAO.updateAmount(listId, product);
				}
			} catch (DaoException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
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
	public void addProductOnList(@PathParam("listId") int listId,
			@PathParam("userId") int userId, String content) {
		if (checkAddDeletePermission(listId, userId)) {
			Product product = null;
			try {
				Gson gson = new Gson();
				product = gson.fromJson(content, Product.class);
			} catch (JsonSyntaxException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.sendError500(response);
			}
			ProductDAO productDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductDAO();
			ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
			if (product != null) {
				try {
					if (!productDAO.getProduct(product.getId()).getOwner().getId().equals(userId)) {
						HttpErrorHandler.sendError401(response);
					} else {
						if (listDAO.isOnList(listId, product)) {
							listDAO.updateAmount(listId, product);
						} else {
							listDAO.addProduct(listId, product);
						}
					}
				} catch (DaoException ex) {
					Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
					HttpErrorHandler.handleDAOException(ex, response);
				}
			}
		}
	}

	/**
	 * Edits the amount of a PERSONAL product on a list. If the product is
	 * already present, its amount on the list is incremented by 1.
	 *
	 * @param userId
	 * @param listId
	 * @param productId
	 * @param content body of the request in the form of { "id" : product_id }
	 * (more information may be provided but is not relevant)
	 */
	@PUT
	@Path("/restricted/{userId}/permission/{listId}/products/personal/{productId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authentication
	public void editProductOnList(@PathParam("userId") int userId, @PathParam("listId") int listId,
			@PathParam("productId") int productId, String content) {
		if (checkAddDeletePermission(listId, userId) && checkProductPermission(productId, userId)) {
			Integer newAmount = -1;
			try {
				Gson gson = new Gson();
				newAmount = gson.fromJson(content, Integer.class);
			} catch (JsonSyntaxException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.sendError500(response);
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
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
		}
	}

	/**
	 * Edits the amount of a PUBLIC product on a list. If the product is already
	 * present, its amount on the list is incremented by 1.
	 *
	 * @param userId
	 * @param listId
	 * @param productId
	 * @param content body of the request in the form of { "id" : product_id }
	 * (more information may be provided but is not relevant)
	 */
	@PUT
	@Path("/restricted/{userId}/permission/{listId}/products/public/{productId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Authentication
	public void editPublicProductOnList(@PathParam("userId") int userId, @PathParam("listId") int listId,
			@PathParam("productId") int productId, String content) {
		if (checkAddDeletePermission(listId, userId)) {
			Integer newAmount = -1;
			try {
				Gson gson = new Gson();
				newAmount = gson.fromJson(content, Integer.class);
			} catch (JsonSyntaxException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.sendError500(response);
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
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
		}
	}

	/**
	 * Removes a PERSONAL product from a list.
	 *
	 * @param userId
	 * @param listId id of the list to be edited
	 * @param productId	id of the product to be deleted
	 */
	@DELETE
	@Path("/restricted/{userId}/permission/{listId}/products/personal/{productId}")
	@Authentication
	public void deleteProductOnList(@PathParam("userId") int userId,
			@PathParam("listId") int listId, @PathParam("productId") int productId) {
		if (checkAddDeletePermission(listId, userId)) {
			ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
			try {
				Product p = new Product();
				p.setId(productId);
				listDAO.deleteFromList(listId, p);
			} catch (DaoException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
		}
	}

	/**
	 * Removes a public product from a list.
	 *
	 * @param userId
	 * @param listId
	 * @param productId
	 */
	@DELETE
	@Path("/restricted/{userId}/permission/{listId}/products/public/{productId}")
	@Authentication
	public void deletePublicProductOnList(@PathParam("userId") int userId,
			@PathParam("listId") int listId, @PathParam("productId") int productId) {
		if (checkAddDeletePermission(listId, userId)) {
			ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
			try {
				PublicProduct p = new PublicProduct();
				p.setId(productId);
				listDAO.deleteFromList(listId, p);
			} catch (DaoException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.handleDAOException(ex, response);
			}
		}
	}

	// ---------------------------------------------------------------------- //
	//////////////////////// ANONYMOUS USER METHODS ////////////////////////////
	// ---------------------------------------------------------------------- //
	/**
	 * Sets the list category of an anonymous list.
	 *
	 * @param token
	 * @param content must be in the form of { "id" : _id, "name" : _name, ...}.
	 * See <code>ListsCategory</code> entity.
	 */
	@PUT
	@Path("/anon/{token}/listCategory")
	public void setAnonCategory(@PathParam("token") String token, String content) {
		ListsCategoryDAO listsCategoryDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListsCategoryDAO();
		Gson gson = new Gson();
		ListsCategory listsCategory;
		try {
			listsCategory = gson.fromJson(content, ListsCategory.class);
		} catch (JsonSyntaxException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.sendError500(response);
			return;
		}

		try {
			listsCategoryDAO.setListCategory(token, listsCategory);
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
	}

	@GET
	@Path("/anon/{token}/listCategory")
	public String getAnonCategory(@PathParam("token") String token) {
		try {
			ListsCategoryDAO listsCategoryDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListsCategoryDAO();
			ListsCategory listsCategory = listsCategoryDAO.getListCategory(token);
			Gson gson = CustomGsonBuilder.create(true);
			String json;
			try {
				json = gson.toJson(listsCategory, ListsCategory.class);
			} catch (JsonException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.sendError500(response);
				return null;
			}
			return json;
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
		return null;
	}

	/**
	 * Retrieves a json representation of all (public) products on an anonymous
	 * list. The products are all mapped to their amount. The Json
	 * representation is an array in the form: [ { "product" : {}, "amount" : x
	 * }, {...}, ...]
	 *
	 * @param token
	 * @return
	 */
	@GET
	@Path("/anon/{token}/product")
	public String getProductsOnAnonList(@PathParam("token") String token) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		Map<PublicProduct, Integer> productsOnList;
		Gson gson = CustomGsonBuilder.create(true);
		String json = null;
		try {
			productsOnList = listDAO.getProductsOnList(token);
			json = "[";
			try {
				for (Map.Entry<PublicProduct, Integer> entry : productsOnList.entrySet()) {
					try {
						json += "{\"product\":" + gson.toJson(entry.getKey()) + ","
								+ "\"amount\":" + entry.getValue().toString() + "},";
					} catch (JsonSyntaxException ex) {
						Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
						HttpErrorHandler.sendError500(response);
						return null;
					}
				}
				if (json.endsWith(",")) {
					char[] tmp = json.toCharArray();
					tmp[json.lastIndexOf(",")] = ']';
					json = new String(tmp);
				}
			} catch (JsonException ex) {
				Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
				HttpErrorHandler.sendError500(response);
				return null;
			}
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}

		return json;
	}

	/**
	 * Adds a (public) product to an anonymous list. The content of the post
	 * request must be in the form of { _id }.
	 *
	 * @param token
	 * @param content
	 */
	@POST
	@Path("/anon/{token}/product")
	public void addProductOnAnonList(@PathParam("token") String token, String content) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		Gson gson = new Gson();
		Integer productId = null;
		try {
			productId = gson.fromJson(content, Integer.class);
		} catch (JsonSyntaxException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.sendError500(response);
			return;
		}
		PublicProduct product = new PublicProduct();
		product.setId(productId);

		try {
			// check if product is already on list, then increase amount by one or newly insert the product
			Map<PublicProduct, Integer> prodOnList = listDAO.getProductsOnList(token);
			if (prodOnList.containsKey(product)) {
				listDAO.updateAmount(token, product);
			} else {
				listDAO.addProduct(token, product);
			}
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
	}

	/**
	 * Updates the amount of a (public) product on an anonymous list. The
	 * content of the post request must be in the form of { _newAmount }.
	 *
	 * @param token
	 * @param productId
	 * @param content
	 */
	@PUT
	@Path("/anon/{token}/product/{prod_id}")
	public void updateProductOnAnonList(@PathParam("token") String token,
			@PathParam("prod_id") Integer productId, String content) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		Gson gson = new Gson();
		Integer newAmount = null;
		try {
			newAmount = gson.fromJson(content, Integer.class);
		} catch (JsonSyntaxException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.sendError500(response);
			return;
		}
		PublicProduct product = new PublicProduct();
		product.setId(productId);

		try {
			listDAO.updateAmount(token, product, newAmount);
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
	}

	@DELETE
	@Path("/anon/{token}/product/{prod_id}")
	public void deleteProductsFromAnonList(@PathParam("token") String token,
			@PathParam("prod_id") Integer productId) {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
		PublicProduct product = new PublicProduct();
		product.setId(productId);

		try {
			listDAO.deleteFromList(token, product);
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
	}

	// ---------------------------------------------------------------------- //
	//////////////////////////// PRIVATE METHODS ///////////////////////////////
	// ---------------------------------------------------------------------- //
	private boolean checkAddDeletePermission(int listId, int userId) {
		try {
			ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
			it.unitn.webprog2018.ueb.shoppinglist.entities.List list = listDAO.getList(listId);

			if (list.getOwner().getId().equals(userId)
					|| listDAO.hasAddDeletePermission(listId, userId)) {
				return true;
			} else {
				HttpErrorHandler.sendError401(response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
		return false;
	}

	private boolean checkViewPermission(int listId, int userId) {
		try {
			ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();
			it.unitn.webprog2018.ueb.shoppinglist.entities.List list = listDAO.getList(listId);

			if (list.getOwner().getId().equals(userId)
					|| listDAO.hasViewPermission(listId, userId)) {
				return true;
			} else {
				HttpErrorHandler.sendError401(response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
		return false;
	}

	private boolean checkProductPermission(int productId, int userId) {
		try {
			ProductDAO productDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductDAO();
			Product product = productDAO.getProduct(productId);
			if (product.getOwner().getId().equals(userId)) {
				return true;
			} else {
				HttpErrorHandler.sendError401(response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
			HttpErrorHandler.handleDAOException(ex, response);
		}
		return false;
	}
}
