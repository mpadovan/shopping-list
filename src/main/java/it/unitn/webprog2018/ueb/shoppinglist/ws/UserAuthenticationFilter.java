/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
import it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.Authentication;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 * Filter that checks if a user has the right to access user-specific
 * information in a web service. If the user is trying to access information
 * different than his own he will be sent an Error 401.
 *
 * @author Giulia Carocari
 */
@Provider
@Authentication
public class UserAuthenticationFilter implements ContainerRequestFilter {
	
	@Context
	private HttpServletRequest servletRequest;
	@Context
	private HttpServletResponse servletResponse;

	/**
	 * Filters the request to the methods in the web services annotated with @Authentication.
	 * 
	 * @param requestContext
	 * @throws IOException
	 * 
	 * @see it.unitn.webprog2018.ueb.shoppinglist.ws.annotations.Authentication
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		HttpSession session = servletRequest.getSession();
		User user = null;
		if (session != null) {
			user = (User) session.getAttribute("user");
		} else {
			servletResponse.sendRedirect("Login");
		}

		if (user != null) {
			String uri = servletRequest.getRequestURI();
			if (!uri.endsWith("/")) {
				uri += "/";
			}
			if (!Pattern.matches(".*/restricted/" + user.getHash()+ "/.*", uri)) {
				// TODO add redirection to correct error page.
				if (!servletResponse.isCommitted()) {
					HttpErrorHandler.sendError401(servletResponse);
				}
			}
		}
	}
}
