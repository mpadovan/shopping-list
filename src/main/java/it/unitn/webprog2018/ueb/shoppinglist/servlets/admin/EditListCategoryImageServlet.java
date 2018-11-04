/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategoriesImage;
import it.unitn.webprog2018.ueb.shoppinglist.utils.UploadHandler;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author simon
 */
@MultipartConfig
@WebServlet(name = "EditListCategoryImageServlet", urlPatterns = {"/restricted/admin/EditListCategoryImage"})
public class EditListCategoryImageServlet extends HttpServlet {

	ListsCategoryImagesDAO listsCategoryImagesDAO;

	@Inject
	UploadHandler uploadHandler;

	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		listsCategoryImagesDAO = factory.getListsCategoryImageDAO();
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
		Integer imageID = Integer.parseInt(request.getParameter("imageId"));
		Part image = request.getPart("image");
		String imageURI = null;
		try {
			ListsCategoriesImage listsCategoriesImage = listsCategoryImagesDAO.getById(imageID);
			// delete old product category logo
			uploadHandler.deleteFile(listsCategoriesImage.getImage(), getServletContext());
			// save new logo
			try {
				imageURI = uploadHandler.uploadFile(image, UploadHandler.FILE_TYPE.LIST_CATEGORY_IMAGE, listsCategoriesImage, getServletContext());
			} catch (IOException ex) {
				// It is not a fatal error, we ask the user to try again
				Logger.getLogger(EditProductsCategoryServlet.class.getName()).log(Level.WARNING, null, ex);
				response.sendError(500, "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
			}
			listsCategoriesImage.setImage(imageURI);
			if (!response.isCommitted()) {
				listsCategoryImagesDAO.updateListsCategoriesImage(imageID, listsCategoriesImage);
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ListCategory");
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(DeleteListCategoryImageServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, "l'immagine non esiste");
		} catch (DaoException ex) {
			Logger.getLogger(DeleteListCategoryImageServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(500, "errore interno");
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
