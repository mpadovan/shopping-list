/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import java.util.ArrayList;

/**
 *
 * @author giulia
 */
public interface ProductsCategoryDAO {
	public ArrayList<ProductsCategory> getFromQuery(String query) throws DaoException;
	
	public boolean addProductsCategory(ProductsCategory productCategory) throws DaoException;
}
