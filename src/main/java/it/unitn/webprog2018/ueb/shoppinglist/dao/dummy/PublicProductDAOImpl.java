/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.LinkedList;
import java.util.List;

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

	@Override
	public List<PublicProduct> getFromQuery(String query) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public PublicProduct getByName(String name) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
