/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
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

	private java.util.List<List> lists;
	private final Map<PublicProduct, Integer> publicProductsOnList1 = new HashMap<>();
	private final Map<PublicProduct, Integer> publicProductsOnList2 = new HashMap<>();
	private final Map<Product, Integer> productsOnList1 = new HashMap<>();
	private final Map<Product, Integer> productsOnList2 = new HashMap<>();

	public ListDAOImpl() {
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
		DAOFactoryImpl factory = new DAOFactoryImpl();

		PublicProductDAO ppdaoi = factory.getPublicProductDAO();
		java.util.List<PublicProduct> publicProducts = ppdaoi.getFromQuery("");
		for (int i = 0; i < publicProducts.size(); i++) {
			if (i % 2 == 0) {
				publicProductsOnList1.put(publicProducts.get(i), (int) (Math.random() * 10 + 1));
			} else {
				publicProductsOnList2.put(publicProducts.get(i), (int) (Math.random() * 10 + 1));
			}
		}
		ProductDAO pdaoi = factory.getProductDAO();
		java.util.List<Product> products = null;
		try {
			products = pdaoi.getByUser(1);
			for (int i = 0; i < products.size(); i++) {
				if (i % 2 == 0) {
					productsOnList1.put(products.get(i), (int) (Math.random() * 10 + 1));
				} else {
					productsOnList2.put(products.get(i), (int) (Math.random() * 10 + 1));
				}
			}
		} catch (DaoException ex) {
			Logger.getLogger(ListDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public Map<PublicProduct, Integer> getPublicProductsOnList(int listId) {
		if (listId == 1) {
			return publicProductsOnList1;
		} else if (listId == 2) {
			return publicProductsOnList2;
		}
		return null;
	}

	@Override
	public List getList(int id) {
		for (List l : lists) {
			if (l.getId() == id) {
				return l;
			}
		}
		return null;
	}

	@Override
	public boolean addProduct(int listId, Product product) {
		if (listId == 1) {
			productsOnList1.putIfAbsent(product, 1);
		} else if (listId == 2) {
			productsOnList2.putIfAbsent(product, 1);
		}
		return true;
	}

	@Override
	public boolean addPublicProduct(int listId, PublicProduct product) {
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
	public boolean isOnList(int listId, PublicProduct product) {
		if (listId == 1) {
			return publicProductsOnList1.containsKey(product);
		} else if (listId == 2) {
			return publicProductsOnList2.containsKey(product);
		}
		return false;
	}

	@Override
	public boolean updateAmount(int listId, PublicProduct product, int newAmount) {
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
	public Map<Product, Integer> getProductsOnList(int listId) {
		if (listId == 1) {
			return productsOnList1;
		} else if (listId == 2) {
			return productsOnList2;
		}
		return null;
	}

	@Override
	public boolean isOnList(int listId, Product product) {
		if (listId == 1) {
			return productsOnList1.containsKey(product);
		} else if (listId == 2) {
			return productsOnList2.containsKey(product);
		}
		return false;
	}

	@Override
	public boolean updateAmount(int listId, Product product, int newAmount) {
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
	public boolean hasAddDeletePermission(int listId, int userId) {
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
	public boolean hasModifyPermission(int listId, int userId) {
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
	public boolean hasDeletePermission(int listId, int userId) {
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

}
