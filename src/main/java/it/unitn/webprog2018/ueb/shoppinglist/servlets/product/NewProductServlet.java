/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.product;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.dummy.DAOFactoryImpl;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.servlets.admin.NewPublicProductServlet;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author simon
 */
@MultipartConfig
public class NewProductServlet extends HttpServlet {

	private ProductDAO productDAO;
	private ProductsCategoryDAO productsCategoryDAO;

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
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		//parametri stringhe
		String name = (String) request.getParameter("name");
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String note = request.getParameter("note");

		//parametri file
		String logoURI = "";
		String imageURI = "";
		File fileLogo = null;
		File fileImage = null;
		String logoFileName = "";
		String imageFileName = "";
		String logoFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productLogo" + File.separator;
		String imageFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productImage" + File.separator;
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
				product.setLogo(null);
				product.setPhotography(null);
			} catch (RecordNotFoundDaoException ex) {
				Logger.getLogger(NewProductServlet.class.getName()).log(Level.SEVERE, null, ex);
				response.sendError(404, ex.getMessage());
			}
			//aggiunta del prodotto senza logo e immagine
			Boolean checkLogo = false;
			Boolean checkImage = false;
			if (productDAO.addProductWithId(product)) {
				//upload logo
				if ((logo != null) && (logo.getSize() > 0)) {
					System.out.println("carico logo");
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
						logoURI = File.separator + "uploads" + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productLogo"
								+ File.separator + product.getId() + (ext > noExt ? logoFileName.substring(ext) : "");


					} catch (FileAlreadyExistsException ex) {
						fileLogo.delete();
						Files.copy(fileContentLogo, fileLogo.toPath());
						logoURI = File.separator + "uploads" + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productLogo"
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
						imageURI = File.separator + "uploads" + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productImage"
								+ File.separator + product.getId() + (ext > noExt ? imageFileName.substring(ext) : "");


					} catch (FileAlreadyExistsException ex) {
						fileImage.delete();
						Files.copy(fileContentImage, fileImage.toPath());
						imageURI = File.separator + "uploads" + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productImage"
								+ File.separator + product.getId() + (ext > noExt ? imageFileName.substring(ext) : "");

					}
					product.setPhotography(imageURI);
				}
				if (checkImage || checkLogo) {
					if (!productDAO.updateProduct(product.getId(), product)) {
						response.sendError(500, "Qualcosa è andato storto. Non è stato possibili aggiornare immagine o logo");
					}
				}
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/ProductList");
			} else {
				request.setAttribute("product", product);
				request.getRequestDispatcher("/WEB-INF/views/product/NewProduct.jsp").forward(request, response);
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
