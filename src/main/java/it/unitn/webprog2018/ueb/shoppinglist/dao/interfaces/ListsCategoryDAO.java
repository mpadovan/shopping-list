/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import java.util.List;

/**
 *
 * @author giulia
 */
public interface ListsCategoryDAO {
	public List<ListsCategory> getFromQuery(String query) throws DaoException;
	
	public Boolean addListCategory(ListsCategory listCategory) throws DaoException;
	
	List<ListsCategory> getAll() throws DaoException;
	
	public ListsCategory getByName(String name) throws DaoException;
	
	public ListsCategory getById(Integer id) throws DaoException ;
	
	public Boolean deleteListsCategory(Integer id) throws DaoException; 
	
	public Boolean updateListsCategory(Integer categoryId, ListsCategory listsCategorys) throws DaoException;
	
	// NOTE TO TEX: l'idea è che ci sono 2 tabelle che matchano un token con una categoria di lista
	// e un token con i prodotti nella lista anonima. Prova a valutare tu se ti sembra più conveniente
	// tenere i token in una tabella e poi matcharli tramite fk o fare i match alla cieca.
	/**
	 * Associates a list category to an anonymous list identified by the token string.
	 * @param token
	 * @param listsCategory
	 * @return true if the match was successful
	 */
	public Boolean setListCategory(String token, ListsCategory listsCategory) throws DaoException;
	
	/**
	 * Returns the list category associated with an anonymous list
	 * @param token	List identifier
	 * @return The list category
	 */
	public ListsCategory getListCategory(String token) throws DaoException;
}
