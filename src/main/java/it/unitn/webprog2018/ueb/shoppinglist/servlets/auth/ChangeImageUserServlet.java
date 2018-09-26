/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.servlets.auth;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.UploadHandler;
import java.io.IOException;
import java.nio.file.Paths;
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
@WebServlet(name = "ChangeImageUserServlet", urlPatterns = {"/restricted/ChangeImageUser"})
@MultipartConfig
public class ChangeImageUserServlet extends HttpServlet {
	
	@Inject
	private UploadHandler uploadHandler;
	
	UserDAO userDAO;
	
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
		request.getRequestDispatcher("/WEB-INF/views/auth/ChangeImageUser.jsp").forward(request, response);
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
		
		String avatarURI = "";

		Part avatar = request.getPart("image");
		if ((avatar != null) && (avatar.getSize() > 0)) {
			// Delete old image
			uploadHandler.deleteFile(user.getImage(), getServletContext());
			
			try {
				avatarURI = uploadHandler.uploadFile(avatar, UploadHandler.FILE_TYPE.AVATAR, user, getServletContext());
			} catch (IOException ex) {
				// It is not a fatal error, we ask the user to try again
				Logger.getLogger(ChangeImageUserServlet.class.getName()).log(Level.WARNING, null, ex);
				user.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
				response.sendRedirect(context + "/restricted/ChangeImageUser");
			}
			if (!response.isCommitted()) {
				user.setImage(avatarURI);
				try {
					if (userDAO.updateUser(user.getId(), user)) {
						session.setAttribute("user", user);
						response.sendRedirect(context + "restricted/InfoUser");
					}
				} catch (DaoException ex) {
					Logger.getLogger(ChangeImageUserServlet.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		} else {
			response.sendRedirect(context + "restricted/InfoUser");
		}
		
	}
	
	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
	
}
