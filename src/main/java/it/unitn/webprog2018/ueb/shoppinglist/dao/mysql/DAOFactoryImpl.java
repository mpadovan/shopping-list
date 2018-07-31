package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ExampleDAO;
import java.sql.Connection;

/**
 *
 * @author giuliapeserico
 */
public class DAOFactoryImpl implements DAOFactory {

	private final Connection con;

	public DAOFactoryImpl(Connection con) {
		this.con = con;
	}

	@Override
	public ExampleDAO getExampleDAO() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
