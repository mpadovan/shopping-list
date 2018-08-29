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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Giulia Carocari
 */
public class ListDeleteFilter implements Filter {

	private ListDAO listDAO;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		listDAO = ((DAOFactory) filterConfig.getServletContext().getAttribute("daoFactory")).getListDAO();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			Integer currentListId = (Integer) request.getAttribute("currentListId");
			try {
				if (!listDAO.hasDeletePermission(currentListId, ((User) req.getSession().getAttribute("user")).getId())) {
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

	@Override
	public void destroy() {
	}

}
