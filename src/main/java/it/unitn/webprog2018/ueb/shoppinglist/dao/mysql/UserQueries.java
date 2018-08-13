package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.UpdateException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Michele Tessari
 */
public class UserQueries extends Query implements UserDAO{
	
	public UserQueries(Connection connection) {
		super(connection);
	}
	
	@Override
	public User getById(Integer id) {
		try{
			User user = new User();
			String query = "SELECT email,password,name,lastname,image,administrator FROM users WHERE id = "+id;
			Statement st = this.connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.first();
			
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
		catch(SQLException | UnsupportedOperationException e){
			return null;
		}
	}
	
	@Override
	public User getByEmail(String email) {
		try{
			User user = new User();
			String query = "SELECT id,password,name,lastname,image,administrator FROM users WHERE email = "+email;
			Statement st = this.connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.first();
			
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
		catch(SQLException | UnsupportedOperationException e){
			return null;
		}
	}
	
	@Override
	public void addUser(User user) {
		try{
			String query = "INSERT INTO users (email,name,lastname,administrator,image,password) VALUES ("+
							user.getEmail()+","+
							user.getName()+","+
							user.getLastname()+","+
							(user.isAdministrator()? 1 : 0)+","+
							user.getImage()+","+
							user.getPassword()+")";
			PreparedStatement st = this.connection.prepareStatement(query);
			int count = st.executeUpdate();
			st.close();
			if(count != 1)
				throw new UpdateException("Bad query: "+query);
		}
		catch(SQLException | UnsupportedOperationException | UpdateException e){
			System.err.println("Error during update\n"+e);
		}	
	}
	
}
