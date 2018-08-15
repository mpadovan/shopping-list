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
		user.setEmail("mariorossi@gmail.com");
		user.setPassword(Sha256.doHash("ciao"));
		user.setName("Mario");
		user.setLastname("Rossi");
		user.setAdministrator(false);
		user.setImage("/uploads/restricted/avatar/445.png");

		users.add(user);
		
		User user2 = new User();
		user2.setEmail("luigibianchi@gmail.com");
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
		users.add(user);
		return true;
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
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
