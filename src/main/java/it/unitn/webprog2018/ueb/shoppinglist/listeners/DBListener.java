package it.unitn.webprog2018.ueb.shoppinglist.listeners;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import it.unitn.webprog2018.ueb.shoppinglist.dao.mysql.DAOFactoryImpl;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.mysql.DatabaseManager;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Network;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.SessionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener. Handles connections to the database.
 *
 * @author giuliapeserico
 */
public class DBListener implements ServletContextListener {

	/**
	 * Connects to the database and creates DaoFactory.
	 * @param sce 
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String dburl;
		dburl = "jdbc:mysql://" + Network.getDatabaseAddress()
				+ ":3306/shoppinglistdb?serverTimezone=WET&allowMultiQueries=true";
		String dbUsername = "root";
		String dbPassword = "root";

		DatabaseManager dbManager = new DatabaseManager(dburl, dbUsername, dbPassword);
		DAOFactory factory = new DAOFactoryImpl(dbManager.getCon());
		sce.getServletContext().setAttribute("daoFactory", factory);
		SessionHandler.setDaoFactory(factory);
		// DAOFactory factory = new DAOFactoryImpl();
		// sce.getServletContext().setAttribute("daoFactory", factory);
	}

	/**
	 * Shuts down the connection to the database.
	 * @param sce 
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			AbandonedConnectionCleanupThread.checkedShutdown();
			((DatabaseManager) sce.getServletContext().getAttribute("dbManager")).shutdown();
		} catch (NullPointerException e) {
			String msg = "DatabaseManager has already destroyed. Skipping...";
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msg);
		}
	}

}
