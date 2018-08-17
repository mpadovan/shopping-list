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
import java.util.logging.Level;
import java.util.logging.Logger;

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
		productCategory.setId((int)(Math.random() * 10000));
		for(ProductsCategory c : productsCategories) {
			if (c.getName().equals(productCategory.getName())) {
				return false;
			}
		}
		productsCategories.add(productCategory);
		return true;
	}


	public List<ProductsCategory> getAll() throws DaoException{
		return productsCategories;
	}

	@Override
	public ProductsCategory getById(Integer id) throws DaoException {
		for (ProductsCategory c : productsCategories) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		throw new RecordNotFoundDaoException("Product category with id: " + id + " not found");
	}

	@Override
	public Boolean deleteProductsCategory(Integer id) throws DaoException {
		ProductsCategory c = getById(id);
		removeProductsCategoryFromList(c);
		return true;
	}
	
	

	@Override
	public Boolean updateProductsCategory(Integer id, ProductsCategory productsCategory) throws DaoException {
		getById(id);

		Boolean valid = productsCategory.isVaildOnUpdate(dAOFactory);
		if (valid) {
			updateProduct(id, productsCategory);
		}
		return valid;
	}
	private synchronized void updateProduct(Integer id, ProductsCategory c) throws DaoException {
		ProductsCategory found = getById(id);

		found.setId(c.getId());
		found.setName(c.getName());
		found.setLogo(c.getLogo());
		found.setDescription(c.getDescription());
		found.setCategory(c.getCategory());

	}
	private synchronized void removeProductsCategoryFromList(ProductsCategory c) {
		productsCategories.remove(c);
	}
}
