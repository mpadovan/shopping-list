/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import java.util.ArrayList;
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
}
