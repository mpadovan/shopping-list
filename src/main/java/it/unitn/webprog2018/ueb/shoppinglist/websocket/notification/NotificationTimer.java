/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.notification;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Notification;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.utils.EmailSender;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Giulia Carocari
 */
@ApplicationScoped
@Startup
public class NotificationTimer {
	private static final int POLLING_RATE = 60 * 1000; // every minute
	private ScheduledThreadPoolExecutor executor;
	
	@Inject
	private NotificationSessionHandler notificationSessionHandler;
	
	public NotificationTimer() {
		executor = new ScheduledThreadPoolExecutor(4);
		executor.scheduleAtFixedRate(new DatabaseQueryTask(), POLLING_RATE, POLLING_RATE, TimeUnit.MILLISECONDS);
		System.out.println("Notification timer ok");
	}
	
	/**
	 * Task that retrieves notifications from the database for them to be
	 * scheduled for sending.
	 */
	private class DatabaseQueryTask implements Runnable {

		@Override
		public void run() {
			try {
				List<Notification> notifications = notificationSessionHandler.getNextNotifications(new Timestamp(System.currentTimeMillis() + POLLING_RATE));
				for (Notification n : notifications) {
					executor.schedule(new NotificationTask(n), n.getTime().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
				}
			} catch (DaoException ex) { // if DAO fails then we try again on the next poll
				Logger.getLogger(NotificationTimer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	/**
	 * Task that performs the notification to the user via email and, if he is
	 * connected at time of execution, via the web-site.
	 */
	private class NotificationTask implements Runnable {
		private final Notification notification;

		public NotificationTask(Notification notification) {
			this.notification = notification;
		}
		
		@Override
		public void run() {
			String productName =(notification.getProduct() instanceof Product ? ((Product)notification.getProduct()).getName() : ((PublicProduct)notification.getProduct()).getName());
			try {
				// If it fails, deal with it.
				if (!EmailSender.send(notification.getUser().getEmail(), "Controlla la tua dispensa, potresti aver finito " + productName,
						"Secondo i nostri calcoli a breve potresti aver bisogno del prodotto " + productName + ", perché non lo inserisci nella lista " +
								notification.getList().getName() + "?\n"
										+ "Clicca qui per connetterti subito al portale:"
										+ "http://" + InetAddress.getLocalHost().getHostName()
								+ ":8080/ShoppingList/restricted/HomePageLogin/" + notification.getUser().getId() + "?list=" +
								+ notification.getList().getId())) {
					System.err.println("Could not reach email address");
				}
			} catch (UnknownHostException ex) {
				Logger.getLogger(NotificationTimer.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (notificationSessionHandler.isConnected(notification.getUser().getId())) {
				notificationSessionHandler.notifyUser(notification.getUser().getId());
			} // else DO NOTHING, WAIT FOR THE USER TO CONNECT
		}

	}
	
	
	@PreDestroy
	void destroy() {
		executor.purge();
		executor.shutdownNow();
	}
}
