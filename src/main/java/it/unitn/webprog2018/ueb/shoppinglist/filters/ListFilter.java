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
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filter that prevents users from accessing lists they do not have the
 * permission to view.
 *
 * @author Giulia Carocari
 */
@WebFilter(filterName = "ListFilter", urlPatterns = {"/restricted/HomePageLogin/*", "/restricted/EditList/*", "/restricted/InfoList/*", "/restricted/DeleteList/*"},
		dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR, DispatcherType.INCLUDE})
public class ListFilter implements Filter {

	private FilterConfig filterConfig = null;
	private ListDAO listDAO;

	public ListFilter() {
	}

	/**
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {

			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = ((HttpServletRequest) request).getSession(false);
			User user = null;
			if (session != null) {
				user = (User) session.getAttribute("user");
			}

			if (user == null) { // Should never happen, but just in case
				String contextPath = filterConfig.getServletContext().getContextPath();
				if (!contextPath.endsWith("/")) {
					contextPath += "/";
				}
				request.getRequestDispatcher("/WEB-INF/views/auth/Login.jsp").forward(request, response);
				return;
			}

			String uri = req.getRequestURI();
			if (!uri.endsWith("/")) {
				uri = uri.trim();
				uri += "/";
			}
			Integer listId = 0;
			// Checks if URI contains the list id (does nothing if it is a no-list home page request -> handled by authentication filter)
			if (Pattern.matches(".*/restricted/[a-zA-Z]+/" + user.getHash() + "/.+", uri)) {
				try {
					listId = List.getDecryptedId(uri.substring(uri.lastIndexOf("/", uri.length() - 2) + 1, uri.length() - 1));
					// request.setAttribute("currentListId", listId);
				} catch (NumberFormatException ex) {
					Logger.getLogger(ListFilter.class.getName()).log(Level.SEVERE, null, ex);
					HttpErrorHandler.sendError400((HttpServletResponse) response);
					return;
				}

				if (!response.isCommitted()) {
					try {
						// if the user can't view the list then she can't do anything else
						List currentList = listDAO.getList(listId);
						if (currentList.getOwner().equals(user) || listDAO.hasViewPermission(listId, user.getId())) {
							request.setAttribute("hasViewPermission", true);
							request.setAttribute("hasModifyPermission", currentList.getOwner().equals(user) || listDAO.hasModifyPermission(listId, user.getId()));
							request.setAttribute("hasDeletePermission", currentList.getOwner().equals(user) || listDAO.hasDeletePermission(listId, user.getId()));
							request.setAttribute("addDeletePermission", currentList.getOwner().equals(user) || listDAO.hasAddDeletePermission(listId, user.getId()));
							request.setAttribute("currentList", currentList);
						} else {
							HttpErrorHandler.sendError401((HttpServletResponse) response);
						}
					} catch (DaoException ex) {
						HttpErrorHandler.handleDAOException(ex, (HttpServletResponse) response);
						Logger.getLogger(ListFilter.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
			if (!response.isCommitted()) {
				try {
					chain.doFilter(request, response);
				} catch (IOException | ServletException ex) {
					Logger.getLogger(ListFilter.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

	}

	/**
	 * Return the filter configuration object for this filter.
	 *
	 * @return
	 */
	public FilterConfig getFilterConfig() {
		return (this.filterConfig);
	}

	/**
	 * Set the filter configuration object for this filter.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter
	 */
	@Override
	public void destroy() {
	}

	/**
	 * Init method for this filter
	 *
	 * @param filterConfig
	 */
	@Override
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		listDAO = ((DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory")).getListDAO();
	}

}
