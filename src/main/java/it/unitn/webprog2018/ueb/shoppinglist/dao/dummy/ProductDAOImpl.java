/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.hk2.utilities.reflection.Logger;

/**
 *
 * @author giulia
 */
public class ProductDAOImpl implements ProductDAO {

	private DAOFactory dAOFactory;
	private static List<Product> products;

	public static List<Product> getProducts() {
		return products;
	}

	public ProductDAOImpl(DAOFactory dAOFactory) {
		this.dAOFactory = dAOFactory;
		User user = new User();
		user.setId(1);
		products = new LinkedList<>();
		Product product = new Product();
		product.setCategory(new ProductsCategory());
		product.getCategory().setId(1);
		product.getCategory().setName("Frutta");
		product.setOwner(user);
		product.setName("Ananas");
		product.setLogo("Sole");
		product.setNote("Ananas maturo e dolce");
		products.add(product);

		Product product2 = new Product();
		product2.setCategory(new ProductsCategory());
		product2.getCategory().setId(3);
		product2.getCategory().setName("Verdura");
		product2.setOwner(user);
		product2.setName("Zucchine");
		product2.setLogo("Contadino di fiducia");
		product2.setNote("Zucchine fresche di stagione");
		products.add(product2);
	}

	@Override
	public Boolean addProduct(Product product) throws DaoException {
		Boolean valid = product.isVaildOnCreate(dAOFactory);
		if (valid) {
			product.setId(products.size() + 1);
			products.add(product);
		}
		return valid;
	}

	@Override
	public Boolean updateProduct(Integer productId, Product product) throws DaoException {
		if (product.isVaildOnUpdate(dAOFactory)) {
			for (Product p : products) {
				if (p.getId().equals(product.getId())) {
					p = product;
					return true;
				}
			}
			throw new RecordNotFoundDaoException("The product with id: " + product.getId() + " does not exist");
		}
		return false;
	}

	@Override
	public List<Product> getByUser(Integer id, String query) throws DaoException {
		List<Product> matching = new LinkedList<>();

		for (Product p : products) {
			if (p.getOwner().getId() == id) {
				if (p.getName().toLowerCase().contains(query.toLowerCase())) {
					matching.add(p);
				}
			}
		}
		return matching;
	}

	@Override
	public List<Product> getByUser(Integer userId) throws DaoException {
		List<Product> matching = new LinkedList<>();

		for (Product p : products) {
			if (p.getOwner().getId() == userId) {
				matching.add(p);
			}
		}
		return matching;
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		for (Product p : products) {
			if (p.getId().equals(productId)) {
				return p;
			}
		}
		throw new RecordNotFoundDaoException("Product " + productId + " does not exist");
	}
}
