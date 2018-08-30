package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.UpdateException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.io.File;
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
public class PublicProductDAOImpl extends AbstractDAO implements PublicProductDAO{
	
	public PublicProductDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}
	
	/**
	 *	ATTENZIONE: non restituisce il logo e la categoria padre delle categorie dei prodotti pubblici
	 */
	@Override
	public List<PublicProduct> getAll() throws DaoException {
		List<PublicProduct> list = new ArrayList<>();
		try{
			String query =	"SELECT pp.id,pp.name,pp.note,pp.logo,pp.photography," +
					"pp.idproductscategory,pc.name,pc.description " +
					"FROM publicproducts pp " +
					"INNER JOIN productscategories pc on pp.idproductscategory = pc.id";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			PublicProduct p;
			ProductsCategory pc;
			int i;
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
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				//pc.setCategory(rs.getInt(i++));
				pc.setDescription(rs.getString(i++));
				//pc.setLogo(rs.getString(i++));
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
	 *	ATTENZIONE: non restituisce il logo e la categoria padre delle categorie dei prodotti pubblici
	 */
	@Override
	public List<PublicProduct> getFromQuery(String matching) throws DaoException {
		List<PublicProduct> list = new ArrayList<>();
		try{
			String query =	"SELECT pp.id,pp.name,pp.note,pp.logo,pp.photography," +
					"pp.idproductscategory,pc.name,pc.description " +
					"FROM publicproducts pp " +
					"INNER JOIN productscategories pc on pp.idproductscategory = pc.id "+
					"WHERE pp.name LIKE \"%"+matching+"%\"";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			PublicProduct p;
			ProductsCategory pc;
			int i;
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
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				//pc.setCategory(rs.getInt(i++));
				pc.setDescription(rs.getString(i++));
				//pc.setLogo(rs.getString(i++));
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
	 *	ATTENZIONE: non restituisce il logo e la categoria padre della categoria del prodotto pubblico
	 */
	@Override
	public PublicProduct getById(Integer id) throws DaoException {
		try{
			PublicProduct p = new PublicProduct();
			String query = "SELECT pp.name,pp.note,pp.logo,pp.photography," +
					"pp.idproductscategory,pc.name,pc.description " +
					"FROM publicproducts pp " +
					"INNER JOIN productscategories pc on pp.idproductscategory = pc.id " +
					"WHERE pp.id = "+id;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			ProductsCategory pc = new ProductsCategory();
			if(rs.first())
			{
				int i = 1;
				p.setId(id);
				p.setName(rs.getString(i++));
				p.setNote(rs.getString(i++));
				p.setLogo(rs.getString(i++));
				p.setPhotography(rs.getString(i++));
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				//pc.setCategory(rs.getInt(i++));
				pc.setDescription(rs.getString(i++));
				//pc.setLogo(rs.getString(i++));
				p.setCategory(pc);
				
				rs.close();
				st.close();
				return p;
			}
			throw new RecordNotFoundDaoException("Public product with id: " + id + " not found");
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean updateProduct(Integer id, PublicProduct product) throws DaoException {
		Boolean valid = product.isVaildOnUpdate(dAOFactory);
		if(valid)
		{
			try{
				String logo = product.getLogo();
				String photo = product.getPhotography();
				if(File.separator.equals("\\")){
					logo = logo.replaceAll("\\\\", "\\\\\\\\");
					photo = photo.replaceAll("\\\\", "\\\\\\\\");
				}
				String query = "UPDATE publicproducts " +
						"SET name = \""+product.getName()+"\"," +
						"note = \""+product.getNote()+"\"," +
						"logo = \""+logo+"\"," +
						"photography = \""+photo+"\"," +
						"idproductscategory = "+product.getCategory().getId()+
						" WHERE id = "+id;
				PreparedStatement st = this.getCon().prepareStatement(query);
				int count = st.executeUpdate();
				st.close();
				if(count != 1)
					throw new RecordNotFoundDaoException("Public product: "+id+" not found");
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
	public Boolean addProduct(PublicProduct product) throws DaoException {
		Boolean valid = true; // product.isVaildOnCreate(dAOFactory);
		if(valid)
		{
			try{
				String logo = product.getLogo();
				String photo = product.getPhotography();
				if(File.separator.equals("\\")){
					logo = logo.replaceAll("\\\\", "\\\\\\\\");
					photo = photo.replaceAll("\\\\", "\\\\\\\\");
				}
				String query = "INSERT INTO publicproducts (name,note,logo,photography,idproductscategory) VALUES (\""+
						product.getName()+"\",\""+
						product.getNote()+"\",\""+
						logo+"\",\""+
						photo+"\","+
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
	 *	ATTENZIONE: mancanza validation, funzione da rivedere
	 */
	@Override
	public Boolean deleteProduct(Integer id) throws DaoException {
		//Boolean valid = product.isVaildOnDestroy(dAOFactory);
		//if(valid)
		//{
		try{
			String query = "DELETE FROM publicproducts WHERE id = "+id;
			PreparedStatement st = this.getCon().prepareStatement(query);
			int count = st.executeUpdate();
			st.close();
			if(count != 1)
				throw new RecordNotFoundDaoException("product "+id+" not found");
			return true;
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new UpdateException(ex);
		}
		//}
		//return valid;
	}
	
	/**
	 *	ATTENZIONE: non restituisce il logo e la categoria padre della categoria del prodotto pubblico
	 */
	@Override
	public PublicProduct getByName(String name) throws DaoException {
		try{
			PublicProduct p = new PublicProduct();
			String query = "SELECT pp.id,pp.note,pp.logo,pp.photography," +
					"pp.idproductscategory,pc.name,pc.description " +
					"FROM publicproducts pp " +
					"INNER JOIN productscategories pc on pp.idproductscategory = pc.id " +
					"WHERE pp.name = \""+name+"\"";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			ProductsCategory pc = new ProductsCategory();
			if(rs.first())
			{
				int i = 1;
				p.setId(i++);
				p.setName(name);
				p.setNote(rs.getString(i++));
				p.setLogo(rs.getString(i++));
				p.setPhotography(rs.getString(i++));
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				//pc.setCategory(rs.getInt(i++));
				pc.setDescription(rs.getString(i++));
				//pc.setLogo(rs.getString(i++));
				p.setCategory(pc);
				
				rs.close();
				st.close();
				return p;
			}
			throw new RecordNotFoundDaoException("Public product with name: " + name + " not found");
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
}
