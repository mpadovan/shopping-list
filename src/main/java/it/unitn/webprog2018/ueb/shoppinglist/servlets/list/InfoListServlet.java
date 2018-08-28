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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author simon
 */
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
		User user = (User) request.getSession().getAttribute("user");
		try {
			List list = listDAO.getList((Integer) request.getAttribute("currentListId"));
			request.setAttribute("currentList", list);
			request.setAttribute("sharedUsers", listDAO.getConnectedUsers(list.getId()));
			request.setAttribute("hasModifyPermission", listDAO.hasModifyPermission(list.getId(), user.getId()));
			request.setAttribute("hasDeletePermission", listDAO.hasDeletePermission(list.getId(), user.getId()));
		} catch (DaoException ex) {
			response.sendError(500);
			Logger.getLogger(InfoListServlet.class.getName()).log(Level.SEVERE, null, ex);
			return;
		}
		request.getRequestDispatcher("/WEB-INF/views/list/InfoList.jsp").forward(request, response);
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
