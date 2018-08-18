/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.*;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michele
 */
public class ListDAOImpl extends AbstractDAO implements ListDAO{
	private DAOFactory dAOFactory;
	
	public ListDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}
	
	@Override
	public List getList(Integer id) throws DaoException {
		try{
			List list = new List();
			String query = "SELECT 	l.name,l.iduser,l.idcategory,l.description,l.image," +
					"		u.email,u.password,u.name,u.lastname,u.image,u.administrator," +
					"        lc.name,lc.description" +
					"FROM lists l " +
					"INNER JOIN users u ON l.iduser = u.id " +
					"INNER JOIN listscategories lc ON l.idcategory = lc.id " +
					"WHERE l.id = "+id;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				User user = new User();
				ListsCategory lc = new ListsCategory();
				
				list.setId(id);
				list.setName(rs.getString(1));
				user.setId(rs.getInt(2));
				lc.setId(rs.getInt(3));
				list.setDescription(rs.getString(4));
				list.setImage(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setPassword(rs.getString(7));
				user.setName(rs.getString(8));
				user.setLastname(rs.getString(9));
				user.setImage(rs.getString(10));
				user.setAdministrator(rs.getInt(11) != 0);
				lc.setName(rs.getString(12));
				lc.setDescription(rs.getString(13));
				list.setOwner(user);
				list.setCategory(lc);
				
				rs.close();
				st.close();
				return list;
			}
			throw new RecordNotFoundDaoException("User with id: " + id + " not found");
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public List getList(String name, User owner) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Map<PublicProduct, Integer> getPublicProductsOnList(Integer listId) throws DaoException {
		Map<PublicProduct, Integer> list = new HashMap<>();
		try{
			String query =	"SELECT pp.id,pp.name,pp.note,pp.logo,pp.photography,ppl.quantity,pp.idproductscategory," +
					"pc.name,pc.category,pc.description,pc.logo " +
					"FROM publicproductsonlists ppl " +
					"INNER JOIN publicproducts pp ON ppl.idpublicproduct = pp.id " +
					"INNER JOIN productscategories pc ON pc.id = pp.idproductscategory " +
					"WHERE ppl.idlist = "+listId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			PublicProduct p;
			ProductsCategory pc;
			Integer quantity;
			while(rs.next())
			{
				p = new PublicProduct();
				pc = new ProductsCategory();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setNote(rs.getString(3));
				p.setLogo(rs.getString(4));
				p.setPhotography(rs.getString(5));
				quantity = rs.getInt(6);
				pc.setId(rs.getInt(7));
				pc.setName(rs.getString(8));
				pc.setCategory(rs.getInt(9));
				pc.setDescription(rs.getString(10));
				pc.setLogo(rs.getString(11));
				p.setCategory(pc);
				list.put(p, quantity);
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
	public Map<Product, Integer> getProductsOnList(Integer listId) throws DaoException {
		/*Map<Product, Integer> list = new HashMap<>();
		try{
			String query =	"SELECT pp.id,pp.name,pp.note,pp.logo,pp.photography,ppl.quantity,pp.idproductscategory," +
					"pc.name,pc.category,pc.description,pc.logo " +
					"FROM publicproductsonlists ppl " +
					"INNER JOIN publicproducts pp ON ppl.idpublicproduct = pp.id " +
					"INNER JOIN productscategories pc ON pc.id = pp.idproductscategory " +
					"WHERE ppl.idlist = "+listId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Product p;
			ProductsCategory pc;
			Integer quantity;
			while(rs.next())
			{
				p = new Product();
				pc = new ProductsCategory();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setNote(rs.getString(3));
				p.setLogo(rs.getString(4));
				p.setPhotography(rs.getString(5));
				quantity = rs.getInt(6);
				pc.setId(rs.getInt(7));
				pc.setName(rs.getString(8));
				pc.setCategory(rs.getInt(9));
				pc.setDescription(rs.getString(10));
				pc.setLogo(rs.getString(11));
				p.setCategory(pc);
				list.put(p, quantity);
			}
			rs.close();
			st.close();
			return list;
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}*/
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean addProduct(Integer listId, Product product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean addPublicProduct(Integer listId, PublicProduct product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean isOnList(Integer listId, PublicProduct product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean isOnList(Integer listId, Product product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean updateAmount(Integer listId, PublicProduct product, Integer newAmount) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean updateAmount(Integer listId, Product product, Integer newAmount) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean updateAmount(Integer listId, PublicProduct product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean updateAmount(Integer listId, Product product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean hasAddDeletePermission(Integer listId, Integer userId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean hasModifyPermission(Integer listId, Integer userId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public Boolean hasDeletePermission(Integer listId, Integer userId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	@Override
	public java.util.List<List> getByUser(Integer userId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean deleteFromList(Integer listId, PublicProduct product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean deleteFromList(Integer listId, Product product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean hasViewPermission(Integer listId, Integer userId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
