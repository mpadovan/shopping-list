package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Michele
 */
public class ProductsCategoryDAOImpl extends AbstractDAO implements ProductsCategoryDAO{
	
	public ProductsCategoryDAOImpl(Connection connection) {
		super(connection);
	}
	
	/**
	 * @param mathing, parola chiave;
	 * @return un'ArrayList con tutte le categorie di prodotti con nomi conteneti quella parola, lancia una DaoException per qualsiasi errore riscontrato
	 * @throws DaoException
	 */
	
	@Override
	public ArrayList<ProductsCategory> getFromQuery(String matching) throws DaoException{
		ArrayList<ProductsCategory> list = new ArrayList<ProductsCategory>();
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
				pc.setCategory(rs.getInt("category"));
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
					(pc.getCategory() < 0 ? "null" : pc.getCategory())+ ",\"" +
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
	
}
