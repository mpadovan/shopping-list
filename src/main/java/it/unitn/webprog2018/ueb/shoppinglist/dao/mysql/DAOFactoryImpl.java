package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ExampleDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.MessageDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.NotificationDAO;
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
	private final ExampleDAO EXAMPLE_DAO;
	private final UserDAO USER_DAO;
	private final TokenDAO TOKEN_DAO;
	private final ProductDAO PRODUCT_DAO;
	private final PublicProductDAO PUBLIC_PRODUCT_DAO;
	private final ListsCategoryDAO LISTS_CATEGORY_DAO;
	private final ProductsCategoryDAO PRODUCTS_CATEGORY_DAO;
	private final ListDAO LIST_DAO;
	private final ListsCategoryImagesDAO LISTS_CATEGORY_IMAGE_DAO;
	private final MessageDAO MESSAGE_DAO;
	private final NotificationDAO NOTIFICATION_DAO;
	
	public DAOFactoryImpl(Connection con) {
		this.con = con;
		this.LISTS_CATEGORY_IMAGE_DAO = new ListsCategoryImagesDAOImpl(con, this);
		this.LIST_DAO = new ListDAOImpl(con, this);
		this.PRODUCTS_CATEGORY_DAO = new ProductsCategoryDAOImpl(con, this);
		this.LISTS_CATEGORY_DAO = new ListsCategoryDAOImpl(con, this);
		this.PUBLIC_PRODUCT_DAO = new PublicProductDAOImpl(con, this);
		this.PRODUCT_DAO = new ProductDAOImpl(con, this);
		this.USER_DAO = new UserDAOimpl(con, this);
		this.EXAMPLE_DAO = new ExampleDAOImpl(con, this);
		this.MESSAGE_DAO = new MessageDAOImpl(con, this);
		this.TOKEN_DAO = new TokenDAOImpl(con, this);
		this.NOTIFICATION_DAO = new NotificationDAOImpl(con,this);
	}

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

	@Override
	public ListsCategoryImagesDAO getListsCategoryImageDAO() {
		return LISTS_CATEGORY_IMAGE_DAO;
	}

	@Override
	public MessageDAO getMessageDAO() {
		return MESSAGE_DAO;
	}

	@Override
	public NotificationDAO getNotificationDAO() {
		return NOTIFICATION_DAO;
	}

}
