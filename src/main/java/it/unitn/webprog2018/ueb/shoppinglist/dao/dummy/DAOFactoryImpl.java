package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ExampleDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;


/**
 *
 * @author giuliapeserico
 */
public class DAOFactoryImpl implements DAOFactory {

	private static final ExampleDAO EXAMPLE_DAO = new ExampleDAOImpl();

	@Override
	public ExampleDAO getExampleDAO() {
		return EXAMPLE_DAO;
	}
	
	public UserDAO getUserDAO() {
		return new UserDAOimpl();
	}

}
