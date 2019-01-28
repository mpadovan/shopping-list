/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String changepassword = request.getParameter("changepassword");
		request.setAttribute("changepassword", changepassword);
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
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
			String name = request.getParameter("name");
			String lastName = request.getParameter("lastName");
			user.setName(name);
			user.setLastname(lastName);
			user.setCheckpassword(user.getPassword());
			String oldpassword = request.getParameter("oldpassword");
			if (oldpassword != null && !oldpassword.equals("")) {
				String password = request.getParameter("password");
				String checkPassword = request.getParameter("checkpassword");
				if (Sha256.doHash(oldpassword).equals(user.getPassword())) {
					user.setPassword(Sha256.doHash(password));
					user.setCheckpassword(Sha256.doHash(checkPassword));
					if (userDAO.updateUser(id, user)) {
						session.setAttribute("user", user);
						redirect = true;
					} else {
						request.setAttribute("user", user);
						request.setAttribute("changepassword", true);
						// System.out.println("validation utente modificato con password");
					}
				} else {
					user.setError("oldpassword", "la password non è corretta");
					request.setAttribute("user", user);
					request.setAttribute("changepassword", true);
				}
			} else {
				if (userDAO.updateUser(id, user)) {
					session.setAttribute("user", user);
					redirect = true;
				} else {
					request.setAttribute("user", user);
				}
			}
			if (redirect) {
				path += "restricted/admin/InfoAdmin";
				response.sendRedirect(path);
			} else {
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
