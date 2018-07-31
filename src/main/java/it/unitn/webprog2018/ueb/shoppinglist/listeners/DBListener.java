package it.unitn.webprog2018.ueb.shoppinglist.listeners;

import it.unitn.webprog2018.ueb.shoppinglist.dao.dummy.DAOFactoryImpl;
//import it.unitn.webprog2018.ueb.shoppinglist.dao.mysql.DAOFactoryImpl;
//import it.unitn.webprog2018.ueb.shoppinglist.dao.mysql.DatabaseManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author giuliapeserico
 */
public class DBListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		String dburl = "jdbc:mysql://localhost:3306/ShoppingListGp";
//		String dbUsername = "root";
//		String dbPassword = "root";
//
//		DatabaseManager dbManager = new DatabaseManager(dburl, dbUsername, dbPassword);
//		sce.getServletContext().setAttribute("daoFactory", new DAOFactoryImpl(dbManager.getCon()));
		sce.getServletContext().setAttribute("daoFactory", new DAOFactoryImpl());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
//		try{
//			((DatabaseManager)sce.getServletContext().getAttribute("dbManager")).shutdown();
//		} catch (NullPointerException e){
//			String msg = "DatabaseManager has already destroyed. Skipping...";
//			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msg);
//		}
	}

}
