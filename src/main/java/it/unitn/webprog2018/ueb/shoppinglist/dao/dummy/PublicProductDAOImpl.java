/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author giulia
 */
public class PublicProductDAOImpl implements PublicProductDAO {

	private List<PublicProduct> publicProducts;

	public PublicProductDAOImpl() {
		publicProducts = new LinkedList<>();
		PublicProduct p1 = new PublicProduct();
		p1.setCategory("Frutta");
		p1.setLogo("Sant'Orsola");
		p1.setName("Fragole");
		p1.setNote("Frutto rosso con i semi gialli all'esterno");

		PublicProduct p2 = new PublicProduct();
		p2.setCategory("Protezioni solari");
		p2.setLogo("Nivea");
		p2.setName("Crema solare protezione 50");
		p2.setNote("Bottiglia blu con scritte giallo/arancio");

		PublicProduct p3 = new PublicProduct();
		p3.setCategory("Pasta");
		p3.setLogo("Barilla");
		p3.setName("Spaghetti numero 5");
		p3.setNote("Dimensione più piccola accettabile di spaghetti");

		publicProducts.add(p1);
		publicProducts.add(p2);
		publicProducts.add(p3);
	}

	@Override
	public List<PublicProduct> getAll() {
		return publicProducts;
	}

	/**
	 * Note that this implementation will not consider sorting, It should be far
	 * easier to implement with SQL
	 *
	 * @param query
	 * @param sortBy
	 * @return 
	 *
	 */
	@Override
	public List<PublicProduct> getFromQuery(StringTokenizer query, StringTokenizer sortBy) {
		List<PublicProduct> matching = new LinkedList<>();

		if (query == null) {
			return publicProducts;
		}

		for (PublicProduct p : publicProducts) {
			while (query.hasMoreElements()) {
				if (p.getName().toLowerCase().contains(query.nextToken().toLowerCase())) {
					matching.add(p);
				}
			}
		}
		return matching;
	}

	@Override
	public PublicProduct getByName(String name) {
		for (PublicProduct p : publicProducts) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	@Override
	public void updateProduct(PublicProduct product) {
		for (PublicProduct p : publicProducts) {
			if (p.getName().equals(product.getName())) {
				p = product;
			}
		}
	}

}
