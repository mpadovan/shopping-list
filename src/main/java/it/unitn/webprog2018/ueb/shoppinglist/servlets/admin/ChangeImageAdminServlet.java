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
		String avatarsFolder = getServletContext().getInitParameter("uploadFolder") + "/restricted/" + user.getId() + "/avatar/";
		Part avatar = request.getPart("image");
		if ((avatar != null) && (avatar.getSize() > 0)) {
			avatarFileName = Paths.get(avatar.getSubmittedFileName()).getFileName().toString();
			int ext = avatarFileName.lastIndexOf(".");
			int noExt = avatarFileName.lastIndexOf(File.separator);
			avatarFileName = user.getId() + (ext > noExt ? avatarFileName.substring(ext) : "");
			try (InputStream fileContent = avatar.getInputStream()) {
				file = new File(avatarFileName);
				Files.copy(fileContent, file.toPath());
				avatarURI = "localhost:8080" + context + "uploads/restricted/" + user.getId() + "/avatar/"
						+ user.getId();
				
				System.out.println(avatarFileName + " \n" + avatarURI);

			} catch (FileAlreadyExistsException ex) {
				response.sendError(500, "Server could not store your avatar, "
						+ "please retry the sign up process. "
						+ "Notice that you can also upload the image later in you user page.");
				getServletContext().log("impossible to upload the file", ex);
			}
		}
		if (!response.isCommitted()) {
			user.setImage(avatarURI);
			System.out.println("entrato");
			try {
				if(userDAO.updateUser(user.getId(), user))
				{
					session.setAttribute("user", user);
					System.out.println("tutto perfetto");
					response.sendRedirect(context+"restricted/admin/InfoAdminServlet");
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
