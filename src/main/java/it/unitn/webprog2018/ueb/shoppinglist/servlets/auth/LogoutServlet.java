/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.servlets.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <code>HttpServlet</code> that handles the logout of a user, deleting all of
 * his associated cookies.
 *
 * @author Giulia Carocari
 */
@WebServlet("/restricted/Logout")
public class LogoutServlet extends HttpServlet {
	
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
		String path = getServletContext().getContextPath();
		if (!path.endsWith("/")) {
			path += "/";
		}
		
		HttpSession session = request.getSession();
		
		// Delete all cookies
		Cookie cookies[] = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0);
			cookie.setValue("");
			cookie.setPath("/ShoppingList");
			response.addCookie(cookie);
		}
		// Session is removed from sessionHandler (notifications)
		session.invalidate();
		response.sendRedirect(path);
	}
	
	@Override
	public String getServletInfo() {
		return "Servlet for logout handling. Only GET requests implemented.";
	}
	
}
