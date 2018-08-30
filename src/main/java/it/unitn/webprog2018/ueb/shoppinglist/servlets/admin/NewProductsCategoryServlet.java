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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author giulia
 */
@MultipartConfig
public class NewProductsCategoryServlet extends HttpServlet {

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
		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getProductsCategoryDAO();
		// parametri stringhe
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		//parametri file
		String logoURI = "";
		File fileLogo = null;
		String logoFileName = "";
		String logoFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "public" + File.separator + "productCategoryLogo" + File.separator;
		Part logo = request.getPart("logo");
		ProductsCategory productCategory = new ProductsCategory();
		try {
			try {

				ProductsCategory subCategory = new ProductsCategory();
				if (categoryId >= 0) {
					subCategory = productsCategoryDAO.getById(categoryId);
				} else {
					subCategory.setId(null);
				}
				productCategory.setName(name);
				productCategory.setDescription(description);
				productCategory.setCategory(subCategory);
			} catch (RecordNotFoundDaoException ex) {
				Logger.getLogger(NewProductsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
				response.sendError(404, ex.getMessage());
			}
			if (productsCategoryDAO.addProductsCategory(productCategory)) {
				//upload logo
				if ((logo != null) && (logo.getSize() > 0)) {
					logoFileName = Paths.get(logo.getSubmittedFileName()).getFileName().toString();
					int ext = logoFileName.lastIndexOf(".");
					int noExt = logoFileName.lastIndexOf(File.separator);
					logoFileName = logoFolder + productCategory.getId() + (ext > noExt ? logoFileName.substring(ext) : "");
					InputStream fileContentLogo = null;
					try {
						ext = logoFileName.lastIndexOf(".");
						noExt = logoFileName.lastIndexOf(File.separator);
						fileContentLogo = logo.getInputStream();
						fileLogo = new File(logoFileName);
						Files.copy(fileContentLogo, fileLogo.toPath());
						logoURI = File.separator + "uploads" + File.separator + "public" + File.separator + "productCategoryLogo"
								+ File.separator + productCategory.getId() + (ext > noExt ? logoFileName.substring(ext) : "");

					} catch (FileAlreadyExistsException ex) {
						fileLogo.delete();
						Files.copy(fileContentLogo, fileLogo.toPath());
						logoURI = File.separator + "uploads" + File.separator + "public" + File.separator + "productCategoryLogo"
								+ File.separator + productCategory.getId() + (ext > noExt ? logoFileName.substring(ext) : "");

					}
					productCategory.setLogo(logoURI);
					if (!productsCategoryDAO.updateProductsCategory(productCategory.getId(), productCategory)) {
						response.sendError(500, "Qualcosa è andato storto. Non è stato possibili aggiornare logo");
					}
				}
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ProductsCategory");
			} else {
				request.setAttribute("productCategory", productCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/NewProductsCategory.jsp").forward(request, response);
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
