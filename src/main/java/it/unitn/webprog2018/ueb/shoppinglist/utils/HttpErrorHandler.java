/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.utils;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.ws.ProductWebService;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that implements shared methods for exception and error handling.
 * Error messages are meant to keep the reporting of http errors uniform across the whole application,
 * each error code is mapped to one or more message strings
 *
 * @author Giulia Carocari
 */
public class HttpErrorHandler {
	
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
