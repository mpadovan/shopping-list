/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author giulia
 */
public interface ProductDAO {
	
	public void addProduct(Product product);
	
	public List<Product> getAll();
	
	public List<Product> getByUser(String userEmail);

	public void updateProduct(Product product);

	public List<Product> getByUser(String email, StringTokenizer filter, String sortBy);
}
