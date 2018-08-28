/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.list;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.dummy.DAOFactoryImpl;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
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
@WebServlet(name = "NewListServlet", urlPatterns = {"/restricted/NewList"})
public class NewListServlet extends HttpServlet {
	ListsCategoryDAO listsCategoryDAO;
	ListDAO listDAO;
	UserDAO userDAO;

	@Override
	public void init() {
		DAOFactory factory = (DAOFactoryImpl) this.getServletContext().getAttribute("daoFactory");
		listsCategoryDAO = factory.getListsCategoryDAO();
		listDAO = factory.getListDAO();
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
		try {
			List<ListsCategory> listsCategory = listsCategoryDAO.getAll();
			request.setAttribute("listsCategory", listsCategory);
		} catch (DaoException ex) {
			Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
		}
		request.getRequestDispatcher("/WEB-INF/views/list/NewList.jsp").forward(request, response);
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

		HttpSession session = request.getSession(false);
		User me = (User) session.getAttribute("user");
		String name =  request.getParameter("nameList");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String description = request.getParameter("description");
		String[] shared = request.getParameterValues("shared");
		
		it.unitn.webprog2018.ueb.shoppinglist.entities.List list = new it.unitn.webprog2018.ueb.shoppinglist.entities.List();
		list.setName(name);
		list.setOwner(me);
		list.setDescription(description);
		ListsCategory listCategory = null;
		try {
			System.out.println("before getByName");
			listCategory = listsCategoryDAO.getById(categoryId);
			System.out.println("after getByName");
			list.setCategory(listCategory);

			if (shared == null || shared.length == 0) {
				Boolean valid = listDAO.addList(list);
				if (valid) {
					it.unitn.webprog2018.ueb.shoppinglist.entities.List newlist = listDAO.getList(list.getName(), me);
					System.out.println("getlist");
					path += "restricted/HomePageLogin/" + me.getId() + "/" + newlist.getId();
					response.sendRedirect(path);
				} else {
					request.setAttribute("list", list);
					request.getRequestDispatcher("/WEB-INF/views/list/NewSharedList.jsp").forward(request, response);
				}
			}

		} catch (RecordNotFoundDaoException ex) {
			System.out.println("RNFDE");
			list.setError("category", "questa categoria non esiste");
			request.setAttribute("list", list);
			request.getRequestDispatcher("/WEB-INF/views/list/NewSharedList.jsp").forward(request, response);
		} catch (DaoException ex) {
			Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
		}

		if (shared != null && shared.length != 0) {
			System.out.println("shared!");
			LinkedList<User> listashared = new LinkedList();
			for (int i = 0; i < shared.length; i++) {
				try {
					User u = userDAO.getByEmail(shared[i]);
					listashared.add(u);
				} catch (RecordNotFoundDaoException ex) {
					list.setError("shared", "l'utente " + shared[i] + " non esiste");
					request.setAttribute("list", list);
					request.getRequestDispatcher("/WEB-INF/views/list/NewSharedList.jsp").forward(request, response);
				} catch (DaoException ex) {
					Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
					response.sendError(500, ex.getMessage());
				}
			}
			try {
				Boolean valid = listDAO.addList(list);
				if (valid) {
					for (User u : listashared) {
						listDAO.linkShoppingListToUser(list, u.getId());
					}
					it.unitn.webprog2018.ueb.shoppinglist.entities.List newlist = listDAO.getList(list.getName(), me);
					path += "restricted/HomePageLogin/" + me.getId() + "/" + newlist.getId();
					response.sendRedirect(path);
				} else {
					request.setAttribute("list", list);
					request.getRequestDispatcher("/WEB-INF/views/list/NewSharedList.jsp").forward(request, response);
				}
			} catch (DaoException ex) {
				Logger.getLogger(NewListServlet.class.getName()).log(Level.SEVERE, null, ex);
				response.sendError(500, ex.getMessage());
			}
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "New list servlet";
	}

}
