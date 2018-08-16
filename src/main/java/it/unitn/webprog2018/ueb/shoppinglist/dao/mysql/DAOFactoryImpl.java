package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ExampleDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
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
		return new ExampleDAOImpl(con, this);
	}

	@Override
	public UserDAO getUserDAO() {
		return new UserDAOimpl(con, this);
	}

	@Override
	public TokenDAO getTokenDAO() {
		return new TokenDAOImpl();
	}

	@Override
	public ProductDAO getProductDAO() {
		return new ProductDAOImpl(con, this);
	}

	@Override
	public PublicProductDAO getPublicProductDAO() {
		return new PublicProductDAOImpl(con, this);
	}

	@Override
	public ListsCategoryDAO getListsCategoryDAO() {
		return new ListsCategoryDAOImpl(con, this);
	}

	@Override
	public ProductsCategoryDAO getProductsCategoryDAO() {
		return new ProductsCategoryDAOImpl(con, this);
	}

	@Override
	public ListDAO getListDAO() {
		return new ListDAOImpl(con, this);
	}

}
