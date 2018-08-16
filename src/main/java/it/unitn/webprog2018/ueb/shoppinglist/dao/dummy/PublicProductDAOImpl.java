/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author giulia
 */
public class PublicProductDAOImpl implements PublicProductDAO {

	private static List<PublicProduct> publicProducts;

	public static List<PublicProduct> getPublicProducts() {
		return publicProducts;
	}

	public PublicProductDAOImpl() {
		publicProducts = new LinkedList<>();
		PublicProduct p1 = new PublicProduct();
		p1.setId(1);
		p1.setCategory(new ProductsCategory());
		p1.getCategory().setId(1);
		p1.getCategory().setName("Frutta");
		p1.setName("Fragole");
		p1.setNote("Frutto rosso con i semi gialli all'esterno");

		PublicProduct p2 = new PublicProduct();
		p2.setId(2);
		p1.setCategory(new ProductsCategory());
		p1.getCategory().setId(4);
		p1.getCategory().setName("Protezioni solari");
		p2.setName("Crema solare protezione 50");
		p2.setNote("Bottiglia blu con scritte giallo/arancio");

		PublicProduct p3 = new PublicProduct();
		p3.setCategory(new ProductsCategory());
		p3.getCategory().setId(6);
		p3.getCategory().setName("Pasta");
		p3.setName("Spaghetti numero 5");
		p3.setNote("Dimensione più piccola accettabile di spaghetti");
		p3.setId(3);
		
		PublicProduct p4 = new PublicProduct();
		p4.setCategory(new ProductsCategory());
		p4.getCategory().setId(9);
		p4.getCategory().setName("Cereali e legumi");
		// p4.setLogo("EquoSolidale");
		p4.setName("Quinoa");
		p4.setNote("Cereale sudamericano dai minuscoli semi rossi, bianchi o neri");
		p4.setId(4);
		
		PublicProduct p5 = new PublicProduct();
		p5.setCategory(new ProductsCategory());
		p5.getCategory().setId(7);
		p5.getCategory().setName("Pane");
		// p5.setLogo("Mulino Bianco");
		p5.setName("Pan Bauletto integrale");
		p5.setNote("Pane in cassetta da cereali integrali, con olio di oliva");
		p5.setId(5);
		
		PublicProduct p6 = new PublicProduct();
		p6.setCategory(new ProductsCategory());
		p6.getCategory().setId(8);
		p6.getCategory().setName("Casearia");
		// p6.setLogo("Lerdammer");
		p6.setName("Lerdammer Legére");
		p6.setNote("Formaggio a fette a ridotto contenuto di grassi. Irresistibile scioglievolezza.");
		p6.setId(6);
		PublicProduct p7 = new PublicProduct();
		p7.setCategory(new ProductsCategory());
		p7.getCategory().setId(2);
		p7.getCategory().setName("Verdura");
		p7.setLogo("Bonduelle");
		p7.setName("Lattuga in sacchetto");
		p7.setNote("Lattuga già lavata e pronta per il consumo");
		p7.setId(7);
		
		PublicProduct p8 = new PublicProduct();
		p8.setCategory(new ProductsCategory());
		p8.getCategory().setId(9);
		p8.getCategory().setName("Cereali e legumi");
		p8.setLogo("Valfrutta");
		p8.setName("Lenticchie a vapore");
		p8.setNote("Lenticchie in lattina pronte al consumo");
		p8.setId(8);
		publicProducts.add(p1);
		publicProducts.add(p2);
		publicProducts.add(p3);
		publicProducts.add(p6);
		publicProducts.add(p4);
		publicProducts.add(p5);
		publicProducts.add(p7);
		publicProducts.add(p8);
	}
	
	@Override
	public PublicProduct getById(Integer id) {
		for (PublicProduct p : publicProducts) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * @param query String to filter the results
	 * @return A list of public products that match the query param
	 *
	 */
	@Override
	public List<PublicProduct> getFromQuery(String query) {
		List<PublicProduct> matching = new LinkedList<>();

		if (query == null) {
			return publicProducts;
		}

		for (PublicProduct p : publicProducts) {
			if (p.getName().toLowerCase().contains(query.toLowerCase())) {
				matching.add(p);
			}
		}
		return matching;
	}

	@Override
	public boolean updateProduct(PublicProduct product) {
		for (PublicProduct p : publicProducts) {
			if (p.getName().equals(product.getName())) {
				p = product;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addProduct(PublicProduct product) {
		product.setId((int)(Math.random() * 10000));
		for(PublicProduct p : publicProducts) {
			if (p.getName().equals(product.getName())) {
				return false;
			}
		}
		publicProducts.add(product);
		return true;
	}

	@Override
	public List<PublicProduct> getAll() {
		return publicProducts;
	}

}
