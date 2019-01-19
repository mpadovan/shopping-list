/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.product;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.UploadHandler;
import java.io.IOException;
import java.nio.file.*;
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
 * Servlet that handles the editing of a personal product.
 *
 * @author simon
 */
@MultipartConfig
@WebServlet(name = "EditProductServlet", urlPatterns = {"/restricted/permission/EditProduct"})
public class EditProductServlet extends HttpServlet {

	private ProductDAO productDAO;
	private ProductsCategoryDAO productsCategoryDAO;

	@Inject
	private UploadHandler uploadHandler;

	/**
	 * Method to be executed at servlet initialization. Handles connections with
	 * persistence layer.
	 */
	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		productDAO = factory.getProductDAO();
		productsCategoryDAO = factory.getProductsCategoryDAO();
	}

	/**
	 * Handles the HTTP <code>GET</code> method. It loads the required
	 * information from the database and forwards the request to the EditProduct
	 * jsp.
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
		Integer productId = Integer.parseInt(request.getParameter("id"));

		try {
			Product product = productDAO.getProduct(productId);
			InitializeCategoryRedirect(request, response, product);
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method. Persists the new information
	 * about the personal product. It takes charge of handling also the file
	 * upload and deletion.
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
		String name = request.getParameter("name");
		Part logo = request.getPart("logo");
		Part photography = request.getPart("image");
		String note = request.getParameter("note");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		Integer productId = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		//parametri file
		String logoURI = "";
		String imageURI = "";
		try {
			Product product = productDAO.getProduct(productId);
			ProductsCategory productCategory = productsCategoryDAO.getById(categoryId);
			productCategory.setId(categoryId);
			product.setCategory(productCategory);
			product.setName(name);
			product.setNote(note);
			if ((logo != null) && (logo.getSize() > 0)) {
				// Delete old logo
				uploadHandler.deleteFile(product.getLogo(), getServletContext());

				// Save new logo
				try {
					logoURI = uploadHandler.uploadFile(logo, UploadHandler.FILE_TYPE.PRODUCT_LOGO, product, getServletContext());
				} catch (FileAlreadyExistsException | NoSuchFileException ex) {
					// It is not a fatal error, we ask the user to try again
					Logger.getLogger(EditProductServlet.class.getName()).log(Level.WARNING, null, ex);
					product.setError("logo", "Non è stato possibile salvare il logo, riprova più tardi o contatta un amministratore");
					request.setAttribute("product", product);
					doGet(request, response);
				}
				product.setLogo(logoURI);
			}
			if ((photography != null) && (photography.getSize() > 0)) {
				// delete old photography
				uploadHandler.deleteFile(product.getPhotography(), getServletContext());

				// save new photo
				try {
					imageURI = uploadHandler.uploadFile(photography, UploadHandler.FILE_TYPE.PRODUCT_IMAGE, product, getServletContext());
				} catch (IOException ex) {
					// It is not a fatal error, we ask the user to try again
					Logger.getLogger(EditProductServlet.class.getName()).log(Level.WARNING, null, ex);
					product.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
					request.setAttribute("product", product);
					doGet(request, response);
				}
				product.setPhotography(imageURI);
			}
			if (!response.isCommitted()) { // everything went well, we can persist the changes
				if (product.isVaildOnUpdate((DAOFactory) getServletContext().getAttribute("daoFactory"))
						&& productDAO.updateProduct(productId, product)) {
					response.sendRedirect(getServletContext().getContextPath() + "/restricted/InfoProduct?id=" + productId);
				} else {
					request.setAttribute("product", product);
					InitializeCategoryRedirect(request, response, product);
				}
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
		}

	}

	private void InitializeCategoryRedirect(HttpServletRequest request, HttpServletResponse response, Product product) throws DaoException, ServletException, IOException {
		List<ProductsCategory> productsCategory = productsCategoryDAO.getAll();
		request.setAttribute("productsCategory", productsCategory);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/WEB-INF/views/product/EditProduct.jsp").forward(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Edit Product Servlet";
	}
}
