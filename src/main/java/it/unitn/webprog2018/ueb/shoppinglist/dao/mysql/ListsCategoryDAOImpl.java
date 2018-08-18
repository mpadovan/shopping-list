/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategoriesImage;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michele
 */
public class ListsCategoryDAOImpl extends AbstractDAO implements ListsCategoryDAO{
	private DAOFactory dAOFactory;
	
	public ListsCategoryDAOImpl(Connection connection, DAOFactory dAOFactory) {
		super(connection, dAOFactory);
	}
	
	/**
	 * @param matching
	 * @return un'ArrayList con tutte le categorie di liste con nomi conteneti quella parola, lancia una DaoException per qualsiasi errore riscontrato
	 * @throws DaoException 
	 */
	
	@Override
	public List<ListsCategory> getFromQuery(String matching) throws DaoException {
		List<ListsCategory> list = new ArrayList<ListsCategory>();
		try{
			String query = "SELECT id,name,description FROM listscategories"
					+ "	WHERE name LIKE \"%"+matching+"%\"";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			ListsCategory pc;
			
			while (rs.next())
			{
				pc = new ListsCategory();
				pc.setId(rs.getInt("id"));
				pc.setName(rs.getString("name"));
				pc.setDescription(rs.getString("description"));
				list.add(pc);
			}
			
			st.close();
			rs.close();
			
			return list;
		}
		catch(SQLException e){
			throw new DaoException(e);
		}
	}
	
	@Override
	public Boolean addListCategory(ListsCategory lc) throws DaoException {
		try{
			String query = "INSERT INTO listscategories (name,description) VALUES (\""+
					lc.getName()+ "\",\"" +
					lc.getDescription()+ "\")";
			PreparedStatement st = this.getCon().prepareStatement(query);
			int count = st.executeUpdate();
			st.close();
			return (count == 1);
		}
		catch(SQLException e){
			throw new DaoException(e);
		}	
	}

	@Override
	public List<ListsCategory> getAll() throws DaoException{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public ListsCategory getByName(String name) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public ListsCategory getById(Integer id) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean deleteListsCategory(Integer id) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean updateListsCategory(Integer categoryId, ListsCategory listsCategory) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
