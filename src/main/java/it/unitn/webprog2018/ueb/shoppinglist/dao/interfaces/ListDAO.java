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
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.util.Map;

/**
 *
 * @author Giulia Carocari
 */
public interface ListDAO {
	public List getList(Integer id) throws DaoException;
	
	public Map<PublicProduct, Integer> getPublicProductsOnList(Integer listId) throws DaoException;
	
	public Map<Product, Integer> getProductsOnList(Integer listId) throws DaoException;

	public Boolean addProduct(Integer listId, Product product) throws DaoException;

	public Boolean addPublicProduct(Integer listId, PublicProduct product) throws DaoException;

	public Boolean isOnList(Integer listId, PublicProduct product) throws DaoException;
	
	public Boolean isOnList(Integer listId, Product product) throws DaoException;
	
	public List getList(String name, User owner) throws DaoException;

	public Boolean updateAmount(Integer listId, PublicProduct product, Integer newAmount) throws DaoException;
	
	public Boolean updateAmount(Integer listId, Product product, Integer newAmount) throws DaoException;
	
	public Boolean updateAmount(Integer listId, PublicProduct product) throws DaoException;
	
	public Boolean updateAmount(Integer listId, Product product) throws DaoException;
	
	public Boolean deleteFromList(Integer listId, PublicProduct product) throws DaoException;
	
	public Boolean deleteFromList(Integer listId, Product product) throws DaoException;
	
	public Boolean hasAddDeletePermission(Integer listId, Integer userId) throws DaoException;
	
	public Boolean hasModifyPermission(Integer listId, Integer userId) throws DaoException;
	
	public Boolean hasDeletePermission(Integer listId, Integer userId) throws DaoException;
	
	public Boolean hasViewPermission(Integer listId, Integer userId) throws DaoException;
	
	public java.util.List<List> getByUser(Integer userId) throws DaoException;
	
	/**
	 * Returns a list of the users (aka their IDs) that have joined the shared list, including its owner
	 * @param listId Id of the list to be inspected
	 * @return A list of user ids for a given list
	 * @throws DaoException 
	 */
	public java.util.List<Integer> getConnectedUsers(Integer listId) throws DaoException;
}
