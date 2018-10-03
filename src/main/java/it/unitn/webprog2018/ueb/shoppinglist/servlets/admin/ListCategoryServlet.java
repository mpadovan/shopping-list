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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giulia
 */
@WebServlet(name = "ListCategoryServlet", urlPatterns = {"/restricted/admin/ListCategory"})
public class ListCategoryServlet extends HttpServlet {
	
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
		ListsCategoryDAO listsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getListsCategoryDAO();
		ListsCategoryImagesDAO listsCategoryImagesDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getListsCategoryImageDAO();
		String searchParam = request.getParameter("search");
		Integer checkParam = 0;
		
		List<ListsCategory> listsCategory = null;
		List<ListsCategoriesImage> listsCategoryImage = null;
		if (searchParam == null) {
			searchParam = "";
		}
		if (searchParam.equals("")) {
			checkParam = 0;
			try {
				listsCategory = listsCategoryDAO.getAll();
				listsCategoryImage = listsCategoryImagesDAO.getAll();
			} catch (DaoException ex) {
				Logger.getLogger(ListCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			checkParam = 1;
			try {
				listsCategory = listsCategoryDAO.getFromQuery(searchParam);
				
			} catch (DaoException ex) {
				Logger.getLogger(ListCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		request.setAttribute("listsCategory", listsCategory);
		request.setAttribute("listsCategoryImage", listsCategoryImage);
		request.setAttribute("searchParam", searchParam);
		request.setAttribute("checkParam", checkParam);
		request.getRequestDispatcher("/WEB-INF/views/admin/CategoryList.jsp").forward(request, response);
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
		return "Servlet for admin category list";
	}
}
