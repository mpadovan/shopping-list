/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giuliapeserico
 */
public class DatabaseManager {

	private final String dburl;
	private final String dbUsername;
	private final String dbPassword;

	private final transient Connection con;

	public DatabaseManager(String dburl, String dbUsername, String dbPassword) {
		this.dburl = dburl;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		con = startConnection();
	}

	private Connection startConnection() {
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver", true, getClass().getClassLoader()).newInstance();
			} catch (InstantiationException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
			}
			return DriverManager.getConnection(dburl, dbUsername, dbPassword);
		} catch (ClassNotFoundException | SQLException cnfe) {
			throw new RuntimeException(cnfe.toString(), cnfe.getCause());
		}
	}

	public void shutdown() {
		try {
			DriverManager.getConnection(dburl + "shutdown=true");
		} catch (SQLException sqle) {
			Logger.getLogger(DatabaseManager.class.getName()).info(sqle.getMessage());
		}
	}

	public Connection getCon() {
		return con;
	}

}
