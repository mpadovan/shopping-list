/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategoriesImage;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
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
 * @author giulia
 */
public class NewListsCategoryServlet extends HttpServlet {

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
		request.getRequestDispatcher("/WEB-INF/views/admin/NewListsCategory.jsp").forward(request, response);
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
		ListsCategoryDAO listsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getListsCategoryDAO();
		ListsCategoryImagesDAO listsCategoryImagesDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getListsCategoryImageDAO();
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String image = request.getParameter("image");
		
		ListsCategory listsCategory = new ListsCategory();
		listsCategory.setName(name);
		listsCategory.setDescription(description);
		ListsCategoriesImage listsCategoriesImage = new ListsCategoriesImage();
		listsCategoriesImage.setImage(image);
		
		try {
			if (listsCategoryDAO.addListCategory(listsCategory)) {
				listsCategory = listsCategoryDAO.getByName(name);
				listsCategoriesImage.setCategory(listsCategory);
				if(listsCategoryImagesDAO.addListsCategoriesImage(listsCategoriesImage)){
					response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ListCategory");
				}
				
			} else {
				request.setAttribute("listsCategory", listsCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/NewListsCategory.jsp").forward(request, response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(NewListsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
	}

}