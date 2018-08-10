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
		product.setCategory("Fruit");
		product.setEmail("mariorossi@gmail.com");
		product.setName("Ananas");
		product.setLogo("Sole");
		product.setNote("Ananas maturo e dolce");
		products.add(product);
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
		for(Product p : products) {
			if (p.getName().equals(product.getName()) &&
					p.getEmail().equals(product.getEmail())) {
				p = product;
				return;
			}
		}
		try {
			throw new RecordNotFoundDaoException("The prduct you want to update does not exist");
		} catch (RecordNotFoundDaoException ex) {
			Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
