/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.utils;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;
import it.unitn.webprog2018.ueb.shoppinglist.servlets.admin.ChangeImageAdminServlet;
import it.unitn.webprog2018.ueb.shoppinglist.ws.ProductWebService;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that implements shared methods for exception and error handling.
 * Error messages are meant to keep the reporting of http errors uniform across the whole application,
 * each error code is mapped to one or more message strings
 *
 * @author Giulia Carocari
 */
public class HttpErrorHandler {
	private static String contextPath;
	
	/**
	 * Sets the context path of the application that uses the <code>HttpErrorHandler</code>
	 * @param contextPath 
	 */
	public static void setContextPath(String contextPath) {
		if (!contextPath.endsWith("/")) {
			contextPath += "/";
		}
		HttpErrorHandler.contextPath = contextPath;
	}

	
	public static final String ERROR_MESSAGE_401 = "YOU SHALL NOT PASS!\n"
							+ "The resource you are trying to access is none of your business.\n"
							+ "If you think you have the right to access it, prove it by logging in: localhost:8080/ShoppingList/Login";
	
	
	public static final String ERROR_MESSAGE_404 = "The resource you are trying to access does not exist on our system";
	
	
	
	/**
	 * Prepares the string in input to be used for performing SQL string
	 * matching
	 *
	 * @param search The plain text string to be edited
	 * @return The string where the separator characters are substituted by the
	 * SQL wildcard character "%"
	 */
	public static String getQuery(String search) {
		StringTokenizer filter = null;
		String query = "";
		if (search != null) {
			filter = new StringTokenizer(search, "-");
			while (filter.hasMoreTokens()) {
				// TODO add sql wildcard
				query += filter.nextToken();
			}
		}
		return query;
	}
	
	/**
	 * Handles the correct setting to use jsp validation when using a form with multipart content.
	 * 
	 * @param ex		Exception thrown by the servlet
	 * @param entity	The entity that the form was accessing
	 * @param response	The response associated with the request object that submitted the form data
	 * @param path		the path to which the response has to be redirected
	 */
	/*
	public static void handleUploadError(Exception ex, AbstractEntity entity, HttpServletResponse response, String path) {
		// It is not a fatal error, we ask the user to try again
		Logger.getLogger(ChangeImageAdminServlet.class.getName()).log(Level.WARNING, null, ex);
		entity.setError("image", "Non è stato possibile salvare l'immagine, riprova più tardi o contatta un amministratore");
		try {
			response.sendRedirect(path);
		} catch (IOException ex1) {
			Logger.getLogger(HttpErrorHandler.class.getName()).log(Level.SEVERE, null, ex1);
		}
	}
*/
	/**
	 * Handles the response to be committed in case a DaoException is thrown. If
	 * the exception is an instance of a RecordNotFoundDaoException then the
	 * response returns Error 404 to the client, else it returns a general Error
	 * 500.
	 *
	 * @param ex DaoException that has been raised by some DAO instance
	 * @param response HttpServletResponse that returns the error to the client
	 */
	public static void handleDAOException(DaoException ex, HttpServletResponse response) {
		ex.printStackTrace();
		if (ex instanceof RecordNotFoundDaoException) {
			try {
				if (!response.isCommitted()) {
					response.sendError(404, "The resource was not found on our system");
				}
			} catch (IOException ex1) {
				Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex1);
			}
		} else {
			try {
				if (!response.isCommitted()) {
					response.sendError(500, "Something went wrong, please try again");
				}
			} catch (IOException ex1) {
				Logger.getLogger(ProductWebService.class.getName()).log(Level.SEVERE, null, ex1);
			}
		}
	}
}
