/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.UpdateException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michele
 */
public class ListsCategoryDAOImpl extends AbstractDAO implements ListsCategoryDAO{
	private DAOFactory dAOFactory;
	
	public ListsCategoryDAOImpl(Connection connection, DAOFactory dAOFactory) {
		super(connection, dAOFactory);
	}
	
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
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean addListCategory(ListsCategory lc) throws DaoException {
		Boolean valid = lc.isVaildOnCreate(dAOFactory);
		if(valid){
			try{
				String query = "INSERT INTO listscategories (name,description) VALUES (\""+
						lc.getName()+ "\",\"" +
						lc.getDescription()+ "\")";
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				return valid && (count == 1);
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
	
	@Override
	public List<ListsCategory> getAll() throws DaoException{
		List<ListsCategory> list = new ArrayList<>();
		try{
			String query =	"SELECT id,name,description " +
					"FROM listscategories";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			ListsCategory lc;
			int i;
			while(rs.next())
			{
				i = 1;
				lc = new ListsCategory();
				lc.setId(rs.getInt(i++));
				lc.setName(rs.getString(i++));
				lc.setDescription(rs.getString(i++));
				
				list.add(lc);
			}
			rs.close();
			st.close();
			return list;
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public ListsCategory getByName(String name) throws DaoException {
		try{
			ListsCategory lc = new ListsCategory();
			String query = "SELECT id,description " +
					"FROM listscategories " +
					"WHERE name = "+name;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				int i = 1;
				lc.setId(rs.getInt(i++));
				lc.setName(name);
				lc.setDescription(rs.getString(i++));
				
				rs.close();
				st.close();
				return lc;
			}
			throw new RecordNotFoundDaoException("List Category with name: " + name + " not found");
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public ListsCategory getById(Integer id) throws DaoException {
		try{
			ListsCategory lc = new ListsCategory();
			String query = "select name,description from listscategories where id = "+id;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				int i = 1;
				lc.setId(id);
				lc.setName(rs.getString(i++));
				lc.setDescription(rs.getString(i++));
				
				rs.close();
				st.close();
				return lc;
			}
			throw new RecordNotFoundDaoException("list category with id: " + id + " not found");
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean deleteListsCategory(Integer id) throws DaoException {
		try{
			String query = "call deleteListsCategory("+id+");";
			PreparedStatement st = this.getCon().prepareStatement(query);
			int count = st.executeUpdate();
			st.close();
			if(count < 1)
				throw new RecordNotFoundDaoException("product category "+id+" not found ");
			return true;
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new UpdateException(ex);
		}
	}
	
	@Override
	public Boolean updateListsCategory(Integer categoryId, ListsCategory listsCategory) throws DaoException {
		Boolean valid = listsCategory.isVaildOnUpdate(dAOFactory);
		if(valid)
		{
			try{
				String query = "update listscategories\n" +
						"set name = \""+listsCategory.getName()+"\",\n" +
						"	description = \""+listsCategory.getDescription()+"\"\n" +
						"where id = " + categoryId;
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("list category "+categoryId+" not found");
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
