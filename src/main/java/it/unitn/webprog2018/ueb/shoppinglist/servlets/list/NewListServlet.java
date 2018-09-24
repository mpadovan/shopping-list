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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author giulia
 */
@WebServlet(name = "NewListServlet", urlPatterns = {"/restricted/NewList"})
@MultipartConfig
public class NewListServlet extends HttpServlet {
	
	ListsCategoryDAO listsCategoryDAO;
	ListDAO listDAO;
	UserDAO userDAO;
	
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
			List<ListsCategory> listsCategory = listsCategoryDAO.getAll();
			request.setAttribute("listsCategory", listsCategory);
		} catch (DaoException ex) {
			Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/views/list/NewList.jsp").forward(request, response);
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
		String path = getServletContext().getContextPath();
		if (!path.endsWith("/")) {
			path += "/";
		}
		
		Boolean everythingOK = true;
		Boolean isShared = false;
		HttpSession session = request.getSession(false);
		User me = (User) session.getAttribute("user");
		String name = request.getParameter("nameList");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String description = request.getParameter("description");
		String[] shared = request.getParameterValues("shared[]");
		
		it.unitn.webprog2018.ueb.shoppinglist.entities.List list = new it.unitn.webprog2018.ueb.shoppinglist.entities.List();
		list.setName(name);
		list.setOwner(me);
		list.setDescription(description);
		ListsCategory listCategory = null;
		try {
			//Categoria
			try {
				listCategory = listsCategoryDAO.getById(categoryId);
			} catch (RecordNotFoundDaoException ex) {
				list.setError("category", "questa categoria non esiste");
				request.setAttribute("list", list);
			}
			list.setCategory(listCategory);
			
			// check if there are any users in the shared[] textfields
			if (shared != null && shared.length != 0) {
				for (int i = 0; i < shared.length; i++) {
					if (!shared[i].isEmpty()) {
						isShared = true;
					}
				}
			}
			
			if (!isShared) {
				// Add privatelist
				Boolean valid = listDAO.addList(list);
				if (!listDAO.linkShoppingListToUser(list, me.getId())) {
					new SQLException("link non effettutato tra lista e utente");
				}
				if (!valid) {
					everythingOK = false;
					request.setAttribute("list", list);
				}
			} else {
				// Add shared list
				LinkedList<User> listShared = new LinkedList();
				for (int i = 0; i < shared.length; i++) {
					try {
						if (!shared[i].isEmpty() && !shared[i].equals(me.getEmail())) {
							User u = userDAO.getByEmail(shared[i]);
							listShared.add(u);
						}
					} catch (RecordNotFoundDaoException ex) {
						everythingOK = false;
						ex.printStackTrace();
						list.setError("shared[]", "l'utente " + shared[i] + " non esiste");
						request.setAttribute("list", list);
					}
				}
				boolean valid = false;
				try {
					valid = listDAO.addList(list);
				} catch (DaoException ex) {
					everythingOK = false;
					// TODO impostare correttamente l'errore
					list.setError("sth", "Qualcosa è andato storto mentre cercavamo di salvare la lista");
					request.setAttribute("list", list);
					ex.printStackTrace();
				}
				if (valid) {
					for (User u : listShared) {	// connect list to users, apart from the owner
						if (!u.getEmail().equals(me.getEmail())) {
							if (!listDAO.linkShoppingListToUser(list, u.getId())) {
								everythingOK = false;
								try {
									throw new SQLException("link non effettutato tra lista e utente");
								} catch (SQLException ex) {
									Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
								}
							}
						}
					}
				} else {
					everythingOK = false;
					request.setAttribute("list", list);
				}
			}
			if (everythingOK) {
				path += "restricted/HomePageLogin/" + me.getId() + "/" + list.getId();
				if (!isShared) {
					//set sessione liste not shared
					((java.util.List<it.unitn.webprog2018.ueb.shoppinglist.entities.List>) session.getAttribute("personalLists")).add(list);
				} else {
					//set sessione liste shared
					((java.util.List<it.unitn.webprog2018.ueb.shoppinglist.entities.List>) session.getAttribute("sharedLists")).add(list);
				}
				// Save the list image, or set the imageURI to an empty string (default will be loaded in InfoList.jsp)
				File file = null;
				String imageURI = "";
				String imageFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "shared";
				Part image = request.getPart("image");
				if ((image != null) && (image.getSize() > 0)) {
					String imageFileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();
					int ext = imageFileName.lastIndexOf(".");
					int noExt = imageFileName.lastIndexOf(File.separator);
					imageFileName = imageFolder + File.separator + list.getId() + (ext > noExt ? imageFileName.substring(ext) : "");
					InputStream fileContentImage = image.getInputStream();
					ext = imageFileName.lastIndexOf(".");
					noExt = imageFileName.lastIndexOf(File.separator);
					file = new File(imageFileName);
					try {
						Files.copy(fileContentImage, file.toPath());
						imageURI = getServletContext().getContextPath() + File.separator + "uploads" + File.separator + "shared"
								+ File.separator + list.getId() + (ext > noExt ? imageFileName.substring(ext) : "");
						
					} catch (IOException ex) {
						Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
						request.setAttribute("uploadFail", true);
					}
				}
				list.setImage(imageURI);
				// Update the list after setting the image URI
				listDAO.updateList(list.getId(), list);
				response.sendRedirect(path);
			} else {
				// reload the page keeping request and response objects
				// redirect would remove request associated objects
				// forward would prevent the categories from being reloaded because request is directly processed by jsp, without servlet interception
				doGet(request, response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
		}
	}
	
	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "New list servlet";
	}
	
}
