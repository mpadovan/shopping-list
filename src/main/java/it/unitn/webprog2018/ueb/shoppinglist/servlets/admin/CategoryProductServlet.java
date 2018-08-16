/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import java.io.IOException;
import java.util.List;
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
public class CategoryProductServlet extends HttpServlet {

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
		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getProductsCategoryDAO();
		String searchParam = request.getParameter("search");
		Integer checkParam = 0;

		List<ProductsCategory> productsCategory = null;
		if (searchParam == null) {
			searchParam = "";
		}
		if (searchParam.equals("")) {
			try {
				checkParam = 0;
				productsCategory = productsCategoryDAO.getAll();
			} catch (DaoException ex) {
				Logger.getLogger(CategoryProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			try {
				checkParam = 1;
				productsCategory = productsCategoryDAO.getFromQuery(searchParam);
			} catch (DaoException ex) {
				Logger.getLogger(CategoryProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		request.setAttribute("productsCategory", productsCategory);
		request.setAttribute("searchParam", searchParam);
		request.setAttribute("checkParam", checkParam);
		request.getRequestDispatcher("/WEB-INF/views/admin/CategoryProduct.jsp").forward(request, response);
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
		return "Servlet for admin category product";
	}
}
