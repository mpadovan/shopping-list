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
import it.unitn.webprog2018.ueb.shoppinglist.servlets.HomePageLoginServlet;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
public class ListViewFilter implements Filter {

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
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
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
				uri = uri.trim();
				uri += "/";
			}

			if (Pattern.matches(".*/restricted/[a-zA-Z]+/" + user.getId() + "/.+", uri)) {
				Integer listId;
				if (uri.endsWith("/" + user.getId() + "/")) { // handles the case in which userId and listId have the same value
					listId = user.getId();
					request.setAttribute("currentListId", listId);
				} else {
					try {
						listId = Integer.parseInt(uri.substring(uri.lastIndexOf("/", uri.length() - 2) + 1, uri.length() - 1));
						request.setAttribute("currentListId", listId);
					} catch (NumberFormatException ex) {
						((HttpServletResponse) response).sendError(400, "You wrote this URL yourself, didn't you? This does not point to a list.");
						return;
					}
				}
				try {
					if (uri.matches(".*/[0-9]+/[0-9]+/")) { // URI ends with user id and list id (checked by filters)
						java.util.List<List> personalLists = (java.util.List<List>) req.getSession().getAttribute("personalLists");
						if (personalLists != null) {
							for (List l : personalLists) {
								if (l.getId().equals(request.getAttribute("currentListId"))) {
									request.setAttribute("currentList", l);
								}
							}
						}
						java.util.List<List> sharedLists = (java.util.List<List>) req.getSession().getAttribute("sharedLists");
						if (sharedLists != null) {
							for (List l : (java.util.List<List>) req.getSession().getAttribute("sharedLists")) {
								if (l.getId().equals(request.getAttribute("currentListId"))) {
									request.setAttribute("currentList", l);
									try {
										request.setAttribute("sharedUsers", listDAO.getConnectedUsers(l.getId()));
									} catch (DaoException ex) {
										Logger.getLogger(HomePageLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
										resp.sendError(500);
									}
								}
							}
						}
					}
					List currentList = (List) ((HttpServletRequest) request).getAttribute("currentList");
					if (!currentList.getOwner().getId().equals(user.getId()) && !listDAO.hasViewPermission(listId, user.getId())) {
						((HttpServletResponse) response).sendError(401, "YOU SHALL NOT PASS!\n"
								+ "The resource you are trying to access is none of your business.\n"
								+ "If you think you have the right to access it, prove it by logging in.");
						return;
					}
				} catch (DaoException ex) {
					ex.printStackTrace();
					((HttpServletResponse) response).sendRedirect("ops");
					return;
				}
			} else if (!uri.contains("HomePageLogin")) { // The home page must not necessarily refer to a list
				((HttpServletResponse) response).sendError(404, "The resource you are trying to access does not exist");
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
