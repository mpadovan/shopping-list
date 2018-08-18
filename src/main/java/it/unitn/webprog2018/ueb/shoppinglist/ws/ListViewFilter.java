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
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.ViewPermission;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Giulia Carocari
 */
@Provider
@ViewPermission
public class ListViewFilter implements ContainerRequestFilter {

	@Context
	private ServletContext servletContext;

	@Context
	private HttpServletRequest servletRequest;
	@Context
	private HttpServletResponse servletResponse;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		ListDAO listDAO = ((DAOFactory)servletContext.getAttribute("daoFactory")).getListDAO();
		
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
				if (!user.getId().equals(listDAO.getList(listId).getOwner().getId()) &&
						!listDAO.hasViewPermission(listId, user.getId())) {
					servletResponse.sendError(401, "YOU SHALL NOT PASS!");
				}
			} catch (DaoException ex) {
				Logger.getLogger(ListProductEditFilter.class.getName()).log(Level.SEVERE, null, ex);
				// TODO redirect to oops page
			}
		}
	}
}
