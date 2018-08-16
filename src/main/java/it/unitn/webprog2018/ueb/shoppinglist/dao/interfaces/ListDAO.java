/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.Map;

/**
 *
 * @author Giulia Carocari
 */
public interface ListDAO {
	public List getList(int id) throws DaoException;
	
	public Map<PublicProduct, Integer> getPublicProductsOnList(int listId) throws DaoException;
	
	public Map<Product, Integer> getProductsOnList(int listId) throws DaoException;

	public Boolean addProduct(int listId, Product product) throws DaoException;

	public Boolean addPublicProduct(int listId, PublicProduct product) throws DaoException;

	public Boolean isOnList(int listId, PublicProduct product) throws DaoException;
	
	public Boolean isOnList(int listId, Product product) throws DaoException;

	public Boolean updateAmount(int listId, PublicProduct product, int newAmount) throws DaoException;
	
	public Boolean updateAmount(int listId, Product product, int newAmount) throws DaoException;
	
	public Boolean updateAmount(int listId, PublicProduct product) throws DaoException;
	
	public Boolean updateAmount(int listId, Product product) throws DaoException;
	
	public Boolean hasAddDeletePermission(int listId, int userId) throws DaoException;
	
	public Boolean hasModifyPermission(int listId, int userId) throws DaoException;
	
	public Boolean hasDeletePermission(int listId, int userId) throws DaoException;
	
	public java.util.List<List> getByUser(int userId) throws DaoException;
}
