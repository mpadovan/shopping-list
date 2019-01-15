/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.list;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
import it.unitn.webprog2018.ueb.shoppinglist.utils.UploadHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author simon
 */
@WebServlet(name = "EditListServlet", urlPatterns = {"/restricted/EditList/*"})
@MultipartConfig
public class EditListServlet extends HttpServlet {

	ListsCategoryDAO listsCategoryDAO;
	ListDAO listDAO;
	UserDAO userDAO;

	@Inject
	private UploadHandler uploadHandler;

	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		listsCategoryDAO = factory.getListsCategoryDAO();
		listDAO = factory.getListDAO();
		userDAO = factory.getUserDAO();
	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		try {
			java.util.List<ListsCategory> listsCategory = listsCategoryDAO.getAll();
			request.setAttribute("listsCategory", listsCategory);
		} catch (DaoException ex) {
			Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/views/list/EditList.jsp").forward(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String context = getServletContext().getContextPath();
		if (!context.endsWith("/")) {
			context += "/";
		}

		if ((boolean) request.getAttribute("hasModifyPermission")) {
			List currentList = (List) request.getAttribute("currentList");
			Boolean everythingOK = true;
			Boolean isShared = false;
			HttpSession session = request.getSession(false);
			User me = (User) session.getAttribute("user");
			String name = request.getParameter("nameList");
			Integer categoryId = Integer.parseInt(request.getParameter("category"));
			String description = request.getParameter("description");
			String[] shared = request.getParameterValues("shared[]");

			currentList.setName(name);
			currentList.setDescription(description);
			ListsCategory listCategory;

			try {
				if (currentList.isVaildOnUpdate((DAOFactory) getServletContext().getAttribute("daoFactory"))) {
					//Categoria
					try {
						listCategory = listsCategoryDAO.getById(categoryId);
						currentList.setCategory(listCategory);
					} catch (RecordNotFoundDaoException ex) {
						everythingOK = false;
						currentList.setError("category", "Questa categoria non esiste");
						request.setAttribute("currentList", currentList);
					}

					// check if there are any users in the shared[] textfields
					if (shared != null && shared.length != 0) {
						for (String shared1 : shared) {
							if (!shared1.isEmpty() && !shared1.equals(me.getEmail())) {
								isShared = true;
							}
						}
					}

					if (isShared) {
						java.util.List<User> connectedUsers = listDAO.getConnectedUsers(currentList.getId());
						LinkedList<User> listShared = new LinkedList();
						// Create the list of new shared users
						for (String shared1 : shared) { // if shared is null then isShared is false
							try {
								if (!shared1.isEmpty() && !shared1.equals(me.getEmail())) {
									User u = userDAO.getByEmail(shared1);
									if (!connectedUsers.contains(u)) {
										listShared.add(u);
									}
								}
							} catch (RecordNotFoundDaoException ex) {
								everythingOK = false;
								Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
								currentList.setError("shared[]", "l'utente " + shared1 + " non esiste");
								request.setAttribute("list", currentList);
							}
						}
						if (everythingOK) {
							if (listDAO.updateList(currentList.getId(), currentList)) {
								// Add new users
								for (User u : listShared) {	// connect list to users, apart from the owner
									String[] perm = request.getParameterValues("permission-" + u.getEmail());
									boolean adddelete = true, edit = false, delete = false;
									switch (perm[0]) {
										case "view":
											adddelete = false; edit = false; delete = false; break;
										case "basic":
											adddelete = true; edit = false; delete = false; break;
										case "full":
											adddelete = true; edit = true; delete = true; break;
										default: break;
									}
									if (!listDAO.linkShoppingListToUser(currentList, u.getId(), adddelete, edit, delete)) {
										everythingOK = false;
										try {
											throw new SQLException("link non effettutato tra lista e utente");
										} catch (SQLException ex) {
											Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
										}
									}
								}
							} else {
								everythingOK = false;
								request.setAttribute("list", currentList);
							}
						}
					} else {
						if (!listDAO.updateList(currentList.getId(), currentList)) {
							everythingOK = false;
						}
					}
					if (everythingOK) {
						String path = context + "restricted/InfoList/" + me.getHash()+ "/" + currentList.getHash();

						// Save the list image, or set the imageURI to an empty string (default will be loaded in InfoList.jsp)
						String imageURI = "";
						Part image = request.getPart("image");
						if ((image != null) && (image.getSize() > 0)) {
							try {
								uploadHandler.deleteFile(currentList.getImage(), getServletContext());
								imageURI = uploadHandler.uploadFile(image, UploadHandler.FILE_TYPE.LIST_IMAGE, currentList, getServletContext());
							} catch (IOException ex) {
								Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
								currentList.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
								request.setAttribute("list", currentList);
								doGet(request, response);
							}
							if (!response.isCommitted()) {
								currentList.setImage(imageURI);
								// Update the list after setting the image URI
								listDAO.updateList(currentList.getId(), currentList);
							}
						}
						response.sendRedirect(path);
					} else {
						// reload the page keeping request and response objects
						// redirect would remove request associated objects
						// forward would prevent the categories from being reloaded because request is directly processed by jsp, without servlet interception
						doGet(request, response);
					}
				} else {
					request.setAttribute("currentList", currentList);
					doGet(request, response);
				}
			} catch (DaoException ex) {
				HttpErrorHandler.handleDAOException(ex, response);
				Logger.getLogger(EditListServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			HttpErrorHandler.sendError401(response);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Edit List Servlet";
	}

}
