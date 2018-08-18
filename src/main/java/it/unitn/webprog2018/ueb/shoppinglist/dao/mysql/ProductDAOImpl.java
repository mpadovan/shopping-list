/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.*;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
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
public class ProductDAOImpl extends AbstractDAO implements ProductDAO{
	private DAOFactory dAOFactory;
	
	public ProductDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}
	
	/**
	 * aggiunge un prodotto al DB
	 * @param product
	 * @return true se l'operazione andata a buon fine, false altrimenti
	 * @throws DaoException
	 */
	@Override
	public Boolean addProduct(Product product) throws DaoException {
		Boolean valid = product.isVaildOnCreate(dAOFactory);
		if(valid)
		{
			try{
				String query = "INSERT INTO products (name,note,logo,photography,iduser,idproductscategory) VALUES (\""+
						product.getName()+"\",\""+
						product.getNote()+"\",\""+
						product.getLogo()+"\",\""+
						product.getPhotography()+"\","+
						product.getOwner().getId()+","+
						product.getCategory().getId()+")";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.executeUpdate();
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
	
	/**
	 * ATTENZIONE: gli owner dei prodotti restituiti sono null per alleggerire la Query (dato che il chiamante conosce giá l'utente)
	 * @param product
	 * @return lista dei prodotti privati di un utente
	 * @throws DaoException
	 */
	@Override
	public List<Product> getByUser(Integer userId) throws DaoException {
		List<Product> list = new ArrayList<Product>();
		try{
			String query =	"SELECT p.id,p.name,p.note,p.logo,p.photography,p.idproductscategory," +
					"pc.name,pc.category,pc.description,pc.logo " +
					"FROM products p inner join productscategories pc on p.idproductscategory = pc.id " +
					"WHERE p.iduser = "+userId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Product p;
			ProductsCategory pc;
			while(rs.next())
			{
				p = new Product();
				pc = new ProductsCategory();
				p.setId(rs.getInt(0));
				p.setName(rs.getString(1));
				p.setNote(rs.getString(2));
				p.setLogo(rs.getString(3));
				p.setPhotography(rs.getString(4));
				p.setOwner(null);
				pc.setId(rs.getInt(5));
				pc.setName(rs.getString(6));
				pc.setCategory(rs.getInt(7));
				pc.setDescription(rs.getString(8));
				pc.setLogo(rs.getString(9));
				p.setCategory(pc);
				list.add(p);
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
	public Boolean updateProduct(Integer productId, Product product) throws DaoException {
		Boolean valid = product.isVaildOnUpdate(dAOFactory);
		if(valid)
		{
			try{
				String query = "UPDATE products\n" +
						"SET name = \""+product.getName()+"\"," +
						"	note = \""+product.getNote()+"\"," +
						"   logo = \""+product.getLogo()+"\"," +
						"   photography = \""+product.getPhotography()+"\"," +
						"   iduser = "+product.getOwner().getId()+"," +
						"   idproductscategory = "+product.getCategory().getId()+
						"WHERE id = "+productId;
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.executeUpdate();
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
	public List<Product> getByUser(Integer userId, String matching) throws DaoException {
		List<Product> list = new ArrayList<Product>();
		try{
			String query =	"SELECT p.id,p.name,p.note,p.logo,p.photography,p.idproductscategory," +
					"pc.name,pc.category,pc.description,pc.logo " +
					"FROM products p inner join productscategories pc on p.idproductscategory = pc.id " +
					"WHERE p.iduser = "+userId+" AND p.name LIKE \"%"+matching+"%\"";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Product p;
			ProductsCategory pc;
			while(rs.next())
			{
				p = new Product();
				pc = new ProductsCategory();
				p.setId(rs.getInt(0));
				p.setName(rs.getString(1));
				p.setNote(rs.getString(2));
				p.setLogo(rs.getString(3));
				p.setPhotography(rs.getString(4));
				p.setOwner(null);
				pc.setId(rs.getInt(5));
				pc.setName(rs.getString(6));
				pc.setCategory(rs.getInt(7));
				pc.setDescription(rs.getString(8));
				pc.setLogo(rs.getString(9));
				p.setCategory(pc);
				list.add(p);
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
	
	
	
}
