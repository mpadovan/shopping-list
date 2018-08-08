/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.listeners;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener that initializes the timer that cleans the 
 * database from unused expired authentication tokens. The timer then becomes
 * an attribute of the servlet context and keeps executing
 *
 * @author Giulia Carocari
 */
public class DBCleaningListener implements ServletContextListener {

	private static final long CLEANING_RATE = 1000 * 60 * 60 * 24;	// 24 hours in milliseconds
	private TokenDAO tokenDAO;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		DAOFactory factory = (DAOFactory) sc.getAttribute("daoFactory");
		tokenDAO = factory.getTokenDAO();
		Timer expiredTokenScheduler = new Timer();
		expiredTokenScheduler.scheduleAtFixedRate(new CleanDBTask(), CLEANING_RATE, CLEANING_RATE);

		sc.setAttribute("expiredTokenScheduler", expiredTokenScheduler);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Timer timer = (Timer) sce.getServletContext().getAttribute("expiredTokenScheduler");
		timer.cancel();
	}

	private class CleanDBTask extends TimerTask {

		@Override
		public void run() {
			System.out.println("Cleaning DB... ... ...");
			tokenDAO.removeExpiredTokens();
		}
	}

}
