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
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author simon
 */
@MultipartConfig
@WebServlet(name = "EditProductServlet", urlPatterns = {"/restricted/permission/EditProduct"})
public class EditProductServlet extends HttpServlet {
	
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Integer productId = Integer.parseInt(request.getParameter("id"));
		
		try {
			Product product = productDAO.getProduct(productId);
			List<ProductsCategory> productsCategory = productsCategoryDAO.getAll();
			request.setAttribute("productsCategory", productsCategory);
			request.setAttribute("product", product);
			request.getRequestDispatcher("/WEB-INF/views/product/EditProduct.jsp").forward(request, response);
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		File fileLogo = null;
		File fileImage = null;
		String logoFileName = "";
		String imageFileName = "";
		String logoFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productLogo" + File.separator;
		String imageFolder = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productImage" + File.separator;
		
		try {
			Product product = productDAO.getProduct(productId);
			ProductsCategory productCategory = productsCategoryDAO.getById(categoryId);
			productCategory.setId(categoryId);
			product.setCategory(productCategory);
			product.setName(name);
			product.setNote(note);
			if ((logo != null) && (logo.getSize() > 0)) {
				//crezione cartella se non esiste
				//delete old product
				System.out.println("logo si");
				if (product.getLogo() != null && !product.getLogo().equals("") && !product.getLogo().equals("null")) {
					System.out.println("voglio cancellare");
					String logoFolderold = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productLogo" + File.separator;
					int extold = product.getLogo().lastIndexOf(".");
					File file = new File(logoFolder + product.getId() + product.getLogo().substring(extold));
					if (file.exists()) {
						Boolean fatto=file.delete();
						System.out.println(fatto);
					}
				}
				System.out.println("logo cancellato o non trovato");
				//create
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
					response.sendError(500, "Server could not store your avatar, "
							+ "please retry the sign up process. "
							+ "Notice that you can also upload the image later in you user page.");
					getServletContext().log("impossible to upload the file", ex);
					
				}
				product.setLogo(logoURI);
			}
			if ((photography != null) && (photography.getSize() > 0)) {
				//delete old photography
				if (product.getPhotography() != null && !product.getPhotography().equals("") && !product.getPhotography().equals("null")) {
					String imageFolderold = getServletContext().getInitParameter("uploadFolder") + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productImage" + File.separator;
					int extold = product.getPhotography().lastIndexOf(".");
					File file = new File(imageFolderold + product.getId() + product.getPhotography().substring(extold));
					if (file.exists()) {
						file.delete();
					}
				}
				//create
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
//					fileImage.delete();
//					Files.copy(fileContentImage, fileImage.toPath());
//					imageURI = File.separator + "uploads" + File.separator + "restricted" + File.separator + user.getId() + File.separator + "productImage"
//							+ File.separator + product.getId() + (ext > noExt ? imageFileName.substring(ext) : "");
response.sendError(500, "Server could not store your avatar, "
		+ "please retry the sign up process. "
		+ "Notice that you can also upload the image later in you user page.");
getServletContext().log("impossible to upload the file", ex);
				}
				product.setPhotography(imageURI);
			}
			if (productDAO.updateProduct(productId, product)) {
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/ProductList");
			} else {
				request.setAttribute("product", product);
				request.getRequestDispatcher("/WEB-INF/views/product/EditProduct.jsp").forward(request, response);
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditProductServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Edit Product Servlet";
	}
}
