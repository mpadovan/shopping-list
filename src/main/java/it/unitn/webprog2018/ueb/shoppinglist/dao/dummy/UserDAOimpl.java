/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
import java.util.LinkedList;
import java.util.List;

/**
 * Dummy implementation of user DAO Persistence is handled during runtime
 * through a <code>List</code>
 *
 * @author Giulia Carocari
 */
public class UserDAOimpl implements UserDAO {

	private static List<User> users;

	public UserDAOimpl() {
		this.users = new LinkedList<>();

		User user = new User();
		user.setId(1);
		user.setEmail("mariorossi@gmail.com");
		user.setPassword(Sha256.doHash("ciao"));
		user.setName("Mario");
		user.setLastname("Rossi");
		user.setAdministrator(false);
		user.setImage("/uploads/restricted/avatar/445.png");

		users.add(user);
	}

	@Override
	public User getByEmail(String email) {
		for (User u : users) {
			if (u.getEmail().equals(email)) {
				return u;
			}
		}
		return null;
	}

	@Override
	public void addUser(User user) {
		users.add(user);
	}

	@Override
	public User getById(Integer id) {
		for (User u : users) {
			if (u.getId().equals(id)) {
				return u;
			}
		}
		return null;
	}

}
