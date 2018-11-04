/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.admin;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategoriesImage;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giulia
 */
@WebServlet(name = "DeleteListCategoryServlet", urlPatterns = {"/restricted/admin/DeleteListCategory"})
public class DeleteListCategoryServlet extends HttpServlet {

	ListsCategoryImagesDAO listsCategoryImagesDAO;
	ListsCategoryDAO listsCategoryDAO;

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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ListsCategoryDAO listsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getListsCategoryDAO();
		Integer categoryId = Integer.parseInt(request.getParameter("id"));
		try {
			ListsCategory listsCategory = listsCategoryDAO.getById(categoryId);
			List<ListsCategoriesImage> listofimages = new ArrayList<ListsCategoriesImage>();
			listofimages = listsCategoryImagesDAO.getByCategoriesID(categoryId);
			for (ListsCategoriesImage c : listofimages) {
				File image = new File(getServletContext().getAttribute("uploadFolder") + c.getImage());
				if (image.exists()) {
					image.delete();
				}
			}
			if (listsCategoryDAO.deleteListsCategory(listsCategory.getId())) {
				//Cancella immagini dalla cartella ListCategoriesImage

				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ListCategory");
			} else {
				request.setAttribute("listsCategory", listsCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/CategoryList.jsp").forward(request, response);
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(DeleteListCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(DeleteListCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Delete list category servlet";
	}

}
