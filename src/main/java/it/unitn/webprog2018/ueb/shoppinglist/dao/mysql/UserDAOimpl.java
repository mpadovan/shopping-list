package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.UpdateException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.io.File;
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
	
	public UserDAOimpl(Connection connection, DAOFactory dAOFactory) {
		super(connection, dAOFactory);
	}
	
	@Override
	public User getById(Integer id) throws DaoException{
		try{
			User user = new User();
			String query = "SELECT email,password,name,lastname,image,administrator FROM users WHERE id = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if(rs.first())
			{
				user.setId(id);
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setLastname(rs.getString("lastname"));
				user.setImage(rs.getString("image"));
				user.setAdministrator(rs.getInt("administrator") != 0);

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
			String query = "SELECT id,password,name,lastname,image,administrator FROM users WHERE email = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			if(rs.first())
			{
				user.setId(rs.getInt("id"));
				user.setEmail(email);
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setLastname(rs.getString("lastname"));
				user.setImage(rs.getString("image"));
				user.setAdministrator(rs.getInt("administrator") != 0);

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
				String image = user.getImage();
				if(File.separator.equals("\\") && image != null)
					image = image.replaceAll("\\\\", "\\\\\\\\");
				String query = "INSERT INTO users (email,name,lastname,administrator,image,password) VALUES (?,?,?,?,?,?)";
				PreparedStatement st = this.getCon().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				st.setString(1, user.getEmail());
				st.setString(2, user.getName());
				st.setString(3, user.getLastname());
				st.setInt(4, user.isAdministrator()? 1 : 0);
				st.setString(5, image);
				st.setString(6, user.getPassword());
				st.executeUpdate();
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next())
					user.setId(rs.getInt(1));
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
				String image = user.getImage();
				if(File.separator.equals("\\") && image != null)
					image = image.replaceAll("\\\\", "\\\\\\\\");
				
				String query = "UPDATE users SET email = ?, password = ?, name = ?, lastname = ?, image = ?, administrator = ? WHERE id = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setString(1, user.getEmail());
				st.setString(2, user.getPassword());
				st.setString(3, user.getName());
				st.setString(4, user.getLastname());
				st.setString(5, image);
				st.setInt(6, user.isAdministrator()? 1 : 0);
				st.setInt(7, id);
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("user: "+id+" not found");
				return valid;
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
}
