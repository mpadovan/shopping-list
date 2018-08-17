package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
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
public class ProductsCategoryDAOImpl extends AbstractDAO implements ProductsCategoryDAO{
	private DAOFactory dAOFactory;
	
	public ProductsCategoryDAOImpl(Connection connection, DAOFactory dAOFactory) {
		super(connection, dAOFactory);
	}
	
	/**
	 * @param mathing, parola chiave;
	 * @return un'ArrayList con tutte le categorie di prodotti con nomi conteneti quella parola, lancia una DaoException per qualsiasi errore riscontrato
	 * @throws DaoException
	 */
	
	@Override
	public List<ProductsCategory> getFromQuery(String matching) throws DaoException{
		List<ProductsCategory> list = new ArrayList<ProductsCategory>();
		try{
			String query = "SELECT id,name,category,description,logo FROM productscategories"
					+ "	WHERE name LIKE \"%"+matching+"%\"";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			ProductsCategory pc;
			
			while (rs.next())
			{
				pc = new ProductsCategory();
				pc.setId(rs.getInt("id"));
				pc.setName(rs.getString("name"));
				Integer idcategory= rs.getInt("category");
				ProductsCategory productsCategory = getById(idcategory);
				pc.setCategory(productsCategory);
				pc.setDescription(rs.getString("description"));
				pc.setLogo(rs.getString("logo"));
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
	
	/**
	 * @param pc, prodotto da aggiungere
	 * @return true operazione andata a buon termine, false altrimenti
	 * @throws DaoException 
	 */
	
	@Override
	public Boolean addProductsCategory(ProductsCategory pc) throws DaoException{
		try{
			String query = "INSERT INTO productscategories (name,category,description,logo) VALUES (\""+
					pc.getName()+ "\"," +
					(pc.getCategory().getId() < 0 ? "null" : pc.getCategory().getId())+ ",\"" +
					pc.getDescription()+ "\",\"" +
					pc.getLogo()+"\")";
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
	public List<ProductsCategory> getAll() throws DaoException{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public ProductsCategory getById(Integer id) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean deleteProductsCategory(Integer id) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
