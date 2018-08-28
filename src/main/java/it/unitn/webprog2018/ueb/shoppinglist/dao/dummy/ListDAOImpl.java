/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giulia Carocari
 */
public class ListDAOImpl implements ListDAO {

	private DAOFactory dAOFactory;
	private java.util.List<List> lists;
	private final Map<PublicProduct, Integer> publicProductsOnList1 = new HashMap<>();
	private final Map<PublicProduct, Integer> publicProductsOnList2 = new HashMap<>();
	private final Map<Product, Integer> productsOnList1 = new HashMap<>();
	private final Map<Product, Integer> productsOnList2 = new HashMap<>();

	public ListDAOImpl(DAOFactory dAOFactory) {
		this.dAOFactory = dAOFactory;
		lists = new LinkedList<>();
		List l1 = new List();
		User user = new User();
		user.setId(1);
		l1.setCategory(new ListsCategory());
		l1.getCategory().setId(1);
		l1.getCategory().setName("Supermercato");
		l1.setDescription("Lista per la spesa dell'appartamento");
		l1.setId(1);
		l1.setImage("https://previews.123rf.com/images/viperagp/viperagp1601/viperagp160100421/50948328-supermarket-interior-with-shelves-full-of-various-products-and-empty-trolley-basket.jpg");
		l1.setName("Orvea");
		l1.setOwner(user);

		lists.add(l1);

		List l2 = new List();
		l2.setCategory(new ListsCategory());
		l2.getCategory().setId(2);
		l2.getCategory().setName("Ferrmenta");
		l2.setDescription("Lista delle cose veramente importanti");
		l2.setId(2);
		l2.setImage("https://www.bricoman.it/media/foto_articoli/2016/01/10040619_HR_PRO_V01_2016_01_11_151504_original.JPG");
		l2.setName("EuroBrico");
		l2.setOwner(user);

		lists.add(l2);

		java.util.List<PublicProduct> publicProducts = null;
		try {
			publicProducts = PublicProductDAOImpl.getPublicProducts();
		} catch (DaoException ex) {
			Logger.getLogger(ListDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		for (int i = 0; i < publicProducts.size(); i++) {
			if (i % 2 == 0) {
				publicProductsOnList1.put(publicProducts.get(i), (int) (Math.random() * 10 + 1));
			} else {
				publicProductsOnList2.put(publicProducts.get(i), (int) (Math.random() * 10 + 1));
			}
		}

		java.util.List<Product> products = ProductDAOImpl.getProducts();
		for (int i = 0; i < products.size(); i++) {
			if (i % 2 == 0 && products.get(i).getOwner().getId().equals(1)) {
				productsOnList1.put(products.get(i), (int) (Math.random() * 10 + 1));
			} else if (products.get(i).getOwner().getId().equals(1)) {
				productsOnList2.put(products.get(i), (int) (Math.random() * 10 + 1));
			}
		}

	}

	@Override
	public Map<PublicProduct, Integer> getPublicProductsOnList(Integer listId) throws DaoException {
		switch (listId) {
			case 1:
				return publicProductsOnList1;
			case 2:
				return publicProductsOnList2;
			default:
				throw new RecordNotFoundDaoException("List " + listId + " not found");
		}
	}

	@Override
	public List getList(Integer id) throws DaoException {
		for (List l : lists) {
			if (l.getId() == id) {
				return l;
			}
		}
		throw new RecordNotFoundDaoException("List " + id + "does not exist");
	}

	@Override
	public Boolean addProduct(Integer listId, Product product) throws DaoException {
		if (listId == 1) {
			productsOnList1.putIfAbsent(product, 1);
		} else if (listId == 2) {
			productsOnList2.putIfAbsent(product, 1);
		}
		return true;
	}

	@Override
	public Boolean addPublicProduct(Integer listId, PublicProduct product) throws DaoException {
		if (listId == 1) {
			publicProductsOnList1.putIfAbsent(product, 1);
			return true;
		} else if (listId == 2) {
			publicProductsOnList1.putIfAbsent(product, 1);
			return true;
		}
		return false;
	}

	@Override
	public Boolean isOnList(Integer listId, PublicProduct product) throws DaoException {
		if (listId == 1) {
			return publicProductsOnList1.containsKey(product);
		} else if (listId == 2) {
			return publicProductsOnList2.containsKey(product);
		}
		return false;
	}

	@Override
	public Boolean updateAmount(Integer listId, PublicProduct product, Integer newAmount) throws DaoException {
		if (listId == 1) {
			if (publicProductsOnList1.containsKey(product)) {
				publicProductsOnList1.replace(product, newAmount);
				return true;
			} else {
				return false;
			}
		}
		if (listId == 2) {
			if (publicProductsOnList2.containsKey(product)) {
				publicProductsOnList2.replace(product, newAmount);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Map<Product, Integer> getProductsOnList(Integer listId) throws DaoException {
		if (listId == 1) {
			return productsOnList1;
		} else if (listId == 2) {
			return productsOnList2;
		}
		return null;
	}

	@Override
	public Boolean isOnList(Integer listId, Product product) {
		if (listId == 1) {
			return productsOnList1.containsKey(product);
		} else if (listId == 2) {
			return productsOnList2.containsKey(product);
		}
		return false;
	}

	@Override
	public Boolean updateAmount(Integer listId, Product product, Integer newAmount) throws DaoException {
		if (listId == 1) {
			if (productsOnList1.containsKey(product)) {
				productsOnList1.replace(product, newAmount);
				return true;
			} else {
				return false;
			}
		}
		if (listId == 2) {
			if (productsOnList2.containsKey(product)) {
				productsOnList2.replace(product, newAmount);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Boolean hasAddDeletePermission(Integer listId, Integer userId) throws DaoException {
		if (listId == 1) {
			if (userId == 1) {
				return true;
			} else {
				return false;
			}
		} else if (listId == 2) {
			if (userId == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Boolean hasModifyPermission(Integer listId, Integer userId) throws DaoException {
		if (listId == 1) {
			if (userId == 1) {
				return true;
			} else {
				return false;
			}
		} else if (listId == 2) {
			if (userId == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Boolean hasDeletePermission(Integer listId, Integer userId) throws DaoException {
		if (listId == 1) {
			if (userId == 1) {
				return true;
			} else {
				return false;
			}
		} else if (listId == 2) {
			if (userId == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Boolean updateAmount(Integer listId, PublicProduct product) throws DaoException {
		if (listId == 1) {
			if (publicProductsOnList1.containsKey(product)) {
				publicProductsOnList1.replace(product, publicProductsOnList1.get(product) + 1);
				return true;
			} else {
				return false;
			}
		}
		if (listId == 2) {
			if (publicProductsOnList2.containsKey(product)) {
				publicProductsOnList2.replace(product, publicProductsOnList2.get(product) + 1);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public Boolean updateAmount(Integer listId, Product product) throws DaoException {
		if (listId == 1) {
			if (productsOnList1.containsKey(product)) {
				productsOnList1.replace(product, productsOnList1.get(product) + 1);
				return true;
			} else {
				return false;
			}
		}
		if (listId == 2) {
			if (productsOnList2.containsKey(product)) {
				productsOnList2.replace(product, productsOnList2.get(product) + 1);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public java.util.List<List> getByUser(Integer userID) throws DaoException {
		java.util.List<List> match = new LinkedList<>();
		for (List l : lists) {
			if (l.getOwner().getId() == userID) {
				match.add(l);
			}
		}
		return match;
	}

	@Override
	public Boolean deleteFromList(Integer listId, PublicProduct product) throws DaoException {
		if (listId == 1) {
			if (publicProductsOnList1.containsKey(product)) {
				publicProductsOnList1.remove(product);
				return true;
			} else {
				throw new RecordNotFoundDaoException("Public product with id " + product.getId() + "not found on list with id " + listId);
			}
		}
		if (listId == 2) {
			if (publicProductsOnList2.containsKey(product)) {
				publicProductsOnList2.remove(product);
				return true;
			} else {
				throw new RecordNotFoundDaoException("Public product with id " + product.getId() + "not found on list with id " + listId);
			}
		}
		return false;
	}

	@Override
	public Boolean deleteFromList(Integer listId, Product product) throws DaoException {
		if (listId == 1) {
			if (productsOnList1.containsKey(product)) {
				productsOnList1.remove(product);
				return true;
			} else {
				throw new RecordNotFoundDaoException("Product with id " + product.getId() + "not found on list with id " + listId);
			}
		}
		if (listId == 2) {
			if (productsOnList2.containsKey(product)) {
				productsOnList2.remove(product);
				return true;
			} else {
				throw new RecordNotFoundDaoException("Product with id " + product.getId() + "not found on list with id " + listId);
			}
		}
		return false;
	}

	@Override
	public List getList(String name, User owner) throws DaoException {
		for (List u : lists) {
			if (u.getName().equals(name) && u.getOwner().getId().equals(owner.getId())) {
				return u;
			}
		}
		throw new RecordNotFoundDaoException("List with name: " + name + " not found");
	}

	@Override
	public Boolean hasViewPermission(Integer listId, Integer userId) throws DaoException {
		if (null == listId) {
			throw new RecordNotFoundDaoException("List " + listId + " not found");
		} else switch (listId) {
			case 1:
				return userId == 1;
			case 2:
				return userId == 1;
			default:
				throw new RecordNotFoundDaoException("List " + listId + " not found");
		}
	}

	@Override
	public java.util.List<List> getPersonalLists(Integer id) {
		if (id == 1) {
			java.util.List<List> l = new LinkedList<>();
			l.add(lists.get(1));
			return l;
		}
		return new LinkedList<>();
	}

	@Override
	public java.util.List<List> getSharedLists(Integer id) {
		if (id == 1) {
			java.util.List<List> l = new LinkedList<>();
			l.add(lists.get(0));
			return l;
		}
		return new LinkedList<>();
	}

	@Override
	public java.util.List<Integer> getConnectedUsersIds(Integer listId) throws DaoException {
		java.util.List<Integer> list = new LinkedList<>();
		list.add(1);
		list.add(2);
		return list;
	}

	@Override
	public java.util.List<User> getConnectedUsers(Integer listId) throws DaoException {
		java.util.List<User> list = new LinkedList<>();
		list.add(dAOFactory.getUserDAO().getById(1));
		list.add(dAOFactory.getUserDAO().getById(2));
		return list;
	}
}
