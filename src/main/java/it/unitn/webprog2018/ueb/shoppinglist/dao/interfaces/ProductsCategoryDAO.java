/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giulia
 */
public interface ProductsCategoryDAO {

	public List<ProductsCategory> getFromQuery(String query) throws DaoException;
	
	public Boolean addProductsCategory(ProductsCategory productCategory) throws DaoException;
	
	public List<ProductsCategory> getAll() throws DaoException;
	
	public ProductsCategory getById(Integer id) throws DaoException;
}
