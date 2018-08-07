/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author giulia
 */
public class UserDAOimpl implements UserDAO {
	private List<User> users;

	public UserDAOimpl() {
		this.users = new LinkedList<>();
	}
	
	@Override
	public User getByEmail(String email) {
		for(User u : users) {
			if (u.getEmail().equals("email")) {
				return u;
			}
		}
		return null;
	}
	
	@Override
	public void addUser(User user) {
		users.add(user);
	}
	
}
