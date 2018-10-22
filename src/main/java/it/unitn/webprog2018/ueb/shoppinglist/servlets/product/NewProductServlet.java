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
import it.unitn.webprog2018.ueb.shoppinglist.servlets.admin.NewPublicProductServlet;
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
 * @author simon
 */
@MultipartConfig
@WebServlet(name = "NewProductServlet", urlPatterns = {"/restricted/NewProduct"})
public class NewProductServlet extends HttpServlet {

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
		try {
			List<ProductsCategory> productsCategory = productsCategoryDAO.getAll();
			request.setAttribute("productsCategory", productsCategory);
			request.getRequestDispatcher("/WEB-INF/views/product/NewProduct.jsp").forward(request, response);
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		//parametri stringhe
		String name = (String) request.getParameter("name");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String note = request.getParameter("note");

		//parametri file
		String logoURI = "";
		String imageURI = "";

		Part logo = request.getPart("logo");
		Part photography = request.getPart("image");

		Product product = new Product();
		try {
			try {
				ProductsCategory productsCategory = productsCategoryDAO.getById(categoryId);
				productsCategory.setId(categoryId);
				product.setOwner(user);
				product.setName(name);
				product.setNote(note);
				product.setCategory(productsCategory);
			} catch (RecordNotFoundDaoException ex) {
				Logger.getLogger(NewProductServlet.class.getName()).log(Level.SEVERE, null, ex);
				response.sendError(404, ex.getMessage());
			}
			// If the product passes validation and is correctly added to the database (see: lazy evaluation)
			if (product.isVaildOnCreate((DAOFactory) getServletContext().getAttribute("daoFactory"))
					&& productDAO.addProductWithId(product)) {
				//upload logo
				if ((logo != null) && (logo.getSize() > 0)) {
					try {
						logoURI = uploadHandler.uploadFile(logo, UploadHandler.FILE_TYPE.PRODUCT_LOGO, product, getServletContext());
					} catch (IOException ex) {
						// It is not a fatal error, we ask the user to try again
						Logger.getLogger(NewProductServlet.class.getName()).log(Level.WARNING, null, ex);
						product.setError("logo", "Non è stato possibile salvare il logo, riprova più tardi o contatta un amministratore");
						request.setAttribute("product", product);
						doGet(request, response);
					}
					product.setLogo(logoURI);
				}

				if (!response.isCommitted()) {
					//upload image
					if ((photography != null) && (photography.getSize() > 0)) {
						try {
							imageURI = uploadHandler.uploadFile(photography, UploadHandler.FILE_TYPE.PRODUCT_IMAGE, product, getServletContext());
						} catch (FileAlreadyExistsException ex) {
							// It is not a fatal error, we ask the user to try again
							Logger.getLogger(NewProductServlet.class.getName()).log(Level.WARNING, null, ex);
							product.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
							request.setAttribute("product", product);
							doGet(request, response);
						}
						product.setPhotography(imageURI);
					}
				}
				if (!response.isCommitted()) {
					if (!productDAO.updateProduct(product.getId(), product)) {
						response.sendError(500, "Qualcosa è andato storto. Non è stato possibili aggiornare immagine o logo");
					} else {
						response.sendRedirect(getServletContext().getContextPath() + "/restricted/ProductList");
					}
				}
			} else {
				request.setAttribute("product", product);
				doGet(request, response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(NewProductServlet.class
					.getName()).log(Level.SEVERE, null, ex);
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
		return "New Product Servlet";
	}
}
