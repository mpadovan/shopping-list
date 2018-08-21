package it.unitn.webprog2018.ueb.shoppinglist.filters;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author giuliapeserico
 */
public class RootFilter implements Filter {

	private UserDAO userDAO;

	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		DAOFactory factory = (DAOFactory) this.filterConfig.getServletContext().getAttribute("daoFactory");
		userDAO = factory.getUserDAO();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			String contextPath = req.getContextPath();
			if (!contextPath.endsWith("/")) {
				contextPath += "/";
			}
			String path = req.getRequestURI().substring(req.getContextPath().length());
			
			if (req.getSession(false) == null) {
				Cookie rememberCookie = getRememberCookie(req);
				if (rememberCookie != null) {
					// IDEA: login user from filter
					HttpSession newSession = req.getSession(true);
					try {
						User user = userDAO.getByEmail(CookieCipher.decrypt(rememberCookie.getValue()));
						newSession.setAttribute("user", user);
						((HttpServletResponse) response).sendRedirect(contextPath + "restricted/HomePageLogin/" + user.getId());
						return;
					} catch (RecordNotFoundDaoException ex) {
						//redirect alla pagina di Login - esiste un remember cookie che però non corrisponde all'email dell'utente (es: cambio email)
						//cancellare cookie di remember per evitare altri problemi
					} catch (DaoException ex) {
						Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
						//redirect pagina di errore: Ops qualcosa è andato storto
						//cancellare cookie di remember per evitare altri problemi
					}
				}
			}
			
			if (path.equals("/")) {
				request.getRequestDispatcher("/home").forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
	}

	private Cookie getRememberCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("remember")) {
					return cookie;
				}
			}
		}
		return null;
	}

}
