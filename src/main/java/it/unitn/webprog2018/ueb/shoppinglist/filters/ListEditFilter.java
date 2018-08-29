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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
public class ListEditFilter implements Filter {
	
	private ListDAO listDAO;
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		listDAO = ((DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory")).getListDAO();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
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
					List currentList = (List) ((HttpServletRequest) request).getAttribute("currentList");
					if (!currentList.getOwner().getId().equals(user.getId()) && !listDAO.hasModifyPermission(listId, ((User) req.getSession().getAttribute("user")).getId())) {
						((HttpServletResponse) response).sendError(401);
					}
				} catch (DaoException ex) {
					((HttpServletResponse) response).sendError(500, "We could not determine if you have the right to delete this list");
					Logger.getLogger(ListDeleteFilter.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			try {
				chain.doFilter(request, response);
			} catch (IOException | ServletException ex) {
				Logger.getLogger(ListDeleteFilter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public void destroy() {
	}

}
