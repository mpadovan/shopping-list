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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
 * @author simon
 */
@WebServlet(name = "ChangeImageAdminServlet", urlPatterns = {"/restricted/admin/ChangeImageAdmin"})
@MultipartConfig
public class ChangeImageAdminServlet extends HttpServlet {

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
		String context = getServletContext().getContextPath();
		if (!context.endsWith("/")) {
			context += "/";
		}

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		String avatarURI = "";
		String avatarsFolder = (String) getServletContext().getAttribute("avatarFolder");
		String uploadFolder = (String) getServletContext().getAttribute("uploadFolder");
		
		Part avatar = request.getPart("image");
		if ((avatar != null) && (avatar.getSize() > 0)) {
			// Delete old image
			if (user.getImage() != null && !user.getImage().equals("") && !user.getImage().equals("null")) {
				File oldFile = new File(uploadFolder + user.getImage());
				if (oldFile.exists()) {
					oldFile.delete();
				}
			}
			String avatarFileName = Paths.get(avatar.getSubmittedFileName()).getFileName().toString();
			// Extension handling
			int ext = avatarFileName.lastIndexOf(".");
			int noExt = avatarFileName.lastIndexOf(File.separator);
			avatarFileName = avatarsFolder + user.getHash() + (ext > noExt ? avatarFileName.substring(ext) : "");
			try {
				InputStream fileContent = avatar.getInputStream();
				File file = new File(avatarFileName);
				if (file.exists()) {	// avoid fileAlreadyExistsException
					file.delete();
				}
				Files.copy(fileContent, file.toPath());
				avatarURI = avatarFileName.substring(avatarFileName.lastIndexOf(uploadFolder) + uploadFolder.length());
				// System.out.println(avatarURI);
				// System.out.println(avatarFileName + " \n" + avatarURI);

			} catch (IOException ex) {
				// It is not a fatal error, we ask the user to try again
				Logger.getLogger(ChangeImageAdminServlet.class.getName()).log(Level.WARNING, null, ex);
				user.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
				// allows to forward response with correct loading of accessory information from the database to be shown in the jsp.
				doGet(request, response);
			}
			if (!response.isCommitted()) {
				user.setImage(avatarURI);
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
