/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.ws;

import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
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
 *
 * @author Giulia Carocari
 */
@Provider
@Authentication
public class UserAuthenticationFilter implements ContainerRequestFilter {

	@Context
	private ServletContext servletContext;
	@Context
	private HttpServletRequest servletRequest;
	@Context
	private HttpServletResponse servletResponse;

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
			if (!Pattern.matches(".*/restricted/" + user.getId() + "/.*", uri)) {
				// TODO add redirection to correct error page.
				if (!servletResponse.isCommitted()) {
					((HttpServletResponse) servletResponse).sendError(401, "YOU SHALL NOT PASS!\n"
							+ "The resource you are trying to access is none of your business.\n"
							+ "If you think you have the right to access it, prove it by logging in: localhost:8080/ShoppingList/Login");
				}
			}
		}
	}
}
