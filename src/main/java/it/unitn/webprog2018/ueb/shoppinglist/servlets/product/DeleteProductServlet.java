/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.product;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.dummy.DAOFactoryImpl;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.servlets.admin.DeletePublicProductServlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "DeleteProductServlet", urlPatterns = {"/restricted/DeleteProduct"})
public class DeleteProductServlet extends HttpServlet {

	private ProductDAO productDAO;

	/**
	 * Method to be executed at servlet initialization. Handles connections with
	 * persistence layer.
	 */
	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		productDAO = factory.getProductDAO();
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
		Integer productId = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		try {
			Product product = productDAO.getProduct(productId);
			if (productDAO.deleteProduct(product.getId())) {
				if(product.getPhotography()!=null && !product.getPhotography().equals(""))
				{
					String imageFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productImage" + File.separator;
					int ext = product.getPhotography().lastIndexOf(".");
					File file = new File(imageFolder + product.getId() + product.getPhotography().substring(ext));
					file.delete();
				}
				if(product.getLogo()!=null && !product.getLogo().equals(""))
				{
					String logoFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productLogo" + File.separator;
					int ext = product.getLogo().lastIndexOf(".");
					File file = new File(logoFolder + product.getId() + product.getLogo().substring(ext));
					file.delete();
				}
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/ProductList");
			} else {
				request.setAttribute("product", product);
				request.getRequestDispatcher("/WEB-INF/views/product/ProductList.jsp").forward(request, response);
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(DeleteProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(DeleteProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Edit custom product servlet";
	}

}
