/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;

/**
 * Interface for the UserDAOImpl object
 *
 * @author Giulia Carocari
 */
public interface UserDAO {
	
	User getById(Integer id) throws DaoException;
	
	User getByEmail(String email) throws DaoException;
	
	Boolean addUser(User user) throws DaoException;
	
	Boolean updateUser(Integer id, User user) throws DaoException;
}
