/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategoriesImage;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.utils.UploadHandler;
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
@WebServlet(name = "NewListCategoryImageServlet", urlPatterns = {"/restricted/admin/NewListCategoryImage"})
public class NewListCategoryImageServlet extends HttpServlet {

	ListsCategoryImagesDAO listsCategoryImagesDAO;
	ListsCategoryDAO listsCategoryDAO;

	@Inject
	UploadHandler uploadHandler;

	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		listsCategoryImagesDAO = factory.getListsCategoryImageDAO();
		listsCategoryDAO = factory.getListsCategoryDAO();
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
		//non si può inserire una listcategoriesimage senza un'immagine da DB. Pensa come diavolo inserirla
		
		try {
			String imageURI = "";

			Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));
			Part image = request.getPart("image");

			//carica immagine
			ListsCategoriesImage listsCategoriesImage = new ListsCategoriesImage();
			ListsCategory listsCategory = listsCategoryDAO.getById(categoryId);
			listsCategoriesImage.setCategory(listsCategory);
			listsCategoriesImage.setImage("temporary");

			listsCategoryImagesDAO.addListsCategoriesImage(listsCategoriesImage);
			try {
				imageURI = uploadHandler.uploadFile(image, UploadHandler.FILE_TYPE.LIST_CATEGORY_IMAGE, listsCategoriesImage, getServletContext());
			} catch (IOException ex) {
				listsCategoryImagesDAO.deleteImageTemporary(categoryId);
				response.sendError(500, "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
			}
			if (!response.isCommitted()) {
				listsCategoriesImage.setImage(imageURI);
				if (!listsCategoryImagesDAO.updateListsCategoriesImage(listsCategoriesImage.getId(), listsCategoriesImage)) {
					listsCategoryImagesDAO.deleteImageTemporary(categoryId);
					response.sendError(500, "Qualcosa è andato storto. Non è stato possibile aggiornare logo");
				}
			}
			if (!response.isCommitted()) {
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ListCategory");
			}
		} catch (DaoException ex) {
			Logger.getLogger(NewListCategoryImageServlet.class.getName()).log(Level.SEVERE, null, ex);
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
