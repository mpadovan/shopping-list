/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.filters;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
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
 * Filter that checks if a request has the right to access a user-uploaded file.
 * Such files are all saved under the /uploads/restricted path and are distinguished between
 * private (avatar folder) and shared (shared/list and shared/product folders).
 * If the session-scoped user is not the owner of the avatar (identified by user-id),
 * has no view-access to the list (image identified by list-id) or has no access to the product image
 * (named after the id of the product itself) then an error with code 401 shall be returned.
 *
 * @author Giulia Carocari
 */
public class UploadFilter implements Filter {

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
			HttpServletResponse resp = (HttpServletResponse) response;
			ServletContext servletContext = ((HttpServletRequest) request).getServletContext();
			HttpSession session = ((HttpServletRequest) request).getSession(false);
			User user = null;
			if (session != null) {
				user = (User) session.getAttribute("user");
			}

			if (user == null) {
				String contextPath = servletContext.getContextPath();
				if (!contextPath.endsWith("/")) {
					contextPath += "/";
				}
				resp.sendRedirect(contextPath + "Login");
			} else {
				String uri = ((HttpServletRequest) request).getRequestURI();
				if (!uri.endsWith("/")) {
					uri += "/";
				}
				// request for an avatar
				if (Pattern.matches(".*/uploads/restricted/avatar/.*", uri)) {
					HttpErrorHandler.sendError401(resp);
				}  // request for a list-image
				else if (Pattern.matches(".*/uploads/restricted/shared/list/.*", uri)) {
					// end of the number containing the list id, either beginning of extension or end of uri
					Integer extIndex = uri.lastIndexOf(".") > 0 ? uri.lastIndexOf(".") : uri.length();
					Integer listId = Integer.parseInt(uri.substring(uri.lastIndexOf("/", uri.length()-2), extIndex));
					// System.out.println(listId);
					try {
						if (!listDAO.getList(listId).getOwner().getId().equals(user.getId()) || !listDAO.hasViewPermission(listId, user.getId())) {
							HttpErrorHandler.sendError401(resp);
						}
					} catch (DaoException ex) {
						HttpErrorHandler.sendError500(resp);
						Logger.getLogger(UploadFilter.class.getName()).log(Level.SEVERE, null, ex);
					}
				} // request for a product-image
				else if (Pattern.matches(".*/uploads/restricted/shared/product/.*", uri)) {
					
				}
				// WTH are you looking for?
				else {
					HttpErrorHandler.sendError404(resp);
				}
			}
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

	@Override
	public void destroy() {
	}

}
