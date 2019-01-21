/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.auth;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.EmailSender;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Network;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles requests to reset a user's password.
 *
 * @author simon
 */
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/ResetPassword"})
public class ResetPasswordServlet extends HttpServlet {

	private UserDAO userDAO;

	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		userDAO = factory.getUserDAO();
	}

	/**
	 * Handles the HTTP <code>GET</code> method. IForwards the request to the ResetPassword
	 * jsp.
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
		request.getRequestDispatcher("/WEB-INF/views/auth/ResetPassword.jsp").forward(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method. Reads the email associated
	 * with the account and sends a reset password email.
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
		String email = (String) request.getParameter("email");

		User user = null;
		try {
			try {
				user = userDAO.getByEmail(email);
				user.setCheckpassword(user.getPassword());
			} catch (RecordNotFoundDaoException ex) {
				request.setAttribute("emailnotfound", true);
				request.getRequestDispatcher("/WEB-INF/views/auth/ResetPassword.jsp").forward(request, response);
			}
			String token = UUID.randomUUID().toString();
			// System.out.println("Token: " + token);
			user.setTokenpassword(token);
			try {
				userDAO.setToken(user);
				String link = "http://" + Network.getServerAddress() + context + "SetNewPassword?id=" + user.getId() + "&token=" + token;
				if (EmailSender.send(user.getEmail(), "Reset Password",
						"Hello " + user.getName() + ", you requested to reset your password.\nPlease click on the following link to reset it:\n" + link)) {
					request.getRequestDispatcher("/WEB-INF/views/auth/ConfirmSendEmail.jsp").forward(request, response);
				} else {
					response.sendError(500, "The server could not reach your email address. Please try again later.");
				}
			} catch (RecordNotFoundDaoException ex) {
				Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
				response.sendError(404, ex.getMessage());
			}
		} catch (DaoException ex) {
			Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
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
