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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author giulia
 */
@MultipartConfig
@WebServlet(name = "NewPublicProductServlet", urlPatterns = {"/restricted/admin/NewPublicProduct"})
public class NewPublicProductServlet extends HttpServlet {
	private ProductsCategoryDAO productsCategoryDAO;

	/**
	 * Method to be executed at servlet initialization. Handles connections with
	 * persistence layer.
	 */
	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
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
		try {
			InitializeCategoryRedirect(request, response);
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
		File fileLogo = null;
		File fileImage = null;
		String logoFileName = "";
		String imageFileName = "";
		String logoFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "public" + File.separator + "productLogo" + File.separator;
		String imageFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "public" + File.separator + "productImage" + File.separator;
		Part logo = request.getPart("logo");
		Part photography = request.getPart("image");

		PublicProduct product = new PublicProduct();
		try {
			try {
				ProductsCategory productsCategory = productsCategoryDAO.getById(categoryId);
				productsCategory.setId(categoryId);
				product.setName(name);
				product.setNote(note);
				//product.setLogo(logo);
				//product.setPhotography(photography);
				product.setCategory(productsCategory);
			} catch (RecordNotFoundDaoException ex) {
				Logger.getLogger(NewPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
				response.sendError(404, ex.getMessage());
			}
			//aggiunta del prodotto senza logo e immagine
			Boolean checkLogo = false;
			Boolean checkImage = false;

			if (publicProductDAO.addProductWithId(product)) {
				//upload logo
				if ((logo != null) && (logo.getSize() > 0)) {
					checkLogo = true;
					logoFileName = Paths.get(logo.getSubmittedFileName()).getFileName().toString();
					int ext = logoFileName.lastIndexOf(".");
					int noExt = logoFileName.lastIndexOf(File.separator);
					logoFileName = logoFolder + product.getId() + (ext > noExt ? logoFileName.substring(ext) : "");
					InputStream fileContentLogo = null;
					try {
						ext = logoFileName.lastIndexOf(".");
						noExt = logoFileName.lastIndexOf(File.separator);
						fileContentLogo = logo.getInputStream();
						fileLogo = new File(logoFileName);
						Files.copy(fileContentLogo, fileLogo.toPath());
						logoURI = File.separator + "uploads" + File.separator + "public" + File.separator + "productLogo"
								+ File.separator + product.getId() + (ext > noExt ? logoFileName.substring(ext) : "");

					} catch (FileAlreadyExistsException ex) {
						fileLogo.delete();
						Files.copy(fileContentLogo, fileLogo.toPath());
						logoURI = File.separator + "uploads" + File.separator + "public" + File.separator + "productLogo"
								+ File.separator + product.getId() + (ext > noExt ? logoFileName.substring(ext) : "");

					}
					product.setLogo(logoURI);
				}
				//upload image
				if ((photography != null) && (photography.getSize() > 0)) {
					checkImage = true;
					imageFileName = Paths.get(photography.getSubmittedFileName()).getFileName().toString();
					int ext = imageFileName.lastIndexOf(".");
					int noExt = imageFileName.lastIndexOf(File.separator);
					imageFileName = imageFolder + product.getId() + (ext > noExt ? imageFileName.substring(ext) : "");
					InputStream fileContentImage = null;
					try {
						ext = imageFileName.lastIndexOf(".");
						noExt = imageFileName.lastIndexOf(File.separator);
						fileContentImage = photography.getInputStream();
						fileImage = new File(imageFileName);
						Files.copy(fileContentImage, fileImage.toPath());
						imageURI = File.separator + "uploads" + File.separator + "public" + File.separator + "productImage"
								+ File.separator + product.getId() + (ext > noExt ? imageFileName.substring(ext) : "");

					} catch (FileAlreadyExistsException ex) {
						fileImage.delete();
						Files.copy(fileContentImage, fileImage.toPath());
						imageURI = File.separator + "uploads" + File.separator + "public" + File.separator + "productImage"
								+ File.separator + product.getId() + (ext > noExt ? imageFileName.substring(ext) : "");

					}
					product.setPhotography(imageURI);
				}
				if (checkImage || checkLogo) {
					if (!publicProductDAO.updateProduct(product.getId(), product)) {
						response.sendError(500, "Qualcosa è andato storto. Non è stato possibili aggiornare immagine o logo");
					}
				}
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/PublicProductList");
			} else {
				request.setAttribute("product", product);
				InitializeCategoryRedirect(request, response);
			}

		} catch (DaoException ex) {
			Logger.getLogger(NewPublicProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, ex.getMessage());
		}
	}

	private void InitializeCategoryRedirect(HttpServletRequest request, HttpServletResponse response) throws DaoException, ServletException, IOException {
		List<ProductsCategory> productsCategory = productsCategoryDAO.getAll();
		request.setAttribute("productsCategory", productsCategory);
		request.getRequestDispatcher("/WEB-INF/views/admin/NewPublicProduct.jsp").forward(request, response);
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
