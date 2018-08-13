/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import java.sql.Connection;

/**
 *
 * @author Michele
 */
public class Query {
	
	protected Connection connection;
	
	public Query(Connection connection){
		this.connection = connection;
	}
}
