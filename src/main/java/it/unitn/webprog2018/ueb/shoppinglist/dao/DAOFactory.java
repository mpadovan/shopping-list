package it.unitn.webprog2018.ueb.shoppinglist.dao;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ExampleDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;

/**
 *
 * @author giuliapeserico
 */
public interface DAOFactory {

	public ExampleDAO getExampleDAO();
	
	public UserDAO getUserDAO();
	
	public TokenDAO getTokenDAO();
	
}
