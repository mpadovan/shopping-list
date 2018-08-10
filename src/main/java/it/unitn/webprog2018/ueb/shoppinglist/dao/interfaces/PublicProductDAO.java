/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.List;

/**
 *
 * @author giulia
 */
public interface PublicProductDAO {
	public List<PublicProduct> getAll();
	
	public List<PublicProduct> getFromQuery(String query);
	
	public PublicProduct getByName(String name);
}
