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
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giulia Carocari
 */
public class NotificationTimer extends ScheduledThreadPoolExecutor {
	private static final int POLLING_RATE = 60 * 1000; // every minute
	private static NotificationSessionHandler notificationSessionHandler;

	public NotificationTimer(int i) {
		super(i);
		this.scheduleAtFixedRate(new DatabaseQueryTask(), 0, POLLING_RATE, TimeUnit.MILLISECONDS);
	}
	
	
	public static void setNotificationSessionHandler(NotificationSessionHandler notificationSessionHandler) {
		NotificationTimer.notificationSessionHandler = notificationSessionHandler;
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
					NotificationTimer.this.schedule(new NotificationTask(n), n.getTime().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
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
			// If it fails, deal with it.
			EmailSender.send(notification.getUser().getEmail(), "Controlla la tua dispensa, potresti aver finito " + productName,
					"Secondo i nostri calcoli a breve potresti aver bisogno del prodotto " + productName + ", perché non lo inserisci nella lista " +
							notification.getList().getName() + "?\n"
									+ "Clicca qui per connetterti subito al portale:"
									+ "http://localhost:8080/ShoppingList/HomePageLogin/" + notification.getUser().getId() + "?list=" +
									+ notification.getList().getId());
			if (notificationSessionHandler.isConnected(notification.getUser().getId())) {
				notificationSessionHandler.notifyUser(notification.getUser().getId());
			} // else DO NOTHING, WAIT FOR THE USER TO CONNECT
		}

	}

}
