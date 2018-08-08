/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.auth;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.dummy.DAOFactoryImpl;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * HttpServlet for login handling
 *
 * @author Giulia Carocari
 */
public class LoginServlet extends HttpServlet {

	private static final int COOKIE_EXP = 60 * 60 * 24 * 7;	// 7 days in seconds
	private UserDAO userDAO;
	
	/**
	 * Method to be executed at servlet initialization.
	 * Handles connections with persistence layer.
	 */
	@Override
	public void init() {
		DAOFactory factory = (DAOFactoryImpl) this.getServletContext().getAttribute("daoFactory");
		userDAO = factory.getUserDAO();
	}

	/**
	 * Handles the HTTP <code>POST</code> method. Logs the user and creates a
	 * session for her.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = (String) request.getParameter("email");
		String password = (String) request.getParameter("password");

		User user = userDAO.getByEmail(email);

		if (user != null) {
			if (user.getPassword().equals(password)) {	// TODO add password encription
				// session is inserted into sessionHandler (notifications)
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				if (request.getParameter("remember") != null) {
					Cookie userId = new Cookie("remember", CookieCipher.encrypt(email));
					// Keeps you signed in for one week
					userId.setMaxAge(COOKIE_EXP);
					response.addCookie(userId);
				}

				// TODO redirect to user page for successful login
				response.sendRedirect("LoginOK.html");
			}
		}
		// either email or password are wrong
		if (!response.isCommitted()) {
			// TODO redirect to login page in case password is wrong
			request.setAttribute("wrongCredentials", "true");
			response.sendRedirect("login.html");
		}
	}

	/**
	 * value Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Servlet for login purpose. Handles only POST requests";
	}
}
