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
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giulia
 */
@MultipartConfig
@WebServlet(name = "EditPublicProductServlet", urlPatterns = {"/restricted/admin/EditPublicProduct"})
public class EditPublicProductServlet extends HttpServlet {

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
		PublicProductDAO publicProductDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getPublicProductDAO();
		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getProductsCategoryDAO();
		Integer productId = Integer.parseInt(request.getParameter("id"));

		try {
			PublicProduct publicProduct = publicProductDAO.getById(productId);
			List<ProductsCategory> productsCategory = productsCategoryDAO.getAll();
			request.setAttribute("productsCategory", productsCategory);
			request.setAttribute("product", publicProduct);
			request.getRequestDispatcher("/WEB-INF/views/admin/EditPublicProduct.jsp").forward(request, response);
		} catch (RecordNotFoundDaoException ex){
			Logger.getLogger(EditPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		PublicProductDAO publicProductDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getPublicProductDAO();
		Integer productId = Integer.parseInt(request.getParameter("id"));
		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getProductsCategoryDAO();

		String name = request.getParameter("name");
		String logo = request.getParameter("logo");
		String note = request.getParameter("note");
		String photography = request.getParameter("photography");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));

		try {
			PublicProduct product = publicProductDAO.getById(productId);
			ProductsCategory productsCategory = productsCategoryDAO.getById(categoryId);
			productsCategory.setId(categoryId);
			product.setName(name);
			product.setLogo(logo);
			product.setNote(note);
			product.setPhotography(photography);
			product.setCategory(productsCategory);
			if (publicProductDAO.updateProduct(productId,product)) {
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/PublicProductList");
			} else {
				request.setAttribute("product", product);
				request.getRequestDispatcher("/WEB-INF/views/admin/EditPublicProduct.jsp").forward(request, response);
			}
		}
		catch (RecordNotFoundDaoException ex){
			Logger.getLogger(EditPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		}catch (DaoException ex) {
			Logger.getLogger(EditPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Edit public product servlet";
	}

}
