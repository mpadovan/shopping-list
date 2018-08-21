/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.ServiceUtils;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.AddDeletePermission;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 * Filter that checks whether the user has the right to edit the products of a
 * list
 *
 * @author Giulia Carocari
 */
@Provider
@AddDeletePermission
public class ListProductEditFilter implements ContainerRequestFilter {

	@Context
	private ServletContext servletContext;

	@Context
	private HttpServletRequest servletRequest;
	@Context
	private HttpServletResponse servletResponse;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		ListDAO listDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getListDAO();

		HttpSession session = servletRequest.getSession();
		User user = null;
		if (session != null) {
			user = (User) session.getAttribute("user");
		}

		if (user != null) {
			String uri = servletRequest.getRequestURI();
			uri = uri.substring(uri.lastIndexOf("permission/") + "permission/".length());
			Integer listId = Integer.parseInt(uri.substring(0, uri.indexOf("/")));
			try {
				if (!listDAO.hasAddDeletePermission(listId, user.getId())) {
					// TODO add correct redirection to error page ?
					if (!servletResponse.isCommitted()) {
						servletResponse.sendError(401, "YOU SHALL NOT PASS!");
					}
				}
			} catch (DaoException ex) {
				// TODO add correct redirection to error page ?
				ServiceUtils.handleDAOException(ex, servletResponse);
			}
		}
	}

}
