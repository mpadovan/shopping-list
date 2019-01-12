/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.list;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
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
 * @author simon
 */
@WebServlet(name = "InfoListServlet", urlPatterns = {"/restricted/InfoList/*"})
public class InfoListServlet extends HttpServlet {

	private ListDAO listDAO;

	@Override
	public void init() {
		listDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getListDAO();
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
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if ((boolean) request.getAttribute("hasViewPermission")) {
			try {
				List list = (List) request.getAttribute("currentList");
				// request.setAttribute("currentList", list);
				// request.setAttribute("hasModifyPermission", list.getOwner().equals(user) || listDAO.hasModifyPermission(list.getId(), user.getId()));
				// request.setAttribute("hasDeletePermission", list.getOwner().equals(user) || listDAO.hasDeletePermission(list.getId(), user.getId()));
				if (((java.util.List<List>) request.getAttribute("sharedLists")).contains((List) request.getAttribute("currentList"))) {
					request.setAttribute("sharedUsers", listDAO.getConnectedUsers(list.getId()));
				}
			} catch (DaoException ex) {
				HttpErrorHandler.handleDAOException(ex, response);
				Logger.getLogger(InfoListServlet.class.getName()).log(Level.SEVERE, null, ex);
				return;
			}
			request.getRequestDispatcher("/WEB-INF/views/list/InfoList.jsp").forward(request, response);
		} else {
			HttpErrorHandler.sendError401(response);
		}
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
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}

}
