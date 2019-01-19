/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.listeners;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener that initializes the timer that cleans the application
 * from unused expired authentication tokens. The timer then becomes an
 * attribute of the servlet context and keeps executing
 *
 * @author Giulia Carocari
 */
public class TokenListener implements ServletContextListener {

	private static final long CLEANING_RATE = 1000 * 60 * 60 * 24;	// 24 hours in milliseconds
	private TokenDAO tokenDAO;

	/**
	 * Initializes the scheduled thread pool executor that cleans the database from the expired new account tokens.
	 * @param sce 
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		DAOFactory factory = (DAOFactory) sc.getAttribute("daoFactory");
		tokenDAO = factory.getTokenDAO();
		ScheduledThreadPoolExecutor expiredTokenScheduler = new ScheduledThreadPoolExecutor(2);
		expiredTokenScheduler.scheduleAtFixedRate(new CleanDBTask(), CLEANING_RATE, CLEANING_RATE, TimeUnit.MILLISECONDS);

		sc.setAttribute("expiredTokenScheduler", expiredTokenScheduler);
	}

	/**
	 * Shuts down the scheduled thread pool executor to prevent memory leaks.
	 * @param sce 
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ScheduledThreadPoolExecutor timer = (ScheduledThreadPoolExecutor) sce.getServletContext().getAttribute("expiredTokenScheduler");
		// removes all scheduled tasks
		timer.purge();
		// stops the timer's threads
		timer.shutdownNow();
	}

	/**
	 * Task that removes the expired new account tokens from the persistence layer.
	 */
	private class CleanDBTask implements Runnable {

		public CleanDBTask() {
		}

		@Override
		public void run() {
			System.out.println("Cleaning DB... ... ...");
			tokenDAO.removeExpiredTokens();
		}
	}
}
