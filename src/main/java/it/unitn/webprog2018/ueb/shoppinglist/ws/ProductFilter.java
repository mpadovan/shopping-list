/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.ProductPermission;
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
 * Filter that checks whether a user has the permission to access a certain
 * personal product
 *
 * @author Giulia Carocari
 */
@Provider
@ProductPermission
public class ProductFilter implements ContainerRequestFilter {
	
	@Context
	private ServletContext servletContext;

	@Context
	private HttpServletRequest servletRequest;
	@Context
	private HttpServletResponse servletResponse;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		ProductDAO productDAO = ((DAOFactory) servletContext.getAttribute("daoFactory")).getProductDAO();

		HttpSession session = servletRequest.getSession();
		User user = null;
		if (session != null) {
			user = (User) session.getAttribute("user");
		}

		if (user != null) {
			String uri = servletRequest.getRequestURI();
			if (uri.endsWith("/")) {
				// removed final / character
				uri = uri.substring(0, uri.lastIndexOf("/"));
			}
			Integer productId = Integer.parseInt(uri.substring(uri.lastIndexOf("/")+1, uri.length()));
			try {
				if(!productDAO.getProduct(productId).getOwner().getId().equals(user.getId())) {
					// TODO USE CORRECT ERROR PAGE
					if (!servletResponse.isCommitted()) {
						servletResponse.sendError(401, "YOU SHALL NOT PASS!");
					}
				}
			} catch (DaoException ex) {
				// TODO add correct redirection to error page ?
				HttpErrorHandler.handleDAOException(ex, servletResponse);
			}
		}
	}
}
