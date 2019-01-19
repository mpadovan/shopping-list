/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles requests to the home page of logged users.
 *
 * @author simon
 */
@WebServlet(name = "HomePageLoginServlet", urlPatterns = {"/restricted/HomePageLogin/*"})
public class HomePageLoginServlet extends HttpServlet {

	@Override
	public void init() {
	}

	/**
	 * Handles the HTTP <code>GET</code> method. Forwards the request to the
	 * HomePageLogin jsp.
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
		request.getRequestDispatcher("/WEB-INF/views/HomePageLogin.jsp").forward(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method. Does nothing.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Home Page Login Servlet";
	}
}
