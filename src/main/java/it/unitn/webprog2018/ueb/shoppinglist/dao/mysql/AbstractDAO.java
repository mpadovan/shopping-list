package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import java.sql.Connection;

/**
 *
 * @author giuliapeserico
 */
public abstract class AbstractDAO {
	protected DAOFactory dAOFactory;
	private final Connection con;

	public AbstractDAO(Connection con, DAOFactory dAOFactory) {
		this.con = con;
		this.dAOFactory=dAOFactory;
	}

	public Connection getCon() {
		return con;
	}

}
