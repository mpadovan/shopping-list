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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Michele
 */
public class ListDAOImpl extends AbstractDAO implements ListDAO{
	private DAOFactory dAOFactory;
	
	public ListDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}
	/**
	 * ATTENZIONE: non da e-mail, password e image degli owner
	 */
	@Override
	public List getList(Integer id) throws DaoException {
		try{
			List list = new List();
			String query = "SELECT l.name,l.iduser,l.idcategory,l.description,l.image," +
					"u.name,u.lastname,u.administrator," +
					"lc.name,lc.description " +
					"FROM lists l " +
					"INNER JOIN users u ON l.iduser = u.id " +
					"INNER JOIN listscategories lc ON l.idcategory = lc.id " +
					"WHERE l.id = "+id;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			int i = 1;
			if(rs.first())
			{
				User user = new User();
				ListsCategory lc = new ListsCategory();
				
				list.setId(id);
				list.setName(rs.getString(i++));
				user.setId(rs.getInt(i++));
				lc.setId(rs.getInt(i++));
				list.setDescription(rs.getString(i++));
				list.setImage(rs.getString(i++));
				user.setName(rs.getString(i++));
				user.setLastname(rs.getString(i++));
				//user.setImage(rs.getString(i++));
				user.setAdministrator(rs.getInt(i++) != 0);
				lc.setName(rs.getString(i++));
				lc.setDescription(rs.getString(i++));
				user.setEmail("");
				user.setPassword("");
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
		try {
			List list = new List();
			String query = "SELECT l.id,l.idcategory,l.description,l.image," +
					"lc.name,lc.description " +
					"FROM lists l " +
					"INNER JOIN listscategories lc ON l.idcategory = lc.id " +
					"WHERE l.name = \""+name+"\" AND l.iduser = "+owner.getId();
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			ListsCategory lc = new ListsCategory();
			int i = 1;
			if(rs.first())
			{
				list.setId(rs.getInt(i++));
				lc.setId(rs.getInt(i++));
				list.setDescription(rs.getString(i++));
				list.setImage(rs.getString(i++));
				lc.setName(rs.getString(i++));
				lc.setDescription(rs.getString(i++));
				list.setName(name);
				list.setCategory(lc);
				list.setOwner(owner);
				
				rs.close();
				st.close();
				return list;
			}
			throw new RecordNotFoundDaoException("list: " + name + " not found");
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
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
			int i = 1;
			while(rs.next())
			{
				i = 1;
				p = new PublicProduct();
				pc = new ProductsCategory();
				p.setId(rs.getInt(i++));
				p.setName(rs.getString(i++));
				p.setNote(rs.getString(i++));
				p.setLogo(rs.getString(i++));
				p.setPhotography(rs.getString(i++));
				quantity = rs.getInt(i++);
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				pc.setCategory(rs.getInt(i++));
				pc.setDescription(rs.getString(i++));
				pc.setLogo(rs.getString(i++));
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
	/**
	 * ATTENZIONE: non da e-mail, password e image degli owner
	 */
	@Override
	public Map<Product, Integer> getProductsOnList(Integer listId) throws DaoException {
		Map<Product, Integer> list = new HashMap<>();
		try{
			String query =	"SELECT p.id,p.name,p.note,p.logo,p.photography,pl.quantity," +
					"p.iduser,u.name,u.lastname,u.administrator," +
					"p.idproductscategory,pc.name,pc.category,pc.description,pc.logo " +
					"FROM productsonlists pl " +
					"INNER JOIN products p ON pl.idproduct = p.id " +
					"INNER JOIN productscategories pc ON pc.id = p.idproductscategory " +
					"INNER JOIN users u ON p.iduser = u.id " +
					"WHERE pl.idlist = "+listId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Product p;
			ProductsCategory pc;
			User u;
			Integer quantity;
			int i = 1;
			while(rs.next())
			{
				i = 1;
				p = new Product();
				pc = new ProductsCategory();
				u = new User();
				p.setId(rs.getInt(i++));
				p.setName(rs.getString(i++));
				p.setNote(rs.getString(i++));
				p.setLogo(rs.getString(i++));
				p.setPhotography(rs.getString(i++));
				quantity = rs.getInt(i++);
				u.setId(rs.getInt(i++));
				u.setName(rs.getString(i++));
				u.setLastname(rs.getString(i++));
				//u.setImage(rs.getString(i++));
				u.setAdministrator(rs.getInt(i++) != 0);
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				pc.setCategory(rs.getInt(i++));
				pc.setDescription(rs.getString(i++));
				pc.setLogo(rs.getString(i++));
				p.setCategory(pc);
				p.setOwner(u);
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
	/**
	 * ATTENZIONE: la quantitá é settata a 1, se volete cambiarla chiamate updateAmount()
	 */
	@Override
	public Boolean addProduct(Integer listId, Product product) throws DaoException {
		Boolean valid = product.isVaildOnCreate(dAOFactory);
		if(valid)
		{
			try{
				String query = "INSERT INTO productsonlists (idlist,idproduct,quantity) VALUES ("+
						listId+","+
						product.getId()+",1)";
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
	 * ATTENZIONE: la quantitá é settata a 1, se volete cambiarla chiamate updateAmount()
	 */
	@Override
	public Boolean addPublicProduct(Integer listId, PublicProduct product) throws DaoException {
		Boolean valid = product.isVaildOnCreate(dAOFactory);
		if(valid)
		{
			try{
				String query = "INSERT INTO publicproductsonlists (idlist,idpublicproduct,quantity) VALUES ("+
						listId+","+
						product.getId()+",1)";
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
	public Boolean isOnList(Integer listId, PublicProduct product) throws DaoException {
		Boolean exist;
		try {
			String query = "SELECT EXISTS(SELECT 1 " +
					"FROM publicproductsonlists " +
					"WHERE idlist = "+listId+" AND idpublicproduct = "+product.getId()+") AS exist";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				exist = rs.getInt(1) == 1;
				
				rs.close();
				st.close();
				return exist;
			}
			throw new RecordNotFoundDaoException("something really strange happened");
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean isOnList(Integer listId, Product product) throws DaoException {
		Boolean exist;
		try {
			String query = "SELECT EXISTS(SELECT 1 " +
					"FROM productsonlists " +
					"WHERE idlist = "+listId+" AND idproduct = "+product.getId()+") AS exist";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				exist = rs.getInt(1) == 1;
				
				rs.close();
				st.close();
				return exist;
			}
			throw new RecordNotFoundDaoException("something really strange happened");
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean updateAmount(Integer listId, PublicProduct product, Integer newAmount) throws DaoException {
		Boolean valid = product.isVaildOnUpdate(dAOFactory);
		if(valid)
		{
			try{
				String query = "UPDATE publicproductsonlists " +
						"SET quantity = "+newAmount+
						" WHERE idlist = " + listId+
						" AND idpublicproduct = " + product.getId();
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("product: "+product.getId()+" not exist in list: "+listId);
				return valid;
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
	
	@Override
	public Boolean updateAmount(Integer listId, Product product, Integer newAmount) throws DaoException {
		Boolean valid = product.isVaildOnUpdate(dAOFactory);
		if(valid)
		{
			try{
				String query = "UPDATE productsonlists " +
						"SET quantity = "+newAmount+
						" WHERE idlist = " + listId+
						" AND idproduct = " + product.getId();
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("product: "+product.getId()+" not exist in list: "+listId);
				return valid;
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
	
	@Override
	public Boolean updateAmount(Integer listId, PublicProduct product) throws DaoException {
		Boolean valid = product.isVaildOnUpdate(dAOFactory);
		if(valid)
		{
			try{
				String query = "UPDATE publicproductsonlists " +
						"SET quantity = quantity + 1"+
						" WHERE idlist = " + listId+
						" AND idpublicproduct = " + product.getId();
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("product: "+product.getId()+" not exist in list: "+listId);
				return valid;
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
	
	@Override
	public Boolean updateAmount(Integer listId, Product product) throws DaoException {
		Boolean valid = product.isVaildOnUpdate(dAOFactory);
		if(valid)
		{
			try{
				String query = "UPDATE productsonlists " +
						"SET quantity = quantity + 1"+
						" WHERE idlist = " + listId+
						" AND idproduct = " + product.getId();
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("product: "+product.getId()+" not exist in list: "+listId);
				return valid;
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
	
	@Override
	public Boolean hasAddDeletePermission(Integer listId, Integer userId) throws DaoException {
		Boolean hasPermission;
		try {
			String query = "SELECT adddelete FROM sharedlists WHERE iduser = "+userId+" AND idlist = "+listId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				hasPermission = rs.getInt(1) == 1;
				
				rs.close();
				st.close();
				return hasPermission;
			}
			throw new RecordNotFoundDaoException("list "+listId+" is not shared by: "+userId);
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean hasModifyPermission(Integer listId, Integer userId) throws DaoException {
		Boolean hasPermission;
		try {
			String query = "SELECT modifylist FROM sharedlists WHERE iduser = "+userId+" AND idlist = "+listId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				hasPermission = rs.getInt(1) == 1;
				
				rs.close();
				st.close();
				return hasPermission;
			}
			throw new RecordNotFoundDaoException("list "+listId+" is not shared by: "+userId);
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean hasDeletePermission(Integer listId, Integer userId) throws DaoException {
		Boolean hasPermission;
		try {
			String query = "SELECT deletelist FROM sharedlists WHERE iduser = "+userId+" AND idlist = "+listId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				hasPermission = rs.getInt(1) == 1;
				
				rs.close();
				st.close();
				return hasPermission;
			}
			throw new RecordNotFoundDaoException("list "+listId+" is not shared by: "+userId);
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	/**
	 * ATTENZIONE: l'owner é null
	 */
	@Override
	public java.util.List<List> getByUser(Integer userId) throws DaoException {
		java.util.List<List> list = new ArrayList<>();
		try{
			String query =	"SELECT l.id,l.name,l.description,l.image," +
					"l.idcategory,lc.name,lc.description " +
					"FROM lists l " +
					"INNER JOIN listscategories lc ON l.idcategory = lc.id " +
					"WHERE iduser = "+userId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			List l;
			ListsCategory lc;
			int i;
			while(rs.next())
			{
				i = 1;
				l = new List();
				lc = new ListsCategory();
				l.setId(rs.getInt(i++));
				l.setName(rs.getString(i++));
				l.setDescription(rs.getString(i++));
				l.setImage(rs.getString(i++));
				lc.setId(rs.getInt(i++));
				lc.setName(rs.getString(i++));
				lc.setDescription(rs.getString(i++));
				l.setCategory(lc);
				l.setOwner(null);
				list.add(l);
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
	public Boolean deleteFromList(Integer listId, PublicProduct product) throws DaoException {
		Boolean valid = product.isVaildOnDestroy(dAOFactory);
		if(valid)
		{
			try{
				String query = "DELETE FROM publicproductsonlists WHERE idlist = "+listId+" AND idproduct = "+product.getId();
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("product "+product.getId()+" not found in list "+listId);
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
	public Boolean deleteFromList(Integer listId, Product product) throws DaoException {
		Boolean valid = product.isVaildOnDestroy(dAOFactory);
		if(valid)
		{
			try{
				String query = "DELETE FROM productsonlists WHERE idlist = "+listId+" AND idproduct = "+product.getId();
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("product "+product.getId()+" not found in list "+listId);
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
	public Boolean hasViewPermission(Integer listId, Integer userId) throws DaoException {
		Boolean hasPermission;
		try {
			String query = "select EXISTS(SELECT 1 FROM sharedlists WHERE idlist = "+listId+" AND iduser = "+userId+") as exist";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.first())
			{
				hasPermission = rs.getInt(1) == 1;
				
				rs.close();
				st.close();
				return hasPermission;
			}
			throw new RecordNotFoundDaoException("something really strange happened");
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
}
