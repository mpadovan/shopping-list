/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
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
@WebServlet("/restricted/HomePageLogin/*")
public class HomePageLoginServlet extends HttpServlet {

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
		String uri = request.getRequestURI();
		if (!uri.endsWith("/")) {
			uri += "/";
		}
		if (uri.matches(".*/[0-9]+/[0-9]+/")) { // URI ends with user id and list id (checked by filters)
			java.util.List<List> personalLists = (java.util.List<List>) request.getSession().getAttribute("personalLists");
			if (personalLists != null) {
				for (List l : personalLists) {
					if (l.getId().equals(request.getAttribute("currentListId"))) {
						request.setAttribute("currentList", l);
					}
				}
			}
			java.util.List<List> sharedLists = (java.util.List<List>) request.getSession().getAttribute("sharedLists");
			if (sharedLists != null) {
				for (List l : (java.util.List<List>) request.getSession().getAttribute("sharedLists")) {
					if (l.getId().equals(request.getAttribute("currentListId"))) {
						System.out.println("List match");
						request.setAttribute("currentList", l);
						try {
							request.setAttribute("sharedUsers", listDAO.getConnectedUsers(l.getId()));
						} catch (DaoException ex) {
							Logger.getLogger(HomePageLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
							response.sendError(500);
						}
					}
				}
			}
		}
		request.getRequestDispatcher("/WEB-INF/views/HomePageLogin.jsp").forward(request, response);
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
		return "Home Page Login Servlet";
	}
}
