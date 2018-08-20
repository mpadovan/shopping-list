package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.*;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
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
 * @author Michele
 */
public class ProductDAOImpl extends AbstractDAO implements ProductDAO{
	private DAOFactory dAOFactory;
	
	public ProductDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}
	
	/**
	 * aggiunge un prodotto al DB
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
	 */
	@Override
	public List<Product> getByUser(Integer userId) throws DaoException {
		List<Product> list = new ArrayList<>();
		try{
			String query =	"SELECT p.id,p.name,p.note,p.logo,p.photography,p.idproductscategory," +
					"pc.name,pc.category,pc.description,pc.logo " +
					"FROM products p inner join productscategories pc on p.idproductscategory = pc.id " +
					"WHERE p.iduser = "+userId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Product p;
			ProductsCategory pc;
			int i;
			while(rs.next())
			{
				i = 1;
				p = new Product();
				pc = new ProductsCategory();
				p.setId(rs.getInt(i++));
				p.setName(rs.getString(i++));
				p.setNote(rs.getString(i++));
				p.setLogo(rs.getString(i++));
				p.setPhotography(rs.getString(i++));
				p.setOwner(null);
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				pc.setCategory(rs.getInt(i++));
				pc.setDescription(rs.getString(i++));
				pc.setLogo(rs.getString(i++));
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
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("product: "+productId+" not found");
				return valid;
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
	/**
	 * ATTENZIONE: gli owner dei prodotti restituiti sono null per alleggerire la Query (dato che il chiamante conosce giá l'utente)
	 */
	@Override
	public List<Product> getByUser(Integer userId, String matching) throws DaoException {
		List<Product> list = new ArrayList<>();
		try{
			String query =	"SELECT p.id,p.name,p.note,p.logo,p.photography,p.idproductscategory," +
					"pc.name,pc.category,pc.description,pc.logo " +
					"FROM products p inner join productscategories pc on p.idproductscategory = pc.id " +
					"WHERE p.iduser = "+userId+" AND p.name LIKE \"%"+matching+"%\"";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Product p;
			ProductsCategory pc;
			int i;
			while(rs.next())
			{
				i = 1;
				p = new Product();
				pc = new ProductsCategory();
				p.setId(rs.getInt(i++));
				p.setName(rs.getString(i++));
				p.setNote(rs.getString(i++));
				p.setLogo(rs.getString(i++));
				p.setPhotography(rs.getString(i++));
				p.setOwner(null);
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				pc.setCategory(rs.getInt(i++));
				pc.setDescription(rs.getString(i++));
				pc.setLogo(rs.getString(i++));
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
	/**
	 * ATTENZIONE: non da e-mail, password e image degli owner
	 */
	@Override
	public Product getProduct(Integer productId) throws DaoException {
		try{
			Product p = new Product();
			String query = "SELECT 	p.name,p.note,p.logo,p.photography," +
					"p.iduser,u.name,u.lastname,u.administrator," +
					"p.idproductscategory,pc.name,pc.category,pc.description,pc.logo " +
					"FROM products p " +
					"INNER JOIN users u ON p.iduser = u.id " +
					"INNER JOIN productscategories pc ON p.idproductscategory = pc.id " +
					"WHERE p.id = "+productId;
					Statement st = this.getCon().createStatement();
					ResultSet rs = st.executeQuery(query);
					User u = new User();
					ProductsCategory pc = new ProductsCategory();
					if(rs.first())
					{
						int i = 1;
						p.setId(productId);
						p.setName(rs.getString(i++));
						p.setNote(rs.getString(i++));
						p.setLogo(rs.getString(i++));
						p.setPhotography(rs.getString(i++));
						u.setId(rs.getInt(i++));
						u.setName(rs.getString(i++));
						u.setLastname(rs.getString(i++));
						u.setAdministrator(rs.getInt(i++) != 0);
						pc.setId(rs.getInt(i++));
						pc.setName(rs.getString(i++));
						pc.setCategory(rs.getInt(i++));
						pc.setDescription(rs.getString(i++));
						pc.setLogo(rs.getString(i++));
						p.setOwner(u);
						p.setCategory(pc);
						
						rs.close();
						st.close();
						return p;
					}
					throw new RecordNotFoundDaoException("Product with id: " + productId + " not found");
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
}
