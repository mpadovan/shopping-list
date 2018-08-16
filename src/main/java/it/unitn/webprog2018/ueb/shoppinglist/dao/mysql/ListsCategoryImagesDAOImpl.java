/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;
import java.sql.Connection;


/**
 *
 * @author simon
 */
public class ListsCategoryImagesDAOImpl extends AbstractDAO implements ListsCategoryImagesDAO{
	
	public ListsCategoryImagesDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}
	
}
