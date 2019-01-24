/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.UploadHandler;
import java.io.IOException;
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
@WebServlet(name = "ChangeImageAdminServlet", urlPatterns = {"/restricted/admin/ChangeImageAdmin"})
@MultipartConfig
public class ChangeImageAdminServlet extends HttpServlet {

	UserDAO userDAO;

	@Inject
	UploadHandler uploadHandler;

	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
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
		request.getRequestDispatcher("/WEB-INF/views/admin/ChangeImageAdmin.jsp").forward(request, response);
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

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		String avatarURI = null;

		Part avatar = request.getPart("image");
		if ((avatar != null) && (avatar.getSize() > 0)) {
			// Delete old image
			if (!uploadHandler.deleteFile(user.getImage(), getServletContext())) {
				response.sendError(500);
			}
			try {
				avatarURI = uploadHandler.uploadFile(avatar, UploadHandler.FILE_TYPE.AVATAR, user, getServletContext());
			} catch (IOException ex) {
				// It is not a fatal error, we ask the user to try again
				Logger.getLogger(ChangeImageAdminServlet.class.getName()).log(Level.WARNING, null, ex);
				user.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
				// allows to forward response with correct loading of accessory information from the database to be shown in the jsp.
				// request.setAttribute("user", user);
				doGet(request, response);
			}

			if (!response.isCommitted()) {
				user.setImage(avatarURI);
				user.setCheckpassword(user.getPassword());
				try {
					if (userDAO.updateUser(user.getId(), user)) {
						session.setAttribute("user", user);
						response.sendRedirect(context + "restricted/admin/InfoAdmin");
					}
				} catch (DaoException ex) {
					Logger.getLogger(ChangeImageAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		} else {
			response.sendRedirect(context + "restricted/admin/InfoAdmin");
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Servlet that changes the avatar of an admin";
	}
}
