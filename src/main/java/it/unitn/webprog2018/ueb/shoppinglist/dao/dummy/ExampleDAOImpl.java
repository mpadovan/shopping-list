/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;


import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ExampleDAO;
import java.sql.Connection;

/**
 *
 * @author giuliapeserico
 */
public class ExampleDAOImpl implements ExampleDAO{
	DAOFactory dAOFactory;
	
	public ExampleDAOImpl(DAOFactory dAOFactory) {
		this.dAOFactory=dAOFactory;
	}

	@Override
	public void example() throws DaoException{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
