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
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;
import java.io.File;
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
public class ListDAOImpl extends AbstractDAO implements ListDAO {

	public ListDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}

	/**
	 * ATTENZIONE: non da e-mail, password e image degli owner
	 */
	@Override
	public List getList(Integer id) throws DaoException {
		try {
			List list = new List();
			String query = "SELECT l.name,l.iduser,l.idcategory,l.description,l.image,"
					+ "u.name,u.lastname,u.administrator,"
					+ "lc.name,lc.description "
					+ "FROM lists l "
					+ "INNER JOIN users u ON l.iduser = u.id "
					+ "INNER JOIN listscategories lc ON l.idcategory = lc.id "
					+ "WHERE l.id = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			int i = 1;
			if (rs.first()) {
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
			throw new RecordNotFoundDaoException("List with id: " + id + " not found");
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	@Override
	public List getList(String name, User owner) throws DaoException {
		try {
			List list = new List();
			String query = "SELECT l.id,l.idcategory,l.description,l.image,"
					+ "lc.name,lc.description "
					+ "FROM lists l "
					+ "INNER JOIN listscategories lc ON l.idcategory = lc.id "
					+ "WHERE l.name = ? AND l.iduser = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setString(1, name);
			st.setInt(2, owner.getId());
			ResultSet rs = st.executeQuery();
			ListsCategory lc = new ListsCategory();
			int i = 1;
			if (rs.first()) {
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
		try {
			String query = "SELECT pp.id,pp.name,pp.note,pp.logo,pp.photography,ppl.quantity,pp.idproductscategory,"
					+ "pc.name,pc.category,pc.description,pc.logo "
					+ "FROM publicproductsonlists ppl "
					+ "INNER JOIN publicproducts pp ON ppl.idpublicproduct = pp.id "
					+ "INNER JOIN productscategories pc ON pc.id = pp.idproductscategory "
					+ "WHERE ppl.quantity >= 0 AND ppl.idlist = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, listId);
			ResultSet rs = st.executeQuery();
			PublicProduct p;
			ProductsCategory pc, pcp;
			Integer quantity;
			int i = 1;
			while (rs.next()) {
				i = 1;
				p = new PublicProduct();
				pc = new ProductsCategory();
				pcp = new ProductsCategory();
				p.setId(rs.getInt(i++));
				p.setName(rs.getString(i++));
				p.setNote(rs.getString(i++));
				p.setLogo(rs.getString(i++));
				p.setPhotography(rs.getString(i++));
				quantity = rs.getInt(i++);
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				pcp.setId(rs.getInt(i++));
				pc.setCategory(pcp);
				pc.setDescription(rs.getString(i++));
				pc.setLogo(rs.getString(i++));
				p.setCategory(pc);
				list.put(p, quantity);
			}
			rs.close();
			st.close();
			return list;
		} catch (SQLException ex) {
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
		try {
			String query = "SELECT p.id,p.name,p.note,p.logo,p.photography,pl.quantity,"
					+ "p.iduser,u.name,u.lastname,u.administrator,"
					+ "p.idproductscategory,pc.name,pc.category,pc.description,pc.logo "
					+ "FROM productsonlists pl "
					+ "INNER JOIN products p ON pl.idproduct = p.id "
					+ "INNER JOIN productscategories pc ON pc.id = p.idproductscategory "
					+ "INNER JOIN users u ON p.iduser = u.id "
					+ "WHERE pl.quantity >= 0 AND pl.idlist = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, listId);
			ResultSet rs = st.executeQuery();
			Product p;
			ProductsCategory pc, pcp;
			User u;
			Integer quantity;
			int i = 1;
			while (rs.next()) {
				i = 1;
				p = new Product();
				pc = new ProductsCategory();
				pcp = new ProductsCategory();
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
				u.setAdministrator(rs.getInt(i++) != 0);
				pc.setId(rs.getInt(i++));
				pc.setName(rs.getString(i++));
				pcp.setId(rs.getInt(i++));
				pc.setCategory(pcp);
				pc.setDescription(rs.getString(i++));
				pc.setLogo(rs.getString(i++));
				p.setCategory(pc);
				p.setOwner(u);
				list.put(p, quantity);
			}
			rs.close();
			st.close();
			return list;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	/**
	 * ATTENZIONE: la quantitá é settata a 1, se volete cambiarla chiamate
	 * updateAmount()
	 */
	@Override
	public Boolean addProduct(Integer listId, Product product) throws DaoException {
		Boolean valid = true; // product.isVaildOnCreate(dAOFactory);
		if (valid) {
			try {
				String query = "CALL addProductOnList(?,?)";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, product.getId());
				st.setInt(2, listId);
				st.executeUpdate();
				st.close();
				return valid;
			} catch (SQLException ex) {
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new UpdateException(ex);
			}
		}
		return valid;
	}

	/**
	 * ATTENZIONE: la quantitá é settata a 1, se volete cambiarla chiamate
	 * updateAmount()
	 */
	@Override
	public Boolean addPublicProduct(Integer listId, PublicProduct product) throws DaoException {
		Boolean valid = true; // product.isVaildOnCreate(dAOFactory);
		if (valid) {
			try {
				String query = "CALL addPublicProductOnList(?,?)";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, product.getId());
				st.setInt(2, listId);
				st.executeUpdate();
				st.close();
				return valid;
			} catch (SQLException ex) {
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
			String query = "SELECT EXISTS(SELECT 1 "
					+ "FROM publicproductsonlists "
					+ "WHERE idlist = ? AND idpublicproduct = ? AND quantity >= 0) AS exist";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, listId);
			st.setInt(2, product.getId());
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
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
			String query = "SELECT EXISTS(SELECT 1 "
					+ "FROM productsonlists "
					+ "WHERE idlist = ? AND idproduct = ? AND quantity >= 0) AS exist";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, listId);
			st.setInt(2, product.getId());
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
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
		Boolean valid = true; // product.isVaildOnUpdate(dAOFactory);
		if (newAmount < 0) {
			throw new DaoException("invalid quantity value");
		}
		if (valid) {
			try {
				String query = "UPDATE publicproductsonlists "
						+ "SET quantity = ?"
						+ " WHERE idlist = ?"
						+ " AND idpublicproduct = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, newAmount);
				st.setInt(2, listId);
				st.setInt(3, product.getId());
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product: " + product.getId() + " not exist in list: " + listId);
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
	public Boolean updateAmount(Integer listId, Product product, Integer newAmount) throws DaoException {
		Boolean valid = true; // product.isVaildOnUpdate(dAOFactory);
		if (newAmount < 0) {
			throw new DaoException("invalid quantity value");
		}
		if (valid) {
			try {
				String query = "UPDATE productsonlists "
						+ "SET quantity = ?"
						+ " WHERE idlist = ?"
						+ " AND idproduct = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, newAmount);
				st.setInt(2, listId);
				st.setInt(3, product.getId());
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product: " + product.getId() + " not exist in list: " + listId);
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
	public Boolean updateAmount(Integer listId, PublicProduct product) throws DaoException {
		Boolean valid = true; // product.isVaildOnUpdate(dAOFactory);
		if (valid) {
			try {
				String query = "UPDATE publicproductsonlists "
						+ "SET quantity = quantity + 1"
						+ " WHERE idlist = ?"
						+ " AND idpublicproduct = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, listId);
				st.setInt(2, product.getId());
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product: " + product.getId() + " not exist in list: " + listId);
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
	public Boolean updateAmount(Integer listId, Product product) throws DaoException {
		Boolean valid = true; // product.isVaildOnUpdate(dAOFactory);
		if (valid) {
			try {
				String query = "UPDATE productsonlists "
						+ "SET quantity = quantity + 1"
						+ " WHERE idlist = ?"
						+ " AND idproduct = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, listId);
				st.setInt(2, product.getId());
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product: " + product.getId() + " not exist in list: " + listId);
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
	public Boolean hasAddDeletePermission(Integer listId, Integer userId) throws DaoException {
		Boolean hasPermission;
		try {
			String query = "SELECT adddelete FROM sharedlists WHERE iduser = ? AND idlist = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, userId);
			st.setInt(2, listId);
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
				hasPermission = rs.getInt(1) == 1;

				rs.close();
				st.close();
				return hasPermission;
			}
			throw new RecordNotFoundDaoException("list " + listId + " is not shared by: " + userId);
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	@Override
	public Boolean hasModifyPermission(Integer listId, Integer userId) throws DaoException {
		Boolean hasPermission;
		try {
			String query = "SELECT modifylist FROM sharedlists WHERE iduser = ? AND idlist = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, userId);
			st.setInt(2, listId);
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
				hasPermission = rs.getInt(1) == 1;

				rs.close();
				st.close();
				return hasPermission;
			}
			throw new RecordNotFoundDaoException("list " + listId + " is not shared by: " + userId);
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	@Override
	public Boolean hasDeletePermission(Integer listId, Integer userId) throws DaoException {
		Boolean hasPermission;
		try {
			String query = "SELECT deletelist FROM sharedlists WHERE iduser = ? AND idlist = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, userId);
			st.setInt(2, listId);
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
				hasPermission = rs.getInt(1) == 1;

				rs.close();
				st.close();
				return hasPermission;
			}
			throw new RecordNotFoundDaoException("list " + listId + " is not shared by: " + userId);
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
		try {
			String query = "SELECT l.id,l.name,l.description,l.image,"
					+ "l.idcategory,lc.name,lc.description "
					+ "FROM lists l "
					+ "INNER JOIN listscategories lc ON l.idcategory = lc.id "
					+ "WHERE iduser = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			List l;
			ListsCategory lc;
			int i;
			while (rs.next()) {
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
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	@Override
	public Boolean deleteFromList(Integer listId, PublicProduct product) throws DaoException {
		Boolean valid = product.isVaildOnDestroy(dAOFactory);
		if (valid) {
			try {
				String query = "CALL deletePublicProductOnList(?,?)";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, product.getId());
				st.setInt(2, listId);
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product " + product.getId() + " not found in list " + listId);
				}
				return valid;
			} catch (SQLException ex) {
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new UpdateException(ex);
			}
		}
		return valid;
	}

	@Override
	public Boolean deleteFromList(Integer listId, Product product) throws DaoException {
		Boolean valid = product.isVaildOnDestroy(dAOFactory);
		if (valid) {
			try {
				String query = "CALL deleteProductOnList(?,?)";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, product.getId());
				st.setInt(2, listId);
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product " + product.getId() + " not found in list " + listId);
				}
				return valid;
			} catch (SQLException ex) {
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
			String query = "select EXISTS(SELECT 1 FROM sharedlists WHERE idlist = ? AND iduser = ?) as exist";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, listId);
			st.setInt(2, userId);
			ResultSet rs = st.executeQuery();
			if (rs.first()) {
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

	@Override
	public java.util.List<Integer> getConnectedUsersIds(Integer listId) throws DaoException {
		java.util.List<Integer> list = new ArrayList<>();
		try {
			String query = "select u.id, u.email from sharedlists s INNER JOIN users u ON s.iduser = u.id where s.idlist = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, listId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
			rs.close();
			st.close();
			return list;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	/*
		Questo metodo deve ritornare tutte le liste dello user escluse quelle che si trovano nella tabella sharedlists
	 */
	@Override
	public java.util.List<List> getPersonalLists(Integer id) throws DaoException {
		java.util.List<List> list = new ArrayList<>();
		try {
			String query = "select distinct l.id,l.name,l.iduser,l.idcategory,l.description,l.image \n"
					+ "from lists l \n"
					+ "where l.iduser = ? AND l.id NOT IN (SELECT DISTINCT idlist FROM sharedlists)";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			List l;
			User u;
			ListsCategory lc;
			int i;
			while (rs.next()) {
				i = 1;
				l = new List();
				u = new User();
				lc = new ListsCategory();
				l.setId(rs.getInt(i++));
				l.setName(rs.getString(i++));
				u.setId(rs.getInt(i++));
				lc.setId(rs.getInt(i++));
				l.setDescription(rs.getString(i++));
				l.setImage(rs.getString(i++));

				l.setOwner(u);
				l.setCategory(lc);
				list.add(l);
			}
			rs.close();
			st.close();
			return list;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	/*
		Questo metodo deve restituire tutte le liste per cui vale:
		- user è owner e ci sono entry corrispondenti alla lista in sharedLists
		- user non è owner ma ci sono entry con la sua id in sharedlists per quella lista
	 */
	@Override
	public java.util.List<List> getSharedLists(Integer id) throws DaoException {
		java.util.List<List> list = new ArrayList<>();
		try {
			String query = "select distinct l.id,l.name,l.iduser,l.idcategory,l.description,l.image\n"
					+ "from lists l inner join sharedlists s on l.id = s.idlist\n"
					+ "where l.iduser = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			List l;
			User u;
			ListsCategory lc;
			int i;
			while (rs.next()) {
				i = 1;
				l = new List();
				u = new User();
				lc = new ListsCategory();
				l.setId(rs.getInt(i++));
				l.setName(rs.getString(i++));
				u.setId(rs.getInt(i++));
				lc.setId(rs.getInt(i++));
				l.setDescription(rs.getString(i++));
				l.setImage(rs.getString(i++));

				l.setOwner(u);
				l.setCategory(lc);
				list.add(l);
			}
			rs.close();
			st.close();
			return list;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}

	//Restituisce solo l'id dell'utente
	@Override
	public java.util.List<User> getConnectedUsers(Integer listId) throws DaoException {
		java.util.List<User> list = new ArrayList<>();
		try {
			String query = "SELECT u.id, u.email\n"
					+ "FROM sharedlists s inner join users u on s.iduser = u.id\n"
					+ "WHERE s.idlist = ?;";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, listId);
			ResultSet rs = st.executeQuery();
			User u;
			while (rs.next()) {
				u = new User();
				u.setId(rs.getInt(1));
				u.setEmail(rs.getString(2));
				list.add(u);
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
	public Boolean deleteList(Integer listId) throws DaoException {
		try {
			String query = "delete from lists where id = ?";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, listId);
			int count = st.executeUpdate();
			st.close();
			if (count != 1) {
				throw new RecordNotFoundDaoException("list " + listId + " not found");
			}
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new UpdateException(ex);
		}
	}

	@Override
	public Boolean linkShoppingListToUser(List list, Integer idpartecipant, Boolean addDeletePermission,
			Boolean editPermission, Boolean deletePermission) throws DaoException {
		try {
			String query = "insert into sharedlists (iduser,idlist,modifylist,deletelist,adddelete) values (?,?,?,?,?)";
			PreparedStatement st = this.getCon().prepareStatement(query);
			st.setInt(1, idpartecipant);
			st.setInt(2, list.getId());
			st.setBoolean(3, editPermission == null ? false : editPermission);
			st.setBoolean(4, deletePermission == null ? false : deletePermission);
			st.setBoolean(5, addDeletePermission == null ? false : addDeletePermission);
			int count = st.executeUpdate();
			st.close();
			return count == 1;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new UpdateException(ex);
		}
	}

	@Override
	public Boolean addList(List list) throws DaoException {
		Boolean valid = list.isVaildOnCreate(dAOFactory);
		if (valid) {
			try {
				String image = list.getImage();
				if (File.separator.equals("\\") && image != null) {
					image = image.replaceAll("\\\\", "\\\\\\\\");
				}
				String query = "insert into lists (name,iduser,idcategory,description,image) values (?,?,?,?,?)";
				PreparedStatement st = this.getCon().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				st.setString(1, list.getName());
				st.setInt(2, list.getOwner().getId());
				st.setInt(3, list.getCategory().getId());
				st.setString(4, list.getDescription());
				st.setString(5, image);
				int count = st.executeUpdate();
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					list.setId(rs.getInt(1));
				}
				st.close();
				return valid && (count == 1);
			} catch (SQLException ex) {
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new UpdateException(ex);
			}
		}
		return valid;
	}

	@Override
	public Boolean updateList(Integer id, List list) throws DaoException {
		Boolean valid = list.isVaildOnUpdate(dAOFactory);
		if (valid) {
			try {
				String image = list.getImage();
				if (File.separator.equals("\\") && image != null) {
					image = image.replaceAll("\\\\", "\\\\\\\\");
				}
				String query = "UPDATE lists SET name = ?,iduser = ?,idcategory = ?,description = ?,image = ? WHERE id = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setString(1, list.getName());
				st.setInt(2, list.getOwner().getId());
				st.setInt(3, list.getCategory().getId());
				st.setString(4, list.getDescription());
				st.setString(5, image);
				st.setInt(6, id);
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("List " + id + " not found");
				}
				return valid;
			} catch (SQLException ex) {
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new DaoException(ex);
			}
		}
		return valid;
	}
	
	// ---------------------------------------------------------------------- //
	//////////////////////// ANONYMOUS USER METHODS ////////////////////////////
	// ---------------------------------------------------------------------- //
	
	@Override
	public Boolean addProduct(String token, PublicProduct product) throws DaoException {
		Boolean valid = true; // product.isVaildOnCreate(dAOFactory);
		if (valid) {
			try {
				String query = "INSERT INTO anonlists (tokenid,publicproductid) VALUES (?,?)";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, Integer.parseInt(CookieCipher.decrypt(token)));
				st.setInt(2, product.getId());
				st.executeUpdate();
				st.close();
				return valid;
			} catch (SQLException ex) {
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new UpdateException(ex);
			}
		}
		return valid;
	}

	@Override
	public Boolean updateAmount(String token, PublicProduct product, Integer newAmount) throws DaoException {
		Boolean valid = true; // product.isVaildOnUpdate(dAOFactory);
		if (newAmount <= 0) {
			throw new DaoException("invalid quantity value");
		}
		if (valid) {
			try {
				String query = "UPDATE anonlists"
						+ " SET quantity = ?"
						+ " WHERE tokenid = ?"
						+ " AND publicproductid = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, newAmount);
				st.setInt(2, Integer.parseInt(CookieCipher.decrypt(token)));
				st.setInt(3, product.getId());
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product: " + product.getId() + " not exist in anonuser: " + Integer.parseInt(CookieCipher.decrypt(token)));
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
	public Boolean updateAmount(String token, PublicProduct product) throws DaoException {
		Boolean valid = true; // product.isVaildOnUpdate(dAOFactory);
		if (valid) {
			try {
				String query = "UPDATE anonlists"
						+ " SET quantity = quantity + 1"
						+ " WHERE tokenid = ?"
						+ " AND publicproductid = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, Integer.parseInt(CookieCipher.decrypt(token)));
				st.setInt(2, product.getId());
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product: " + product.getId() + " not exist in anonuser: " + Integer.parseInt(CookieCipher.decrypt(token)));
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
	public Boolean deleteFromList(String token, PublicProduct product) throws DaoException {
		Boolean valid = product.isVaildOnDestroy(dAOFactory);
		if (valid) {
			try {
				String query = "DELETE FROM anonlists WHERE tokenid = ? AND publicproductid = ?";
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.setInt(1, Integer.parseInt(CookieCipher.decrypt(token)));
				st.setInt(2, product.getId());
				int count = st.executeUpdate();
				st.close();
				if (count != 1) {
					throw new RecordNotFoundDaoException("product " + product.getId() + " not found in anonuser " + Integer.parseInt(CookieCipher.decrypt(token)));
				}
				return valid;
			} catch (SQLException ex) {
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new UpdateException(ex);
			}
		}
		return valid;
	}

}
