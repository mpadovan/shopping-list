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
import java.io.IOException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that confirms an account and persists the new user.
 *
 * @author Giulia Carocari
 */
@WebServlet("/AccountConfirmation")
public class AccountConfirmationServlet extends HttpServlet {

	private UserDAO userDAO;
	private TokenDAO tokenDAO;

	/**
	 * Method to be executed at servlet initialization. Handles connections with
	 * persistence layer.
	 */
	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		userDAO = factory.getUserDAO();
		tokenDAO = factory.getTokenDAO();
	}

	/**
	 * Handles the HTTP <code>GET</code> method. Confirms the account and
	 * persists the user.
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
		String path = this.getServletContext().getContextPath();
		if (!path.endsWith("/")) {
			path += "/";
		}

		String tokenString = request.getParameter("token");

		Token token = tokenDAO.getByTokenString(tokenString);
		if (token == null) {
			response.sendError(403, "Token non valido");
		} else if (isExpired(token)) {
			tokenDAO.removeToken(token);
			response.sendError(403, "Token scaduto");
		}
		if (!response.isCommitted()) {	// If token is null then response is committed
			User user = token.getUser();
			try {
				user.setCheckpassword(user.getPassword());
				if (userDAO.addUser(user)) {
					tokenDAO.removeToken(token);
					path += "Login";
					response.sendRedirect(path);
				} else {
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

	/**
	 * Checks if an account confirmation timer is expired.
	 *
	 * @param token
	 * @return
	 */
	private boolean isExpired(Token token) {
		java.util.Date utilDate = new java.util.Date();
		Date now = new Date(utilDate.getTime());
		return !token.getExpirationDate().after(now);
	}
}
