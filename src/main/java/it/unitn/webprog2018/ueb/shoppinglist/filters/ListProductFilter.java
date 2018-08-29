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
public class ListProductFilter implements Filter {

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
		Integer listId = (Integer) request.getAttribute("currentListId");
		User user = (User) ((HttpServletRequest)request).getSession().getAttribute("user");
		try {
			request.setAttribute("addDeletePermission", listDAO.hasAddDeletePermission(listId, user.getId()));
		} catch (DaoException ex) {
			ex.printStackTrace();
			((HttpServletResponse)response).sendError(500, "We could not determine if you have the right to edit the products on this list");
		}
	}

	@Override
	public void destroy() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
