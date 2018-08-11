/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giulia
 */
public class ProductDAOImpl implements ProductDAO {

	List<Product> products;

	public ProductDAOImpl() {
		products = new LinkedList<>();
		Product product = new Product();
		product.setCategory("Frutta");
		product.setEmail("mariorossi@gmail.com");
		product.setName("Ananas");
		product.setLogo("Sole");
		product.setNote("Ananas maturo e dolce");
		products.add(product);

		Product product2 = new Product();
		product2.setCategory("Verdura");
		product2.setEmail("mariorossi@gmail.com");
		product2.setName("Zucchine");
		product2.setLogo("Contadino di fiducia");
		product2.setNote("Zucchine fresche di stagione");
		products.add(product2);
	}

	@Override
	public void addProduct(Product product) {
		products.add(product);
	}

	@Override
	public List<Product> getAll() {
		return products;
	}

	@Override
	public List<Product> getByUser(String email) {
		List<Product> owned = new LinkedList<>();
		for (Product p : products) {
			if (p.getEmail().equals(email)) {
				owned.add(p);
			}
		}
		return owned;
	}

	@Override
	public void updateProduct(Product product) {
		for (Product p : products) {
			if (p.getName().equals(product.getName())
					&& p.getEmail().equals(product.getEmail())) {
				p = product;
				return;
			}
		}
		try {
			throw new RecordNotFoundDaoException("The product you want to update does not exist");
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public List<Product> getByUser(String email, String query) {
		List<Product> matching = new LinkedList<>();

		if (query == null) {
			return getByUser(email);
		}
		System.out.println("Checkin out custom products");
		for (Product p : products) {
			if (p.getEmail().equals(email)) {
				System.out.println("Checking product " + p.getName());
				if (p.getName().toLowerCase().contains(query.toLowerCase())) {
					matching.add(p);
					System.out.println("Found " + query + " in " + p.getName());
				}
			}
		}
		return matching;
	}
}
