/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
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
@WebServlet(name = "DeletePublicProductServlet", urlPatterns = {"/restricted/admin/DeletePublicProduct"})
public class DeletePublicProductServlet extends HttpServlet {
	
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
		PublicProductDAO publicProductDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getPublicProductDAO();
		Integer productId = Integer.parseInt(request.getParameter("id"));
		try {
			PublicProduct publicProduct = publicProductDAO.getById(productId);
			if (publicProductDAO.deleteProduct(publicProduct.getId())) {
				if(publicProduct.getPhotography()!=null && !publicProduct.getPhotography().equals(""))
				{
					String imageFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "public" + File.separator + "productImage" + File.separator;
					int ext = publicProduct.getPhotography().lastIndexOf(".");
					File file = new File(imageFolder + publicProduct.getId() + publicProduct.getPhotography().substring(ext));
					file.delete();
				}
				if(publicProduct.getLogo()!=null && !publicProduct.getLogo().equals(""))
				{
					String logoFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "public" + File.separator + "productLogo" + File.separator;
					int ext = publicProduct.getLogo().lastIndexOf(".");
					File file = new File(logoFolder + publicProduct.getId() + publicProduct.getLogo().substring(ext));
					file.delete();
				}
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/PublicProductList");
			} else {
				request.setAttribute("product", publicProduct);
				request.getRequestDispatcher("/WEB-INF/views/admin/ProductList.jsp").forward(request, response);
			}
		} catch (RecordNotFoundDaoException ex){
			Logger.getLogger(DeletePublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		}catch (DaoException ex) {
			Logger.getLogger(DeletePublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Delete public product servlet";
	}
	
}
