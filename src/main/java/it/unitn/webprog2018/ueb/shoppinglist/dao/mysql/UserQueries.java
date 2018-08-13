package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.sql.Connection;
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

			user.setId(id);
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setLastname(rs.getString("lastname"));
			user.setImage(rs.getString("image"));
			user.setAdministrator(rs.getInt("administrator") == 0 ? false : true);
			
			//System.out.format("%s, %s, %s, %s, %s, %s %s\n", user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getLastname(), user.getImage(), user.isAdministrator());
			
			st.close();
			return user;
		}
		catch(SQLException | UnsupportedOperationException e){
			return null;
		}
	}
	
	@Override
	public User getByEmail(String email) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public void addUser(User user) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
