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
public class NewProductsCategoryServlet extends HttpServlet {
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
		try {
			List<ProductsCategory> productsCategory = productsCategoryDAO.getAll();
			request.setAttribute("productsCategory", productsCategory);
			request.getRequestDispatcher("/WEB-INF/views/admin/NewCategoryProduct.jsp").forward(request, response);
		} catch (DaoException ex) {
			Logger.getLogger(NewProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		String name = request.getParameter("name");
		String logo = request.getParameter("logo");
		String description = request.getParameter("description");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		try {
			ProductsCategory productCategory = new ProductsCategory();
			ProductsCategory subCategory = new ProductsCategory();
			if(categoryId >= 0){
				subCategory = productsCategoryDAO.getById(categoryId);
			} else {
				subCategory.setId(null);
			}
			productCategory.setName(name);
			productCategory.setLogo(logo);
			productCategory.setDescription(description);
			productCategory.setCategory(subCategory);
			
			if (productsCategoryDAO.addProductsCategory(productCategory)) {
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ProductsCategory");
			} else {
				request.setAttribute("productCategory", productCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/NewProductsCategory.jsp").forward(request, response);
			}
			
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(NewProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		}catch (DaoException ex) {
			Logger.getLogger(NewPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "New category product servlet";
	}
}
