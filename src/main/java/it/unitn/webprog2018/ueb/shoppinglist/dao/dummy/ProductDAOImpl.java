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
		product.setOwner(1);
		product.setName("Ananas");
		product.setLogo("Sole");
		product.setNote("Ananas maturo e dolce");
		products.add(product);

		Product product2 = new Product();
		product2.setCategory("Verdura");
		product2.setOwner(1);
		product2.setName("Zucchine");
		product2.setLogo("Contadino di fiducia");
		product2.setNote("Zucchine fresche di stagione");
		products.add(product2);
	}

	@Override
	public boolean addProduct(Product product) {
		products.add(product);
		return true;
	}
	/*
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
	*/
	@Override
	public void updateProduct(Integer productId, Product product) {
		for (Product p : products) {
			if (p.getId().equals(product.getId())) {
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
	public List<Product> getByUser(int id, String query) {
		List<Product> matching = new LinkedList<>();
		
		System.out.println("Checkin out custom products");
		for (Product p : products) {
			if (p.getOwner() == id) {
				if (p.getName().toLowerCase().contains(query.toLowerCase())) {
					matching.add(p);
				}
			}
		}
		return matching;
	}
}
