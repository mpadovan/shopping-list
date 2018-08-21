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
import java.util.logging.Level;
import java.util.logging.Logger;

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
		user.setEmail("mariorossi@gmail.com");
		user.setPassword(Sha256.doHash("ciao"));
		user.setName("Mario");
		user.setLastname("Rossi");
		user.setAdministrator(false);
		user.setTokenpassword(null);
		user.setImage("/uploads/restricted/1/avatar/1.png");

		users.add(user);
		
		User user2 = new User();
		user2.setId(2);
		user2.setEmail("luigibianchi@gmail.com");
		user2.setPassword(Sha256.doHash("ciaone"));
		user2.setName("Luigi");
		user2.setLastname("Bianchi");
		user2.setAdministrator(true);
		user2.setTokenpassword(null);

		users.add(user2);
		
		User user3 = new User();
		user3.setId(127);
		user3.setEmail("zwisl0j5.lsn@20minutemail.it");
		user3.setPassword(Sha256.doHash("c"));
		user3.setName("c");
		user3.setLastname("c");
		user3.setAdministrator(false);
		user3.setTokenpassword(null);

		users.add(user3);
		
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
		throw new RecordNotFoundDaoException("User with id: " + id + " not found");
	}

	@Override
	public Boolean updateUser(Integer id, User user) throws DaoException{
		getById(id);
		update(id, user);
		return true;
	}
	
	private synchronized void update(Integer id, User p) throws DaoException{
		try {
			User found = getById(id);

			found.setId(p.getId());
			found.setEmail(p.getEmail());
			found.setPassword(p.getPassword());
			found.setCheckpassword(p.getCheckpassword());
			found.setTokenpassword(p.getTokenpassword());
			found.setName(p.getName());
			found.setLastname(p.getLastname());
			found.setAdministrator(p.isAdministrator());
			found.setImage(p.getImage());
		} catch (DaoException ex) {
			Logger.getLogger(ProductDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
