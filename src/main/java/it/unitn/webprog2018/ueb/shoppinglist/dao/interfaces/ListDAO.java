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
	public java.util.List<Integer> getConnectedUsersIds(Integer listId) throws DaoException;
	
	/**
	 * Returns a list of the users (aka their IDs) that have joined the shared list, including its owner
	 * @param listId Id of the list to be inspected
	 * @return A list of user ids for a given list
	 * @throws DaoException 
	 */
	public java.util.List<User> getConnectedUsers(Integer listId) throws DaoException;

	public java.util.List<List> getPersonalLists(Integer id) throws DaoException;
	
	public Boolean linkShoppingListToUser(List list, Integer idpartecipant) throws DaoException;

	public Boolean addList (List list) throws DaoException;

	public java.util.List<List> getSharedLists(Integer id) throws DaoException;

	public Boolean deleteList(Integer listId) throws DaoException;
	
	public Boolean updateList(Integer id, List list) throws DaoException;
	
	/**
	 * Adds a public product to an anonymous list identified by a token setting its amount to 1.
	 * @param token	String token that identifies the list
	 * @param product	Product to be added to the list
	 * @return	true if the list-product connection was successfully persisted
	 * @throws DaoException	if either the list or the product do not exist
	 */
	public Boolean addProduct(String token, PublicProduct product) throws DaoException;
	
	/**
	 * Sets the amount of a public product on an anonymous list identified by a token.
	 * @param token	String token that identifies the list
	 * @param product	Product to be updated
	 * @param newAmount	If the call was successful the new amount of the product on the list will be <code>newAmount</code>
	 * @return	true if the persistence was successful
	 * @throws DaoException If the product is not on the list or the list does not exist
	 */
	public Boolean updateAmount(String token, PublicProduct product, Integer newAmount) throws DaoException;
	
	/**
	 * Increases the amount of a product on an anonymous list by 1.
	 * @param token	String token that identifies the list
	 * @param product	Product to be updated
	 * @return	true if the call was successful
	 * @throws DaoException If the product is not on the list or the list does not exist
	 */
	public Boolean updateAmount(String token, PublicProduct product) throws DaoException;
	
	/**
	 * Deletes a product from an anonimous list.
	 * @param token	String token that identifies the list
	 * @param product	Product to be updated
	 * @return	true if the call was successful
	 * @throws DaoException If the product is not on the list or the list does not exist
	 */
	public Boolean deleteFromList(String token, PublicProduct product) throws DaoException;
}
