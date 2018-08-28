/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Dummy implementation of user DAO Persistence is handled during runtime
 * through a <code>List</code>
 *
 * @author Giulia Carocari
 */
public class UserDAOimpl implements UserDAO {
	private DAOFactory dAOFactory;
	private static List<User> users;

	public UserDAOimpl(DAOFactory dAOFactory) {
		this.dAOFactory=dAOFactory;
		this.users = new LinkedList<>();

		User user = new User();
		user.setId(1);
		user.setEmail("sdhfudshfh@dadadadada.com");
		user.setPassword(Sha256.doHash("ciao"));
		user.setName("Giulia");
		user.setLastname("Carocari");
		user.setAdministrator(false);
		user.setImage("/uploads/restricted/1/avatar/1.png");

		users.add(user);
		
		User user2 = new User();
		user2.setId(2);
		user2.setEmail("sdcsdsdfsdfsfdsfasdftbryjyhtd@sfmyudtrsdf.com");
		user2.setPassword(Sha256.doHash("ciaone"));
		user2.setName("Luigi");
		user2.setLastname("Bianchi");
		user2.setAdministrator(true);

		users.add(user2);
	}

	@Override
	public User getByEmail(String email) throws DaoException{
		for (User u : users) {
			if (u.getEmail().equals(email)) {
				return u;
			}
		}
		throw new RecordNotFoundDaoException("User with email: " + email + " not found");
	}

	@Override
	public Boolean addUser(User user) throws DaoException{
		
		Random rand = new Random();
		user.setId(rand.nextInt());
		boolean valid = user.isVaildOnCreate(dAOFactory);
		if(valid)
		{
			users.add(user);
		}
		return valid;
	}

	@Override
	public User getById(Integer id) throws DaoException{
		for (User u : users) {
			if (u.getId().equals(id)) {
				return u;
			}
		}
		throw new RecordNotFoundDaoException("User with email: " + id + " not found");
	}

	@Override
	public Boolean updateUser(Integer id, User user) throws DaoException{
		if(user.isVaildOnUpdate(dAOFactory))
		{
			for (User p : users) {
				if (p.getId().equals(user.getId())) {
					p = user;
					return true;
				}
			}
			throw new RecordNotFoundDaoException("The user with id: " + user.getId() + " does not exist");
		}
		return false;
	}

}
