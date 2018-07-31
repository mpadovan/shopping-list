package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import java.sql.Connection;

/**
 *
 * @author giuliapeserico
 */
public abstract class AbstractDAO {

	private final Connection con;

	public AbstractDAO(Connection con) {
		this.con = con;
	}

	public Connection getCon() {
		return con;
	}

}
