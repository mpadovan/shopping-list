/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ExampleDAO;
import java.sql.Connection;

/**
 *
 * @author giuliapeserico
 */
public class ExampleDAOImpl extends AbstractDAO implements ExampleDAO{
	
	public ExampleDAOImpl(Connection con) {
		super(con);
	}

	@Override
	public void example() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
