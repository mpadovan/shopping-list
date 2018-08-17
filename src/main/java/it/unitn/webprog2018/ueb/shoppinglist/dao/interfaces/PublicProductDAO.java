/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.List;

/**
 *
 * @author giulia
 */
public interface PublicProductDAO {
	public List<PublicProduct> getAll() throws DaoException;
	
	public List<PublicProduct> getFromQuery(String query) throws DaoException;
	
	public PublicProduct getById(Integer id) throws DaoException;
	
	public PublicProduct getByName(String name) throws DaoException;

	public Boolean updateProduct(Integer id, PublicProduct product) throws DaoException;

	public Boolean addProduct(PublicProduct product) throws DaoException;
	
	public Boolean deleteProduct(Integer id) throws DaoException;
}
