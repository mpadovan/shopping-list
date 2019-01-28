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
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "EditListsCategoryServlet", urlPatterns = {"/restricted/admin/EditListsCategory"})
public class EditListsCategoryServlet extends HttpServlet {
	
	private ListsCategoryDAO listsCategoryDAO;
	private ListsCategoryImagesDAO listsCategoryImagesDAO;
	
	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		listsCategoryDAO = factory.getListsCategoryDAO();
		listsCategoryImagesDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getListsCategoryImageDAO();
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
		Integer categoryId = Integer.parseInt(request.getParameter("id"));
		try {
			ListsCategory listsCategory = listsCategoryDAO.getById(categoryId);
			List<ListsCategoriesImage> listsCategoriesImage = listsCategoryImagesDAO.getAll();
			request.setAttribute("listsCategory", listsCategory);
			request.setAttribute("listsCategoryImage", listsCategoriesImage);
			request.getRequestDispatcher("/WEB-INF/views/admin/EditListsCategory.jsp").forward(request, response);
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditListsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
		} catch (DaoException ex) {
			Logger.getLogger(EditListsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		Integer categoryId = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		//String image = request.getParameter("image");
		//String imageIdString = request.getParameter("imageId");
		
		//Integer imageId = null;
		ListsCategory listsCategory = new ListsCategory();
		//ListsCategoriesImage categoriesImage = null;
		listsCategory.setId(categoryId);
		listsCategory.setName(name);
		listsCategory.setDescription(description);
		
		try {
			if (listsCategoryDAO.updateListsCategory(categoryId, listsCategory)) {
				response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ListCategory");
			} else {
				request.setAttribute("listsCategory", listsCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/EditListsCategory.jsp").forward(request, response);
			}
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(EditListsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
			response.sendError(404, ex.getMessage());
		} catch (DaoException ex) {
			Logger.getLogger(EditListsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Edit list category servlet";
	}
	
}
