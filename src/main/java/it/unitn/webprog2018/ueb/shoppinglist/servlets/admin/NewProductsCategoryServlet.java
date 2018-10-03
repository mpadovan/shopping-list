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
import it.unitn.webprog2018.ueb.shoppinglist.utils.UploadHandler;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author giulia
 */
@MultipartConfig
@WebServlet(name = "NewProductsCategoryServlet", urlPatterns = {"/restricted/admin/NewProductsCategory"})
public class NewProductsCategoryServlet extends HttpServlet {

	@Inject
	UploadHandler uploadHandler;

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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String context = getServletContext().getContextPath();
		if (!context.endsWith("/")) {
			context += "/";
		}

		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getProductsCategoryDAO();
		// parametri stringhe
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		//parametri file
		String logoURI = "";
		Part logo = request.getPart("logo");
		
		ProductsCategory productCategory = new ProductsCategory();
		try {
			
			productCategory.setName(name);
			productCategory.setDescription(description);
			
			if (productsCategoryDAO.addProductsCategory(productCategory)) {
				//upload logo
				if ((logo != null) && (logo.getSize() > 0)) {
					try {

						logoURI = uploadHandler.uploadFile(logo, UploadHandler.FILE_TYPE.PRODUCT_CATEGORY_LOGO, productCategory, getServletContext());
					} catch (IOException ex) {
						// It is not a fatal error, we ask the user to try again
						Logger.getLogger(NewProductsCategoryServlet.class.getName()).log(Level.WARNING, null, ex);
						productCategory.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
						// allows to forward response with correct loading of accessory information from the database to be shown in the jsp.
						request.setAttribute("productCategory", productCategory);
						doGet(request, response);
					}
					if (!response.isCommitted()) {
						productCategory.setLogo(logoURI);
						if (!productsCategoryDAO.updateProductsCategory(productCategory.getId(), productCategory)) {
							response.sendError(500, "Qualcosa è andato storto. Non è stato possibile aggiornare logo");
						}
					}
				}
				if (!response.isCommitted()) {
					response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ProductsCategory");
				}
			} else {
				request.setAttribute("productCategory", productCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/NewCategoryProduct.jsp").forward(request, response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(NewProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
