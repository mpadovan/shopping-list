/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.filters;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Giulia Carocari
 */
public class ListFilter implements Filter {

	private ListDAO listDAO;

	/**
	 * FilterConfig object associated with this filter
	 */
	private FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		listDAO = ((DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory")).getListDAO();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			ServletContext servletContext = ((HttpServletRequest) request).getServletContext();
			HttpSession session = ((HttpServletRequest) request).getSession(false);
			User user = null;
			if (session != null) {
				user = (User) session.getAttribute("user");
			}

			if (user == null) { // Should never happen, but just in case
				String contextPath = servletContext.getContextPath();
				if (!contextPath.endsWith("/")) {
					contextPath += "/";
				}
				request.getRequestDispatcher("/WEB-INF/views/auth/Login.jsp").forward(request, response);
				return;
			}

			String uri = ((HttpServletRequest) request).getRequestURI();
			if (!uri.endsWith("/")) {
				uri += "/";
			}
			String listIdString = "1";// uri.substring(uri.lastIndexOf("/" + user.getId() + "/") + ("/" + user.getId() + "/").length(), uri.lastIndexOf("/"));
			if (!listIdString.isEmpty() || !uri.contains("HomePageLogin")) {
				try {
					Integer listId = Integer.parseInt(listIdString);
					if (!listDAO.hasViewPermission(listId, user.getId())) {
						((HttpServletResponse) response).sendError(401, "YOU SHALL NOT PASS!\n"
								+ "The resource you are trying to access is none of your business.\n"
								+ "If you think you have the right to access it, prove it by logging in: localhost:8080/ShoppingList/Login");
						return;
					} else {
						java.util.List<List> personalLists = (java.util.List<List>) session.getAttribute("personalLists");
						if (personalLists != null) {
							for (List l : personalLists) {
								if (l.getId().equals(l.getId())) {
									request.setAttribute("currentList", l);
									System.out.println("Added List");
								}
							}
						}
						java.util.List<List> sharedLists = (java.util.List<List>) session.getAttribute("sharedLists");
						if (sharedLists != null) {
							for (List l : (java.util.List<List>) session.getAttribute("sharedLists")) {
								if (l.getId().equals(l.getId())) {
									request.setAttribute("currentList", l);
									request.setAttribute("sharedUsers", listDAO.getConnectedUsers(l.getId()));
									System.out.println("Added list");
								}
							}
						}
					}
				} catch (DaoException | NumberFormatException ex) {
					ex.printStackTrace();
					((HttpServletResponse) response).sendRedirect("ops");
					return;
				}
			} else {
				// ((HttpServletResponse) response).sendError(404, "The resource you are trying to access does not exist");
			}
			if (!response.isCommitted()) {
				Throwable problem = null;
				try {
					chain.doFilter(request, response);
				} catch (Exception ex) {
					if (response instanceof HttpServletResponse) {
						((HttpServletResponse) response).sendError(500, ex.getMessage());
					}
				}
			}
		}
	}

	@Override
	public void destroy() {
	}

}
