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
		ProductsCategoryDAO productsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getProductsCategoryDAO();
		Integer productCategoryId = Integer.parseInt(request.getParameter("id"));
		//parametri stringhe
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		//parametri file
		String logoURI = "";
		File fileLogo = null;
		String logoFileName = "";
		String logoFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "public" + File.separator + "productCategoryLogo" + File.separator;
		Part logo = request.getPart("logo");
		try {
			ProductsCategory productsCategory = productsCategoryDAO.getById(productCategoryId);
			productsCategory.setName(name);
			productsCategory.setDescription(description);
			productsCategory.setId(productCategoryId);
			if ((logo != null) && (logo.getSize() > 0)) {
				//creazione cartella se non esiste
				//delete old logo product category
				if (productsCategory.getLogo() != null && !productsCategory.getLogo().equals("") && !productsCategory.getLogo().equals("null")) {
					int ext = productsCategory.getLogo().lastIndexOf(".");
					File file = new File(logoFolder + productsCategory.getId() + productsCategory.getLogo().substring(ext));
					if (file.exists()) {
						Boolean fatto = file.delete();
					}
				}
				//create
				logoFileName = Paths.get(logo.getSubmittedFileName()).getFileName().toString();
				int ext = logoFileName.lastIndexOf(".");
				int noExt = logoFileName.lastIndexOf(File.separator);
				logoFileName = logoFolder + productsCategory.getId() + (ext > noExt ? logoFileName.substring(ext) : "");
				InputStream fileContentLogo = null;
				try {
					ext = logoFileName.lastIndexOf(".");
					noExt = logoFileName.lastIndexOf(File.separator);
					fileContentLogo = logo.getInputStream();
					fileLogo = new File(logoFileName);
					Files.copy(fileContentLogo, fileLogo.toPath());
					logoURI = File.separator + "uploads" + File.separator + "public" + File.separator + "productCategoryLogo"
							+ File.separator + productsCategory.getId() + (ext > noExt ? logoFileName.substring(ext) : "");

				} catch (FileAlreadyExistsException ex) {
					response.sendError(500, "Server could not store your avatar, "
							+ "please retry the sign up process. "
							+ "Notice that you can also upload the image later in you user page.");
					getServletContext().log("impossible to upload the file", ex);

				}
				productsCategory.setLogo(logoURI);
			}

			if (productsCategoryDAO.updateProductsCategory(productCategoryId, productsCategory)) {
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ProductsCategory");
			} else {
				request.setAttribute("productCategory", productsCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/EditProductsCategory.jsp").forward(request, response);
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
