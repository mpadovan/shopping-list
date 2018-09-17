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
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
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
		File file = null;
		String avatarFileName = "";
		String avatarsFolder = (String) getServletContext().getAttribute("avatarFolder");
		String uploadFolder = (String) getServletContext().getAttribute("uploadFolder");
		/*
		//crezione cartella se non esiste
		String uploadFolder = getServletContext().getInitParameter("uploadFolder");
		File userDir = new File(uploadFolder + File.separator + "restricted" + File.separator + user.getId());
		File avatarDir = new File(userDir.getAbsolutePath() + File.separator + "avatar");
		if (!userDir.exists()) {
			if (!userDir.mkdir()) {
				response.sendError(500, "impossibile creare cartella per utente");
			}
		}
		if (!avatarDir.exists()) {
			if (!avatarDir.mkdir()) {
				response.sendError(500, "impossibile creare cartella per utente");
			}
		}
		*/
		Part avatar = request.getPart("image");
		if ((avatar != null) && (avatar.getSize() > 0)) {
			// System.out.println("entrato");
			if (user.getImage() != null && !user.getImage().equals("") && !user.getImage().equals("null")) {
				// System.out.println("entrato2");
				// String imageFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "avatar" + File.separator;
				// int extold = user.getImage().lastIndexOf(".");
				File oldFile = new File(uploadFolder + user.getImage());
				if (oldFile.exists()) {
					oldFile.delete();
					System.out.println("entrato3");
				}
			}
			avatarFileName = Paths.get(avatar.getSubmittedFileName()).getFileName().toString();
			// Extension handling
			int ext = avatarFileName.lastIndexOf(".");
			int noExt = avatarFileName.lastIndexOf(File.separator);
			avatarFileName = avatarsFolder + user.getHash()+ (ext > noExt ? avatarFileName.substring(ext) : "");
			InputStream fileContent = null;
			try {
				fileContent = avatar.getInputStream();
				file = new File(avatarFileName);
				Files.copy(fileContent, file.toPath());
				avatarURI = avatarFileName.substring(avatarFileName.lastIndexOf(uploadFolder) + uploadFolder.length());

				System.out.println(avatarFileName + " \n" + avatarURI);

			} catch (FileAlreadyExistsException ex) {
				Logger.getLogger(ChangeImageAdminServlet.class.getName()).log(Level.WARNING, null, ex);
				user.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
				response.sendRedirect("/restricted/admin/ChangeImageAdmin");
			}
			
			user.setImage(avatarURI);
			try {
				if (userDAO.updateUser(user.getId(), user)) {
					session.setAttribute("user", user);
					response.sendRedirect(context + "restricted/admin/InfoAdmin");
				}
			} catch (DaoException ex) {
				Logger.getLogger(ChangeImageAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Short description";
	}// </editor-fold>

}
