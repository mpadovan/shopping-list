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
import java.io.File;
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
public class DeleteProductsCategoryServlet extends HttpServlet {

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
			ProductsCategory productsCategory = productsCategoryDAO.getById(categoryId);
			if (productsCategoryDAO.deleteProductsCategory(productsCategory.getId())) {
				if (productsCategory.getLogo() != null && !productsCategory.getLogo().equals("") && !productsCategory.getLogo().equals("null")) {
					String logoFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "public" + File.separator + "productCategoryLogo" + File.separator;
					int ext = productsCategory.getLogo().lastIndexOf(".");
					File file = new File(logoFolder + productsCategory.getId() + productsCategory.getLogo().substring(ext));
					file.delete();
				}
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ProductsCategory");
			} else {
				request.setAttribute("productsCategory", productsCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/ProductList.jsp").forward(request, response);
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(DeleteProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(DeleteProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Edit products category servlet";
	}

}
