/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.notification;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Notification;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author giulia
 */
public class NotificationTimer extends ScheduledThreadPoolExecutor {
	private static DAOFactory dAOFactory;
	private static final int POLLING_RATE = 60 * 1000; // every minute
	private static NotificationSessionHandler notificationSessionHandler;

	public NotificationTimer(int i) {
		super(i);
		// TODO schedule DatabaseQueryTask every minute
	}
	
	
	public static void setNotificationSessionHandler(NotificationSessionHandler notificationSessionHandler) {
		NotificationTimer.notificationSessionHandler = notificationSessionHandler;
	}
	
	
	public static void setdAOFactory(DAOFactory dAOFactory) {
		NotificationTimer.dAOFactory = dAOFactory;
	}
	
	/**
	 * Task that retrieves notifications from the database for them to be
	 * scheduled for sending.
	 */
	private class DatabaseQueryTask implements Runnable {

		@Override
		public void run() {
			List<Notification> notifications = dAOFactory.getNotificationDAO().getNextNotifications(new Timestamp(System.currentTimeMillis() + POLLING_RATE));
			for (Notification n : notifications) {
			}
		}

	}

	/**
	 * Task that performs the notification to the user via email and, if he is
	 * connected at time of execution, via the web-site.
	 */
	private class NotificationTask implements Runnable {

		@Override
		public void run() {
			// TODO send notification via email and on the web portal.
		}

	}

}
