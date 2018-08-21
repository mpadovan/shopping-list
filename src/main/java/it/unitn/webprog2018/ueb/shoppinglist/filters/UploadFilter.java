/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.filters;

import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.io.IOException;
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
 *
 * @author Giulia Carocari
 */
public class UploadFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
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
				((HttpServletResponse) response).sendRedirect(contextPath + "Login");
			} else {
				String uri = ((HttpServletRequest) request).getRequestURI();
				if (!Pattern.matches(".*/uploads/restricted/" + user.getId() + "/.*", uri)) {
					// TODO add redirection to correct error page.
					((HttpServletResponse) response).sendError(401, "YOU SHALL NOT PASS!\n"
							+ "The resource you are trying to access is none of your business.\n"
							+ "If you think you have the right to access it, prove it by logging in: localhost:8080/ShoppingList/Login");
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
