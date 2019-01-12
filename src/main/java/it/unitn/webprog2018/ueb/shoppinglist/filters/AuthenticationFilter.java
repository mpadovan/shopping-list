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
 * Filter that prevents not logged users from accessing restricted areas of the
 * web app
 *
 * @author Giulia Carocari
 */
public class AuthenticationFilter implements Filter {

	private FilterConfig filterConfig = null;
	private ListDAO listDAO;
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

			if (user == null) {
				String contextPath = servletContext.getContextPath();
				if (!contextPath.endsWith("/")) {
					contextPath += "/";
				}
				request.getRequestDispatcher("/WEB-INF/views/auth/Login.jsp").forward(request, response);
				return;
			} else { 
				try {
				// the user is logged in and we load her lists for the sidebar
				java.util.List<List> personalLists = listDAO.getPersonalLists(user.getId());
				java.util.List<List> sharedLists = listDAO.getSharedLists(user.getId());
				// For now we leave it associated with the session, we shall switch to a request attribute once it is safe to change JSPs
				// Session cannot be null if user is not null
				request.setAttribute("personalLists", personalLists);
				request.setAttribute("sharedLists", sharedLists);
				} catch (DaoException ex) {
					Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
					HttpErrorHandler.handleDAOException(ex, (HttpServletResponse) response);
				}
				
			}
		}
		if (!response.isCommitted()) {
			try {
				chain.doFilter(request, response);
			} catch (IOException | ServletException ex) {
				Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public void destroy() {
	}

}
