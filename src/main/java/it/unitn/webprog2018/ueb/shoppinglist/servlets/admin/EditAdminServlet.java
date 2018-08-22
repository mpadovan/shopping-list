/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import com.mysql.cj.Session;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author giulia
 */
@WebServlet(name = "EditAdminServlet", urlPatterns = {"/restricted/admin/EditAdmin"})
public class EditAdminServlet extends HttpServlet {
	private UserDAO userDAO;

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
		request.getRequestDispatcher("/WEB-INF/views/admin/EditAdmin.jsp").forward(request, response);
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
		String path = getServletContext().getContextPath();
		if (!path.endsWith("/")) {
			path += "/";
		}
		Boolean redirect = false;
		HttpSession session = request.getSession();
		User usersession = (User) (session.getAttribute("user"));
		Integer id = usersession.getId();
		try {
			User user = userDAO.getById(id);
			System.out.println("email: " + user.getEmail());
			String name = request.getParameter("name");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			user.setEmail(email);
			user.setName(name);
			user.setLastname(lastName);
			String password = request.getParameter("password");
			if(password!=null && !password.equals(""))
			{
				System.out.println("password equal");
				String newPassword = request.getParameter("newPassword");
				String checkPassword = request.getParameter("checkPassword");
				if(Sha256.doHash(password).equals(user.getPassword()))
				{
					user.setPassword(Sha256.doHash(newPassword));
					user.setCheckpassword(Sha256.doHash(checkPassword));
					if(userDAO.updateUser(id, user))
					{
						session.setAttribute("user", user);
						redirect=true;
						System.out.println("utente modificato con password");
					}
					else
					{
						request.setAttribute("user", user);
						System.out.println("validation utente modificato con password");
					}
				}
				else
				{
					request.setAttribute("passworderrata", "la password non è corretta");
					System.out.println("password errata");
				}
			}
			else{
				if(userDAO.updateUser(id, user))
				{
					session.setAttribute("user", user);
					redirect=true;
					System.out.println("utente modificato senza password");
				}
				else
				{
					request.setAttribute("user", user);
					System.out.println("validation utente modificato senza password");
				}
			}
			if(redirect)
			{
				changeRememberCookie(request, response, user);
				path += "restricted/admin/InfoAdmin";
				response.sendRedirect(path);
			}
			else
			{
				request.getRequestDispatcher("/WEB-INF/views/admin/EditAdmin.jsp").forward(request, response);
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
		}
	}
	
	private void changeRememberCookie(HttpServletRequest req, HttpServletResponse resp, User user) {
		Cookie rememberCookie = getRememberCookie(req);
		if (rememberCookie != null) {
			rememberCookie.setValue(CookieCipher.encrypt(user.getEmail()));
			resp.addCookie(rememberCookie);
		}
	}
	
	private Cookie getRememberCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("remember")) {
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Edit admin servlet";
	}

}
