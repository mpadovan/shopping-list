package it.unitn.webprog2018.ueb.shoppinglist.listeners;

import it.unitn.webprog2018.ueb.shoppinglist.dao.mysql.DAOFactoryImpl;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.mysql.DatabaseManager;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.SessionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
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
		String dburl = "jdbc:mysql://localhost:3306/shoppinglistdb?serverTimezone=WET&allowMultiQueries=true";
		String dbUsername = "root";
		String dbPassword = "root";

		DatabaseManager dbManager = new DatabaseManager(dburl, dbUsername, dbPassword);
		DAOFactory factory = new DAOFactoryImpl(dbManager.getCon());
		sce.getServletContext().setAttribute("daoFactory", factory);
		SessionHandler.setDaoFactory(factory);
		// DAOFactory factory = new DAOFactoryImpl();
		// sce.getServletContext().setAttribute("daoFactory", factory);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try{
			((DatabaseManager)sce.getServletContext().getAttribute("dbManager")).shutdown();
		} catch (NullPointerException e){
			String msg = "DatabaseManager has already destroyed. Skipping...";
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msg);
		}
	}

}
