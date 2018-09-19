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
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
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
@MultipartConfig
@WebServlet(name = "EditProductsCategoryServlet", urlPatterns = {"/restricted/admin/EditProductsCategory"})
public class EditProductsCategoryServlet extends HttpServlet {

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
			List<ProductsCategory> productsCategory = productsCategoryDAO.getAll();
			ProductsCategory productCategory = productsCategoryDAO.getById(categoryId);
			request.setAttribute("productsCategory", productsCategory);
			request.setAttribute("productCategory", productCategory);
			request.getRequestDispatcher("/WEB-INF/views/admin/EditProductsCategory.jsp").forward(request, response);

		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		String context = getServletContext().getContextPath();
		if (!context.endsWith("/")) {
			context += "/";
		}
		
		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getProductsCategoryDAO();
		Integer productCategoryId = Integer.parseInt(request.getParameter("id"));

		//parametri stringhe
		String name = request.getParameter("name");
		String description = request.getParameter("description");

		//parametri file
		String logoURI = "";
		String logoFolder = (String) getServletContext().getAttribute("categoryLogoFolder");
		String uploadFolder = (String) getServletContext().getAttribute("uploadFolder");

		Part logo = request.getPart("logo");
		try {
			ProductsCategory productsCategory = productsCategoryDAO.getById(productCategoryId);
			productsCategory.setName(name);
			productsCategory.setDescription(description);
			productsCategory.setId(productCategoryId);

			if ((logo != null) && (logo.getSize() > 0)) {
				// delete old product category logo
				if (productsCategory.getLogo() != null && !productsCategory.getLogo().equals("") && !productsCategory.getLogo().equals("null")) {
					File oldLogo = new File(uploadFolder + productsCategory.getLogo());
					if (oldLogo.exists()) {
						oldLogo.delete();
					}
				}
				// save new logo
				String logoFileName = Paths.get(logo.getSubmittedFileName()).getFileName().toString();
				int ext = logoFileName.lastIndexOf(".");
				int noExt = logoFileName.lastIndexOf(File.separator);
				logoFileName = logoFolder + productsCategory.getHash() + (ext > noExt ? logoFileName.substring(ext) : "");
				try {
					File fileLogo = new File(logoFileName);
					if (fileLogo.exists()) { // avoid FileAlreadyExistsException
					 	fileLogo.delete();
					}
					Files.copy(logo.getInputStream(), fileLogo.toPath());
					logoURI = context + logoFileName.substring(logoFileName.lastIndexOf(uploadFolder) + uploadFolder.length());
				} catch (IOException ex) {
					// It is not a fatal error, we ask the user to try again
					Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.WARNING, null, ex);
					productsCategory.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
					doGet(request, response);
				}
				productsCategory.setLogo(logoURI);
			}
			if (!response.isCommitted()) {
				if (productsCategoryDAO.updateProductsCategory(productCategoryId, productsCategory)) {
					response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ProductsCategory");
				} else {
					request.setAttribute("productCategory", productsCategory);
					request.getRequestDispatcher("/WEB-INF/views/admin/EditProductsCategory.jsp").forward(request, response);
				}
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
