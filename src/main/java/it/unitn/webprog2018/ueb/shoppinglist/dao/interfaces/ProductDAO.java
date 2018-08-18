/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author giulia
 */
public interface ProductDAO {
	public Product getProduct(Integer productId) throws DaoException;
	
	public Boolean addProduct(Product product) throws DaoException;
	
	public List<Product> getByUser(Integer userId) throws DaoException;

	public Boolean updateProduct(Integer productId, Product product) throws DaoException;

	public List<Product> getByUser(Integer id, String query) throws DaoException;
}
