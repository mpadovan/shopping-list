package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.mysql.TokenDAOImpl;
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

/**
 *
 * @author giuliapeserico
 */
public class DAOFactoryImpl implements DAOFactory {

	private final ExampleDAO EXAMPLE_DAO = new ExampleDAOImpl(this);
	private final UserDAO USER_DAO = new UserDAOimpl(this);
	private final TokenDAO TOKEN_DAO = new TokenDAOImpl();
	private final ProductDAO PRODUCT_DAO = new ProductDAOImpl(this);
	private final PublicProductDAO PUBLIC_PRODUCT_DAO = new PublicProductDAOImpl(this);
	private final ListsCategoryDAO LISTS_CATEGORY_DAO = new ListsCategoryDAOImpl(this);
	private final ProductsCategoryDAO PRODUCTS_CATEGORY_DAO = new ProductsCategoryDAOImpl(this);
	private final ListDAO LIST_DAO = new ListDAOImpl(this);
	private final ListsCategoryImagesDAO LISTS_CATEGORY_IMAGE_DAO = new ListsCategoryImagesDAOImpl(this);
	private final MessageDAOImpl MESSAGE_DAO = new MessageDAOImpl(this);
	private final NotificationDAO NOTIFICATION_DAO = new NotificationDaoImpl(this);

	@Override
	public ExampleDAO getExampleDAO() {
		return EXAMPLE_DAO;
	}

	public MessageDAOImpl getMESSAGE_DAO() {
		return MESSAGE_DAO;
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
