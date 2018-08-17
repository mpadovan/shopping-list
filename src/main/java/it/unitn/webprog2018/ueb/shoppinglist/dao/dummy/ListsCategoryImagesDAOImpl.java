/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;

/**
 *
 * @author simon
 */
public class ListsCategoryImagesDAOImpl implements ListsCategoryImagesDAO {
	private DAOFactory dAOFactory;

	public ListsCategoryImagesDAOImpl(DAOFactory dAOFactory) {
		this.dAOFactory = dAOFactory;
	}
	
}
