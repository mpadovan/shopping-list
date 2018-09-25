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
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author simon
 */
@WebServlet(name = "SetNewPasswordServlet", urlPatterns = {"/SetNewPassword"})
public class SetNewPasswordServlet extends HttpServlet {
	
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
		request.getRequestDispatcher("/WEB-INF/views/auth/SetNewPassword.jsp").forward(request, response);
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
		String context = getServletContext().getContextPath();
		if (!context.endsWith("/")) {
			context += "/";
		}
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		String token = request.getParameter("token");
		String password = Sha256.doHash(request.getParameter("password"));
		String checkPassword = Sha256.doHash(request.getParameter("checkPassword"));
		User user = null;
		try {
			user = userDAO.getById(id);
			System.out.println(user.getTokenpassword() +" " + token);
			if(user.getTokenpassword().equals(token) && token!=null)
			{
				user.setPassword(password);
				user.setCheckpassword(checkPassword);
				
				if(userDAO.updateUser(id, user))
				{
					user.setTokenpassword(null);
					userDAO.updateUser(user.getId(), user);
					request.getRequestDispatcher("/WEB-INF/views/auth/ConfirmChangePassword.jsp").forward(request, response);
				}
				else
				{
					request.setAttribute("user", user);
					request.getRequestDispatcher("/WEB-INF/views/auth/SetNewPassword.jsp").forward(request, response);
				}
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(SetNewPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		}
		catch (DaoException ex) {
			Logger.getLogger(SetNewPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
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
