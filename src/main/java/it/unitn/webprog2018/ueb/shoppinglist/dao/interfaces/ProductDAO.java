/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import java.util.List;

/**
 *
 * @author giulia
 */
public interface ProductDAO {
	
	void addProduct();
	
	List<Product> getAll();
	
	List<Product> getByUser(String userEmail);
}
