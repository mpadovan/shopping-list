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
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.UploadHandler;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author giulia
 */
@MultipartConfig
@WebServlet(name = "NewPublicProductServlet", urlPatterns = {"/restricted/admin/NewPublicProduct"})
public class NewPublicProductServlet extends HttpServlet {

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
		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getProductsCategoryDAO();
		try {
			List<ProductsCategory> productsCategory = productsCategoryDAO.getAll();
			PublicProduct product = new PublicProduct();
			request.setAttribute("productsCategory", productsCategory);
			request.setAttribute("product", product);
			request.getRequestDispatcher("/WEB-INF/views/admin/NewPublicProduct.jsp").forward(request, response);
		} catch (DaoException ex) {
			Logger.getLogger(NewPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		PublicProductDAO publicProductDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getPublicProductDAO();

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		//parametri stringhe
		String name = request.getParameter("name");
		//String logo = request.getParameter("logo");
		String note = request.getParameter("note");
		//String photography = request.getParameter("photography");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));

		//parametri file
		String logoURI = "";
		String imageURI = "";
		
		Part logo = request.getPart("logo");
		Part image = request.getPart("image");

		PublicProduct product = new PublicProduct();
		try {
			try {
				ProductsCategory productsCategory = productsCategoryDAO.getById(categoryId);
				productsCategory.setId(categoryId);
				product.setName(name);
				product.setNote(note);
				product.setCategory(productsCategory);
			} catch (RecordNotFoundDaoException ex) {
				Logger.getLogger(NewPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
				response.sendError(404, ex.getMessage());
			}

			if (publicProductDAO.addProductWithId(product)) {
				// logo upload
				if ((logo != null) && (logo.getSize() > 0)) {
					try {
						logoURI = uploadHandler.uploadFile(logo, UploadHandler.FILE_TYPE.PUBLIC_PRODUCT_LOGO, product, getServletContext());
					} catch (IOException ex) {
						// It is not a fatal error, we ask the user to try again
						Logger.getLogger(NewPublicProductServlet.class.getName()).log(Level.WARNING, null, ex);
						product.setError("logo", "Non è stato possibile salvare il logo, riprova più tardi o contatta un amministratore");
						// allows to forward response with correct loading of accessory information from the database to be shown in the jsp.
						request.setAttribute("product", product);
						doGet(request, response);
					}
					product.setLogo(logoURI);
				}
				if (!response.isCommitted()) {
					// image upload
					if ((image != null) && (image.getSize() > 0)) {
						try {
							imageURI = uploadHandler.uploadFile(image, UploadHandler.FILE_TYPE.PUBLIC_PRODUCT_IMAGE, product, getServletContext());
						} catch (FileAlreadyExistsException ex) {
							// It is not a fatal error, we ask the user to try again
							Logger.getLogger(NewPublicProductServlet.class.getName()).log(Level.WARNING, null, ex);
							product.setError("logo", "Non è stato possibile salvare il logo, riprova più tardi o contatta un amministratore");
							// allows to forward response with correct loading of accessory information from the database to be shown in the jsp.
							request.setAttribute("product", product);
							doGet(request, response);
						}
						product.setPhotography(imageURI);
					}
					if (!response.isCommitted()) {
						publicProductDAO.updateProduct(product.getId(), product);
						response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/PublicProductList");
					}
				}
			} else {
				request.setAttribute("product", product);
				// request.getRequestDispatcher("/WEB-INF/views/admin/NewPublicProduct.jsp").forward(request, response);
				doGet(request, response);
			}

		} catch (DaoException ex) {
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
		return "New public product servlet";
	}

}
