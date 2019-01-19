/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.filters;

import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
 * Filters requests to the administration servlets to prevent users without
 * admin privileges to access them.
 *
 * @author Giulia Carocari
 */
public class AdministrationFilter implements Filter {

	/**
	 * FilterConfig object associated with this filter
	 */
	private FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * Filters the requests to see if the user in this session has
	 * administration permissions. If not, it sends an error to the client.
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {

			ServletContext servletContext = ((HttpServletRequest) request).getServletContext();
			HttpSession session = ((HttpServletRequest) request).getSession(false);
			User user = null;
			if (session != null) {
				user = (User) session.getAttribute("user");
			}

			String contextPath = servletContext.getContextPath();
			if (!contextPath.endsWith("/")) {
				contextPath += "/";
			}

			if (user == null) { // Should never happen, but just in case
				((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "Login"));
				return;
			} else {
				if (!user.isAdministrator()) {
					HttpErrorHandler.sendError401((HttpServletResponse) response);
					return;
				}
			}
		}
		if (!response.isCommitted()) {
			Throwable problem = null;
			try {
				chain.doFilter(request, response);
			} catch (Throwable t) {
				problem = t;
				t.printStackTrace();
				sendProcessingError(problem, response);
			}
		}
	}

	@Override
	public void destroy() {
	}

	/**
	 * Sends processing error to client, either stack trace exception or generic
	 * message
	 *
	 * @param t Throwable that contains information about the processing error
	 * @param response Response used to send the processing error information
	 */
	private void sendProcessingError(Throwable t, ServletResponse response) {
		String stackTrace = getStackTrace(t);
		if (stackTrace != null && !stackTrace.equals("")) {
			try {
				response.setContentType("text/html");
				PrintStream ps = new PrintStream(response.getOutputStream());
				PrintWriter pw = new PrintWriter(ps);
				pw.print("<html>\n<head>\n<title>Errore</title>\n</head>\n<body>\n");
				pw.print("<h1>La risorsa non è stata processata correttamente</h1>\n<pre>\n");
				pw.print(stackTrace);
				pw.print("</pre></body>\n</html>");
				pw.close();
				ps.close();
				response.getOutputStream().close();
			} catch (IOException ex) {
			}
		} else {
			try {
				PrintStream ps = new PrintStream(response.getOutputStream());
				t.printStackTrace(ps);
				ps.close();
				response.getOutputStream().close();
			} catch (IOException ex) {
			}
		}
	}

	/**
	 *
	 * @param t Throwable that contains the stack trace to be obtained
	 * @return String object containing the stack trace of t
	 */
	private static String getStackTrace(Throwable t) {
		String stackTrace = "";
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (IOException ex) {
		}
		return stackTrace;
	}

}
