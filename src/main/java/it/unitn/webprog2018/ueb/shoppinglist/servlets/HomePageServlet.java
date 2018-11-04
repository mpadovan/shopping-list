package it.unitn.webprog2018.ueb.shoppinglist.servlets;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.UpdateException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giuliapeserico
 */
@WebServlet(name = "HomePageServlet", urlPatterns = {"/home"})
public class HomePageServlet extends HttpServlet {
	
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
		
		Cookie[] cookies = request.getCookies();
		boolean found = false;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("anonToken")) {
					found = true;
				}
			}
		}
		if (!found) {
			try {
				Cookie cookie = new Cookie("anonToken",
						((DAOFactory)getServletContext().getAttribute("daoFactory")).getTokenDAO().getAnonimousToken());
				cookie.setMaxAge(60 * 60 * 24 * 365 * 10); // expires in 10 years
				response.addCookie(cookie);
			} catch (UpdateException ex) {
				Logger.getLogger(HomePageServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		request.getRequestDispatcher("/WEB-INF/views/HomePage.jsp").forward(request, response);
	}
	
	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Home Page Servlet";
	}
	
}
