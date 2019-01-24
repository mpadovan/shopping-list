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
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategoriesImage;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
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
 * @author simon
 */
public class ListsCategoryImagesDAOImpl extends AbstractDAO implements ListsCategoryImagesDAO {

	public ListsCategoryImagesDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}

	@Override
	public Boolean addListsCategoriesImage(ListsCategoriesImage listCategoriesImage) throws DaoException {
		Boolean valid = listCategoriesImage.isVaildOnCreate(dAOFactory);
		if (valid) {
			try {
				String image = listCategoriesImage.getImage();
				/*
				if (File.separator.equals("\\") && image != null) {
					image = image.replaceAll("\\\\", "\\\\\\\\");
				}
				 */
				String query = "INSERT INTO listscategoriesimages (image,idcategory) VALUES (?,?)";
				PreparedStatement st = this.getCon().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				st.setString(1, image);
				st.setInt(2, listCategoriesImage.getCategory().getId());
				int count = st.executeUpdate();
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					listCategoriesImage.setId(rs.getInt(1));
				}
				st.close();
				return valid && (count == 1);
			} catch (SQLException ex) {
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}

	@Override
	public List<ListsCategoriesImage> getAll() throws DaoException {
		List<ListsCategoriesImage> list = new ArrayList<>();
		try {
			String query = "select lci.id,lci.image,lci.idcategory,lc.name,lc.description from listscategoriesimages lci inner join listscategories lc on lci.idcategory = lc.id";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			ListsCategoriesImage lci;
			ListsCategory lc;
			int i;
			while (rs.next()) {
				i = 1;
				lci = new ListsCategoriesImage();
				lc = new ListsCategory();

				lci.setId(rs.getInt(i++));
				lci.setImage(rs.getString(i++));
				lc.setId(rs.getInt(i++));
				lc.setName(rs.getString(i++));
				lc.setDescription(rs.getString(i++));

				lci.setCategory(lc);
				list.add(lci);
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
	public Boolean updateListsCategoriesImage(Integer categoryId, ListsCategoriesImage listCategoriesImage) throws DaoException {
		Boolean valid = listCategoriesImage.isVaildOnUpdate(dAOFactory);
		if (valid) {
			try {
				String image = listCategoriesImage.getImage();
				/*
				if (File.separator.equals("\\") && image != null) {
					image = image.replaceAll("\\\\", "\\\\\\\\");
				}
				*/
				String query = "UPDATE listscategoriesimages SET image = ?, idcategory = ? WHERE id = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setString(1, image);
				st.setInt(2, listCategoriesImage.getCategory().getId());
				st.setInt(3, categoryId);
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("ListsCategoriesImage " + categoryId + " not found");
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
	public ListsCategoriesImage getById(Integer id) throws DaoException {
		try {
			ListsCategoriesImage lci;
			ListsCategory lc;
			String query = "select lci.image,lci.idcategory,lc.name,lc.description\n"
					+ "from listscategoriesimages lci inner join listscategories lc on lci.idcategory = lc.id\n"
					+ "where lci.id = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
				int i = 1;
				lci = new ListsCategoriesImage();
				lc = new ListsCategory();

				lci.setId(id);
				lci.setImage(rs.getString(i++));
				lc.setId(rs.getInt(i++));
				lc.setName(rs.getString(i++));
				lc.setDescription(rs.getString(i++));

				lci.setCategory(lc);

				rs.close();
				st.close();
				return lci;
			}
			throw new RecordNotFoundDaoException("ListsCategoriesImage " + id + " not found");
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	@Override
	public List<ListsCategoriesImage> getByCategoriesID(Integer CategoryID) throws DaoException {
		try {
			List<ListsCategoriesImage> lci = new ArrayList<ListsCategoriesImage>();
			String query = "select lci.id, lci.image"
					+ " from listscategoriesimages lci"
					+ " where lci.idcategory = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, CategoryID);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ListsCategoriesImage listsCategoriesImage = new ListsCategoriesImage();
				listsCategoriesImage.setId(rs.getInt("id"));
				listsCategoriesImage.setImage(rs.getString("image"));
				lci.add(listsCategoriesImage);

			}
			rs.close();
			st.close();
			return lci;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	@Override
	public Boolean deleteImage(Integer id) throws DaoException {
		try {
			String query = "DELETE FROM listscategoriesimages WHERE id = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, id);
			int count = st.executeUpdate();
			st.close();
			if (count != 1) {
				throw new RecordNotFoundDaoException("product " + id + " not found");
			}
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new UpdateException(ex);
		}
	}

	@Override
	public Boolean deleteImageTemporary(Integer idcategory) throws DaoException {
		try {
			String query = "DELETE FROM listscategoriesimages WHERE idcategory = ? AND image = 'temporary'";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, idcategory);
			int count = st.executeUpdate();
			st.close();
			if (count != 1) {
				throw new RecordNotFoundDaoException("Image with " + idcategory + " not found");
			}
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new UpdateException(ex);
		}
	}

}
