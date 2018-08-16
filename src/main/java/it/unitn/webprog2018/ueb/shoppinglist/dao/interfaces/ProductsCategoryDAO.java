/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import java.util.List;

/**
 *
 * @author giulia
 */
public interface ProductsCategoryDAO {
	public List<ProductsCategory> getFromQuery(String query);
	public List<ProductsCategory> getAll();
}
