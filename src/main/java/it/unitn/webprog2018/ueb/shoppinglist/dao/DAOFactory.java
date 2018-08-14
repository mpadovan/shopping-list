package it.unitn.webprog2018.ueb.shoppinglist.dao;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ExampleDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
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
	
	public ProductDAO getProductDAO();

	public PublicProductDAO getPublicProductDAO();
	
	public ListsCategoryDAO getListsCategoryDAO();
	
	public ProductsCategoryDAO getProductsCategoryDAO();

	public ListDAO getListDAO();
	
}
