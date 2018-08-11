/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.auth;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.dummy.DAOFactoryImpl;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
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
	 * Method to be executed at servlet initialization. Handles connections with
	 * persistence layer.
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

		String path = getServletContext().getContextPath();
		if (!path.endsWith("/")) {
			path += "/";
		}

		String email = (String) request.getParameter("email");
		String password = (String) request.getParameter("password");

		User user = userDAO.getByEmail(email);
		
		if (user != null) {
			if (Sha256.checkPassword(password, user.getPassword())) {
				// session is inserted into sessionHandler (notifications)
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				if (request.getParameter("remember") != null) {
					boolean found = false;
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (Cookie cookie : request.getCookies()) {
							if (cookie.getName().equals("remember")) {
								found = true;
							}
						}
					}
					if (!found) {
						Cookie userId = new Cookie("remember", CookieCipher.encrypt(email));
						userId.setMaxAge(COOKIE_EXP);
						response.addCookie(userId);
					}
				}

				path += "restricted/HomePageLogin";
				response.sendRedirect(path);
			}
		}
		// either email or password are wrong
		if (!response.isCommitted()) {
			request.setAttribute("wrongCredentials", "true");
			request.getRequestDispatcher("/WEB-INF/views/auth/AuthFailed.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/auth/Login.jsp").forward(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Servlet for login purpose. Handles only POST requests";
	}
}
