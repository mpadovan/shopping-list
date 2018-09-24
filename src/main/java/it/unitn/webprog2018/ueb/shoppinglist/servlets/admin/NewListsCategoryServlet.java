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
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "NewListsCategoryServlet", urlPatterns = {"/restricted/admin/NewListsCategory"})
public class NewListsCategoryServlet extends HttpServlet {
	
	ListsCategoryDAO listsCategoryDAO;
	ListsCategoryImagesDAO listsCategoryImagesDAO;
	
	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		listsCategoryDAO = ((DAOFactory) getServletContext().getAttribute("daoFactory")).getListsCategoryDAO();
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
		request.getRequestDispatcher("/WEB-INF/views/admin/NewListsCategory.jsp").forward(request, response);
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
		String description = request.getParameter("description");
		Part image = request.getPart("image");
		System.out.println();
		
		ListsCategory listsCategory = new ListsCategory();
		listsCategory.setName(name);
		listsCategory.setDescription(description);
		
		try {
			if (listsCategoryDAO.addListCategory(listsCategory)) {
				if (image == null) {
					response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ListCategory");
				} else {
					listsCategory = listsCategoryDAO.getByName(name);
					ListsCategoriesImage listsCategoriesImage = new ListsCategoriesImage();
					listsCategoriesImage.setImage("string");
					listsCategoriesImage.setCategory(listsCategory);
					if (listsCategoryImagesDAO.addListsCategoriesImage(listsCategoriesImage)) {
						response.sendRedirect(getServletContext().getContextPath() + "/restricted/admin/ListCategory");
					} else {
						response.sendError(500, "qualcosa è andato storto");
					}
				}
			} else {
				request.setAttribute("listsCategory", listsCategory);
				request.getRequestDispatcher("/WEB-INF/views/admin/NewListsCategory.jsp").forward(request, response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(NewListsCategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		return "Short description";
	}
	
}
