/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author giulia
 */
public interface ListsCategoryDAO {
	List<ListsCategory> getFromQuery(String query);
}
