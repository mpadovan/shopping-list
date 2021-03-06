package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.*;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
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
 *
 * @author Michele
 */
public class ProductsCategoryDAOImpl extends AbstractDAO implements ProductsCategoryDAO {
	
	public ProductsCategoryDAOImpl(Connection connection, DAOFactory dAOFactory) {
		super(connection, dAOFactory);
	}
	
	@Override
	public List<ProductsCategory> getFromQuery(String matching) throws DaoException {
		List<ProductsCategory> list = new ArrayList<ProductsCategory>();
		try {
			String query = "SELECT id,name,description,logo FROM productscategories WHERE name LIKE ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setString(1, "%" + matching + "%");
			ResultSet rs = st.executeQuery();
			ProductsCategory pc;
			
			while (rs.next()) {
				pc = new ProductsCategory();
				pc.setId(rs.getInt("id"));
				pc.setName(rs.getString("name"));
				pc.setDescription(rs.getString("description"));
				pc.setLogo(rs.getString("logo"));
				list.add(pc);
			}
			
			st.close();
			rs.close();
			
			return list;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean addProductsCategory(ProductsCategory pc) throws DaoException {
		Boolean valid = pc.isVaildOnCreate(dAOFactory);
		if (valid) {
			try {
				String logo = pc.getLogo();
				/*
				if (File.separator.equals("\\") && logo != null) {
					logo = logo.replaceAll("\\\\", "\\\\\\\\");
				}
				*/
				String query = "INSERT INTO productscategories (name,description,logo) VALUES (?,?,?)";
				PreparedStatement st = this.getCon().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				st.setString(1, pc.getName());
				st.setString(2, pc.getDescription());
				st.setString(3, logo);
				st.executeUpdate();
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					pc.setId(rs.getInt(1));
				}
				st.close();
				return valid;
			} catch (SQLException ex) {
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
	
	@Override
	public List<ProductsCategory> getAll() throws DaoException {
		List<ProductsCategory> list = new ArrayList<>();
		try {
			String query = "SELECT id,name,category,description,logo FROM productscategories";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			ProductsCategory pc, pcp;
			int i;
			while (rs.next()) {
				i = 1;
				pc = new ProductsCategory();
				pcp = new ProductsCategory();
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				pcp.setId(rs.getInt(i++));
				pc.setCategory(pcp);
				pc.setDescription(rs.getString(i++));
				pc.setLogo(rs.getString(i++));
				list.add(pc);
			}
			rs.close();
			st.close();
			return list;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public ProductsCategory getById(Integer id) throws DaoException {
		try {
			ProductsCategory pc = new ProductsCategory();
			ProductsCategory pcp = new ProductsCategory();
			String query = "SELECT name,category,description,logo FROM productscategories WHERE id = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
				int i = 1;
				pc.setId(id);
				pc.setName(rs.getString(i++));
				pcp.setId(rs.getInt(i++));
				pc.setCategory(pcp);
				pc.setDescription(rs.getString(i++));
				pc.setLogo(rs.getString(i++));
				
				rs.close();
				st.close();
				return pc;
			}
			throw new RecordNotFoundDaoException("Product category with id: " + id + " not found");
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	@Override
	public Boolean deleteProductsCategory(Integer id) throws DaoException {
		try {
			String query = "CALL deleteProductsCategory(?);";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, id);
			int count = st.executeUpdate();
			st.close();
			if (count < 1) {
				throw new RecordNotFoundDaoException("product category " + id + " not found ");
			}
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new UpdateException(ex);
		}
	}
	
	@Override
	public Boolean updateProductsCategory(Integer id, ProductsCategory productsCategory) throws DaoException {
		Boolean valid = productsCategory.isVaildOnUpdate(dAOFactory);
		if (valid) {
			try {
				String logo = productsCategory.getLogo();
				/*
				if (File.separator.equals("\\") && logo!=null) {
					logo = logo.replaceAll("\\\\", "\\\\\\\\");
				}
				*/
				String query = "UPDATE productscategories SET name = ?, description = ?, logo = ? WHERE id = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setString(1, productsCategory.getName());
				st.setString(2, productsCategory.getDescription());
				st.setString(3, logo);
				st.setInt(4, id);
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product category " + productsCategory.getId() + " not found");
				}
				return valid;
			} catch (SQLException ex) {
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
	
	@Override
	public ProductsCategory getByName(String name) throws DaoException {
		try {
			ProductsCategory pc = new ProductsCategory();
			ProductsCategory pcp = new ProductsCategory();
			String query = "SELECT id,category,description,logo FROM productscategories WHERE name = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
				int i = 1;
				pc.setId(rs.getInt(i++));
				pc.setName(name);
				pcp.setId(rs.getInt(i++));
				pc.setCategory(pcp);
				pc.setDescription(rs.getString(i++));
				pc.setLogo(rs.getString(i++));
				
				rs.close();
				st.close();
				return pc;
			}
			throw new RecordNotFoundDaoException("Product Category with name: " + name + " not found");
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	/**
	 * Retrieves the product category for rapid-create products.
	 * The name of the category is "Default".
	 * 
	 * @return Default product category.
	 */
	@Override
	public ProductsCategory getDefault() {
		ProductsCategory productsCategory = new ProductsCategory();
		productsCategory.setId(1);
		return productsCategory;
	}
}
