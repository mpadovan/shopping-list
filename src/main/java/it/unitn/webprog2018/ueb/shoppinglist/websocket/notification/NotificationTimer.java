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
import it.unitn.webprog2018.ueb.shoppinglist.utils.Network;
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
 * Class that handles notifications for products that are regularly inserted
 * into lists. It is responsible for polling the database for expiring
 * notifications and sending them to the user via email (and on the web page -
 * not implemented client side and not tested).
 *
 * @author Giulia Carocari
 */
@ApplicationScoped
@Startup
public class NotificationTimer {

	private static final int POLLING_RATE = 60 * 1000; // every minute
	private final ScheduledThreadPoolExecutor executor;

	@Inject
	private NotificationSessionHandler notificationSessionHandler;

	/**
	 * Creates a new notification timer and schedules the queries to the
	 * database to retrieve expiring notifications.
	 */
	public NotificationTimer() {
		executor = new ScheduledThreadPoolExecutor(4);
		executor.scheduleAtFixedRate(new DatabaseQueryTask(), POLLING_RATE, POLLING_RATE, TimeUnit.MILLISECONDS);
		System.out.println("Notification timer ok");
	}

	/**
	 * Task that retrieves notifications from the database and schedules them
	 * for sending.
	 */
	private class DatabaseQueryTask implements Runnable {

		/**
		 * Queries the database and schedules notidications.
		 */
		@Override
		public void run() {
			try {
				List<Notification> notifications = notificationSessionHandler.getNextNotifications(new Timestamp(System.currentTimeMillis() + POLLING_RATE));
				for (Notification n : notifications) {
					executor.schedule(new NotificationTask(n), ((int) Math.random()) % POLLING_RATE /* Always 0? */, TimeUnit.MILLISECONDS);
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

		/**
		 * Creates a new notification task.
		 *
		 * @param notification Notification to be sent.
		 */
		public NotificationTask(Notification notification) {
			this.notification = notification;
		}

		/**
		 * Sends an email to the user associated with the notification.
		 */
		@Override
		public void run() {
			String productName = (notification.getProduct() instanceof Product
					? ((Product) notification.getProduct()).getName()
					: ((PublicProduct) notification.getProduct()).getName());
			// If it fails, deal with it.
			if (!EmailSender.send(notification.getUser().getEmail(), "Controlla la tua dispensa, potresti aver finito " + productName,
					"Secondo i nostri calcoli a breve potresti aver bisogno del prodotto " + productName + ", perché non lo inserisci nella lista "
					+ notification.getList().getName() + "?\n"
					+ "Clicca qui per connetterti subito al portale:"
					+ "http://" + Network.getServerAddress()
					+ ":8080/ShoppingList/restricted/HomePageLogin/" + notification.getUser().getId() + "?list="
					+ +notification.getList().getId())) {
				System.err.println("Could not reach email address");
			}
			// if (notificationSessionHandler.isConnected(notification.getUser().getId())) {
			//	notificationSessionHandler.notifyUser(notification.getUser().getId());
			// } // else DO NOTHING, WAIT FOR THE USER TO CONNECT
		}

	}

	/**
	 * Method called on instance destruction. Closes the timer threads to
	 * prevent memory leaks.
	 */
	@PreDestroy
	void destroy() {
		executor.purge();
		executor.shutdownNow();
	}
}
