/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author giulia
 */
public class ProductsCategoryDAOImpl implements ProductsCategoryDAO {
	private DAOFactory dAOFactory;
	private static List<ProductsCategory> productsCategories;

	public ProductsCategoryDAOImpl(DAOFactory dAOFactory) {
		this.dAOFactory=dAOFactory;
		productsCategories = new LinkedList<>();

		ProductsCategory pc1 = new ProductsCategory();
		pc1.setId(1);
		pc1.setName("Frutta");
		pc1.setDescription("Vegetali dal sapore dolce");
		productsCategories.add(pc1);

		ProductsCategory pc2 = new ProductsCategory();
		pc2.setId(2);
		pc2.setName("Frutta surgelata");
		pc2.setDescription("Vegetali dal sapore dolce, ma surgelati");
		pc2.setCategory(-1);
		productsCategories.add(pc2);

		ProductsCategory pc3 = new ProductsCategory();
		pc3.setId(3);
		pc3.setName("Verdura");
		pc3.setDescription("Vegetali in genere mangiati salati");
		productsCategories.add(pc3);

		ProductsCategory pc4 = new ProductsCategory();
		pc4.setId(4);
		pc4.setName("Protezioni solari");
		pc4.setDescription("Creme per il corpo che proteggono contro l'azione dei raggi solari");
		pc4.setCategory(-1);
		
		productsCategories.add(pc4);

		ProductsCategory pc5 = new ProductsCategory();
		pc5.setId(5);
		pc5.setName("Crema");
		pc5.setDescription("Prodotto da spalmare sul corpo. Può avere diverse finalità");
		productsCategories.add(pc5);

		ProductsCategory pc6 = new ProductsCategory();
		pc6.setId(6);
		pc6.setName("Cereali e legumi");
		pc6.setDescription("Vegetali amidacei e in genere ricchi di carboidrati");
		productsCategories.add(pc6);
	}

	@Override
	public List<ProductsCategory> getFromQuery(String query) {
		List<ProductsCategory> matching = new ArrayList<>();

		System.out.println("Checkin out custom products");
		for (ProductsCategory p : productsCategories) {
			if (p.getName().toLowerCase().contains(query.toLowerCase())) {
				matching.add(p);
			}
		}
		return matching;
	}

	@Override
	public Boolean addProductsCategory(ProductsCategory productCategory) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	public List<ProductsCategory> getAll() throws DaoException{
		return productsCategories;
	}

	@Override
	public ProductsCategory getById(Integer id) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public ProductsCategory getByName(String name) throws DaoException {
		for (ProductsCategory u : productsCategories) {
			if (u.getName().equals(name)) {
				return u;
			}
		}
		throw new RecordNotFoundDaoException("Product category with name: " + name + " not found");
	}

}
