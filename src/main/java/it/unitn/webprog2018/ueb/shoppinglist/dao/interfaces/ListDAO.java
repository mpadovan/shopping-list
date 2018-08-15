/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.util.Map;

/**
 *
 * @author Giulia Carocari
 */
public interface ListDAO {
	public List getList(int id);
	
	public Map<PublicProduct,Integer> getPublicProductsOnList(int listId);

	public boolean addProduct(int listId, Product product);

	public boolean addPublicProduct(int listId, PublicProduct product);

	public boolean isOnList(int listId, PublicProduct product);

	public boolean updateAmount(int listId, PublicProduct product);

	public java.util.List<List> getByUser(int userId);
}
