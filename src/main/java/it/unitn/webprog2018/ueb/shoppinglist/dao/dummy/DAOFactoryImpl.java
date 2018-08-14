package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.mysql.TokenDAOImpl;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
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
public class DAOFactoryImpl implements DAOFactory {

	private static final ExampleDAO EXAMPLE_DAO = new ExampleDAOImpl();
	private static final UserDAO USER_DAO = new UserDAOimpl();
	private static final TokenDAO TOKEN_DAO = new TokenDAOImpl();
	private static final ProductDAO PRODUCT_DAO = new ProductDAOImpl();
	private static final PublicProductDAO PUBLIC_PRODUCT_DAO = new PublicProductDAOImpl();
	private static final ListsCategoryDAO LISTS_CATEGORY_DAO = new ListsCategoryDAOImpl();
	private static final ProductsCategoryDAO PRODUCTS_CATEGORY_DAO = new ProductsCategoryDAOImpl();
	private static final ListDAO LIST_DAO = new ListDAOImpl();
	
	@Override
	public ExampleDAO getExampleDAO() {
		return EXAMPLE_DAO;
	}
	
	@Override
	public UserDAO getUserDAO() {
		return USER_DAO;
	}
	
	@Override
	public TokenDAO getTokenDAO() {
		return TOKEN_DAO;
	}

	@Override
	public ProductDAO getProductDAO() {
		return PRODUCT_DAO;
	}

	@Override
	public PublicProductDAO getPublicProductDAO() {
		return PUBLIC_PRODUCT_DAO;
	}

	@Override
	public ListsCategoryDAO getListsCategoryDAO() {
		return LISTS_CATEGORY_DAO;
	}

	@Override
	public ProductsCategoryDAO getProductsCategoryDAO() {
		return PRODUCTS_CATEGORY_DAO;
	}

	@Override
	public ListDAO getListDAO() {
		return LIST_DAO;
	}

}
