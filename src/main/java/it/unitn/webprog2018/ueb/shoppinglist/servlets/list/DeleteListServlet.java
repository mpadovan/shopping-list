/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.list;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
import it.unitn.webprog2018.ueb.shoppinglist.utils.UploadHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Giulia Carocari
 */
@WebServlet(name = "DeleteListServlet", urlPatterns = {"/restricted/DeleteList/*"})
public class DeleteListServlet extends HttpServlet {

	private ListDAO listDAO;

	@Inject
	private UploadHandler uploadHandler;

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
		User user = ((User) request.getSession().getAttribute("user"));
		if ((boolean) request.getAttribute("hasDeletePermission")) {
			try {
				List list = (List) request.getAttribute("currentList");
				if (listDAO.deleteList(list.getId())) {
					uploadHandler.deleteFile(list.getImage(), getServletContext());
					request.getSession().setAttribute("personalLists", listDAO.getPersonalLists(user.getId()));
					request.getSession().setAttribute("sharedLists", listDAO.getSharedLists(user.getId()));
					response.sendRedirect(this.getServletContext().getContextPath() + "/restricted/HomePageLogin/" + user.getHash());
				}
			} catch (DaoException ex) {
				HttpErrorHandler.handleDAOException(ex, response);
				Logger.getLogger(DeleteListServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
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
		return "Deletes a List";
	}// </editor-fold>

}
