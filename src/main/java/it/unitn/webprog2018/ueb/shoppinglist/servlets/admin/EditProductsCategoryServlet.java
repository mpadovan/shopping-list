/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import java.io.IOException;
import java.io.PrintWriter;
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
public class EditProductsCategoryServlet extends HttpServlet {

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
		Integer categoryId = Integer.parseInt(request.getParameter("id"));

		try {
			List<ProductsCategory> productsCategory = productsCategoryDAO.getAll();
			ProductsCategory productCategory = productsCategoryDAO.getById(categoryId);
			request.setAttribute("productsCategory", productsCategory);
			request.setAttribute("productCategory", productCategory);
			request.getRequestDispatcher("/WEB-INF/views/admin/EditProductsCategory.jsp").forward(request, response);

		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
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
		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getProductsCategoryDAO();
		Integer productCategoryId = Integer.parseInt(request.getParameter("id"));

		String name = request.getParameter("name");
		String logo = request.getParameter("logo");
		String description = request.getParameter("description");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		try {
			ProductsCategory productCategory = new ProductsCategory();
			ProductsCategory subCategory = new ProductsCategory();
			if (categoryId >= 0) {
				subCategory = productsCategoryDAO.getById(categoryId);
			} else {
				subCategory.setId(null);
			}
			productCategory.setName(name);
			productCategory.setDescription(description);
			productCategory.setLogo(logo);
			productCategory.setId(productCategoryId);
			productCategory.setCategory(subCategory);
			
			if (productsCategoryDAO.updateProductsCategory(productCategoryId,productCategory)) {
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ProductsCategory");
			} else {
				request.setAttribute("productCategory", productCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/EditProductsCategory.jsp").forward(request, response);
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Edit products category servlet";
	}
}