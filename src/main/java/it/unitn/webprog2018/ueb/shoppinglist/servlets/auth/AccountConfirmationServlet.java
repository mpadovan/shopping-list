/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.auth;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that confirms an account and persists the new user
 *
 * @author Giulia Carocari
 */
public class AccountConfirmationServlet extends HttpServlet {

	private UserDAO userDAO;
	private TokenDAO tokenDAO;
	
	/**
	 * Method to be executed at servlet initialization.
	 * Handles connections with persistence layer.
	 */
	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		userDAO = factory.getUserDAO();
		tokenDAO = factory.getTokenDAO();
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

		String path = this.getServletContext().getContextPath();
		if (!path.endsWith("/")) {
			path += "/";
		}

		String tokenString = request.getParameter("token");

		Token token = tokenDAO.getByTokenString(tokenString);
		
		if (token == null) {
			// TODO edit to correct page
			path += "invalidToken.html";
			response.sendRedirect(path);
		} else if (isExpired(token)) {
			tokenDAO.removeToken(token);
			// TODO redirect to error page
			path += "expiredToken.html";
			response.sendRedirect(path);
		}
		if (!response.isCommitted()) {
			User user = token.getUser();
			try {
				if(userDAO.addUser(user))
				{
					String avatarName = user.getImage().substring(user.getImage().lastIndexOf("/") + 1);
			
					String uploadFolder = getServletContext().getInitParameter("uploadFolder");
					File src = new File(uploadFolder + "/restricted/tmp/" + avatarName);
					String avatarName2 = avatarName.replaceFirst(user.getEmail(), user.getId().toString());
					File dest = new File(uploadFolder + "/restricted/avatar/" + avatarName2);
					Files.copy(src.toPath(), dest.toPath());
					src.delete();

					tokenDAO.removeToken(token);
					path += "Login";
					response.sendRedirect(path);
				}
				else
				{
					request.setAttribute("user", user);
					request.getRequestDispatcher("/WEB-INF/views/auth/SignUp.jsp").forward(request, response);
				}
			} catch (DaoException ex) {
				Logger.getLogger(AccountConfirmationServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Servlet that confirms an account given the token the user is sent via email."
				+ "Only handles GET requests";
	}

	private boolean isExpired(Token token) {
		java.util.Date utilDate = new java.util.Date();
		Date now = new Date(utilDate.getTime());
		return !token.getExpirationDate().after(now);
	}
}
