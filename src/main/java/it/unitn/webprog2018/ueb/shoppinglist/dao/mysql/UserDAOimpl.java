package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.UpdateException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michele Tessari
 */
public class UserDAOimpl extends AbstractDAO implements UserDAO{
	private DAOFactory dAOFactory;
	
	public UserDAOimpl(Connection connection, DAOFactory dAOFactory) {
		super(connection, dAOFactory);
	}
	
	@Override
	public User getById(Integer id) throws DaoException{
		try{
			User user = new User();
			String query = "SELECT email,password,name,lastname,image,administrator FROM users WHERE id = "+id;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				user.setId(id);
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setLastname(rs.getString("lastname"));
				user.setImage(rs.getString("image"));
				user.setAdministrator(rs.getInt("administrator") == 0 ? false : true);

				rs.close();
				st.close();
				return user;
			}
			throw new RecordNotFoundDaoException("User with id: " + id + " not found");
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public User getByEmail(String email) throws DaoException{
		try {
			User user = new User();
			String query = "SELECT id,password,name,lastname,image,administrator FROM users WHERE email = "+email;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				user.setId(rs.getInt("id"));
				user.setEmail(email);
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setLastname(rs.getString("lastname"));
				user.setImage(rs.getString("image"));
				user.setAdministrator(rs.getInt("administrator") == 0 ? false : true);

				rs.close();
				st.close();
				return user;
			}
			throw new RecordNotFoundDaoException("User with email: " + email + " not found");
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean addUser(User user) throws DaoException{
		Boolean valid = user.isVaildOnCreate(dAOFactory);
		if(valid)
		{
			try{
				String query = "INSERT INTO users (email,name,lastname,administrator,image,password) VALUES (\""+
						user.getEmail()+"\",\""+
						user.getName()+"\",\""+
						user.getLastname()+"\","+
						(user.isAdministrator()? 1 : 0)+",\""+
						user.getImage()+"\",\""+
						user.getPassword()+"\")";
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				return valid;
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new UpdateException(ex);
			}
		}
		return valid;
	}
	
	@Override
	public Boolean updateUser(Integer id, User user) throws DaoException{
		Boolean valid = user.isVaildOnUpdate(dAOFactory);
		if(valid)
		{
			try{
				String query = "UPDATE users " +
						"SET email = \"" + user.getEmail() +
						"\", password = \"" + user.getPassword() +
						"\", name = \"" + user.getName() +
						"\", lastname = \"" + user.getLastname() +
						"\", image = \"" + user.getImage() +
						"\", administrator = " + (user.isAdministrator()? 1 : 0) +
						" WHERE id = " + id;
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				return valid;
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new UpdateException(ex);
			}
		}
		return valid;
	}
}
