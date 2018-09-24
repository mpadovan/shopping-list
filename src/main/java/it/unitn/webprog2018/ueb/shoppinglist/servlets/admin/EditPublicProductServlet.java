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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "EditPublicProductServlet", urlPatterns = {"/restricted/admin/EditPublicProduct"})
@MultipartConfig
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
		} catch (RecordNotFoundDaoException ex) {
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
		Part logo = request.getPart("logo");
		String note = request.getParameter("note");
		Part photography = request.getPart("image");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));

		String uploadFolder = (String) getServletContext().getAttribute("uploadFolder");
		String logoFolder = (String) getServletContext().getAttribute("productLogoFolder");
		String imageFolder = (String) getServletContext().getAttribute("publicProductFolder");

		String logoURI = "";
		String imageURI = "";

		try {
			PublicProduct product = publicProductDAO.getById(productId);
			ProductsCategory productsCategory = productsCategoryDAO.getById(categoryId);
			productsCategory.setId(categoryId);
			product.setName(name);
			// product.setLogo(logo);
			product.setNote(note);
			// product.setPhotography(photography);
			product.setCategory(productsCategory);

			if ((logo != null) && (logo.getSize() > 0)) {
				// delete old product logo
				if (product.getLogo() != null && !product.getLogo().equals("") && !product.getLogo().equals("null")) {
					File oldLogo = new File(uploadFolder + product.getLogo());
					if (oldLogo.exists()) {
						oldLogo.delete();
					}
				}
				// save new logo
				String logoFileName = Paths.get(logo.getSubmittedFileName()).getFileName().toString();
				int ext = logoFileName.lastIndexOf(".");
				int noExt = logoFileName.lastIndexOf(File.separator);
				logoFileName = logoFolder + product.getHash() + (ext > noExt ? logoFileName.substring(ext) : "");
				try {
					File fileLogo = new File(logoFileName);
					if (fileLogo.exists()) { // avoid FileAlreadyExistsException
						fileLogo.delete();
					}
					Files.copy(logo.getInputStream(), fileLogo.toPath());
					logoURI = logoFileName.substring(logoFileName.lastIndexOf(uploadFolder) + uploadFolder.length());
				} catch (IOException ex) {
					// It is not a fatal error, we ask the user to try again
					Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.WARNING, null, ex);
					product.setError("logo", "Non è stato possibile salvare il logo, riprova più tardi o contatta un amministratore");
					request.setAttribute("product", product);
					doGet(request, response);
				}
				product.setLogo(logoURI);
				
				if (!response.isCommitted()) {
					if ((photography != null) && (photography.getSize() > 0)) {
						// delete old product image
						if (product.getLogo() != null && !product.getLogo().equals("") && !product.getLogo().equals("null")) {
							File oldLogo = new File(uploadFolder + product.getLogo());
							if (oldLogo.exists()) {
								oldLogo.delete();
							}
						}
						// save new image
						String imageFileName = Paths.get(logo.getSubmittedFileName()).getFileName().toString();
						ext = imageFileName.lastIndexOf(".");
						noExt = imageFileName.lastIndexOf(File.separator);
						logoFileName = imageFolder + product.getHash() + (ext > noExt ? logoFileName.substring(ext) : "");
						try {
							File fileLogo = new File(logoFileName);
							if (fileLogo.exists()) { // avoid FileAlreadyExistsException
								fileLogo.delete();
							}
							Files.copy(logo.getInputStream(), fileLogo.toPath());
							imageURI = logoFileName.substring(logoFileName.lastIndexOf(uploadFolder) + uploadFolder.length());
						} catch (IOException ex) {
							// It is not a fatal error, we ask the user to try again
							Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.WARNING, null, ex);
							product.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
							request.setAttribute("product", product);
							doGet(request, response);
						}
						product.setPhotography(imageURI);
					}
				}
				if (publicProductDAO.updateProduct(productId, product)) {
					response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/PublicProductList");
				} else {
					request.setAttribute("product", product);
					request.getRequestDispatcher("/WEB-INF/views/admin/EditPublicProduct.jsp").forward(request, response);
				}
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
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
