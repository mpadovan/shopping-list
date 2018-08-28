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
		String avatarsFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "avatar" + File.separator;
		Part avatar = request.getPart("image");
		if ((avatar != null) && (avatar.getSize() > 0)) {
			avatarFileName = Paths.get(avatar.getSubmittedFileName()).getFileName().toString();
			int ext = avatarFileName.lastIndexOf(".");
			int noExt = avatarFileName.lastIndexOf(File.separator);
			avatarFileName = avatarsFolder + user.getId() + (ext > noExt ? avatarFileName.substring(ext) : "");
			InputStream fileContent = null;
			try {
				ext = avatarFileName.lastIndexOf(".");
				noExt = avatarFileName.lastIndexOf(File.separator);
				fileContent = avatar.getInputStream();
				file = new File(avatarFileName);
				Files.copy(fileContent, file.toPath());
				avatarURI = File.separator + "uploads" + File.separator + "restricted" + File.separator + user.getId() + File.separator + "avatar" +
					File.separator	+ user.getId()+ (ext > noExt ? avatarFileName.substring(ext) : "");
				
				System.out.println(avatarFileName + " \n" + avatarURI);

			} catch (FileAlreadyExistsException ex) {
				file.delete();
				Files.copy(fileContent, file.toPath());
				avatarURI = File.separator + "uploads" + File.separator + "restricted" + File.separator + user.getId() + File.separator + "avatar" +
					File.separator	+ user.getId()+ (ext > noExt ? avatarFileName.substring(ext) : "");
				
			}
		}
		if (!response.isCommitted()) {
			user.setImage(avatarURI);
			try {
				if(userDAO.updateUser(user.getId(), user))
				{
					session.setAttribute("user", user);
					response.sendRedirect(context+"restricted/admin/InfoAdmin");
				}
			} catch (DaoException ex) {
				Logger.getLogger(ChangeImageAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
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
