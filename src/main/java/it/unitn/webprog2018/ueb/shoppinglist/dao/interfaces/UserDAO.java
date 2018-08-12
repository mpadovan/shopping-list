/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.entities.User;

/**
 * Interface for the UserDAOImpl object
 *
 * @author Giulia Carocari
 */
public interface UserDAO {
	
	User getById(Integer id);
	
	User getByEmail(String email);
	
	void addUser(User user);
	
	// void updateUser(Integer id, User user);
}
