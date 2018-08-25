/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.notification;

import it.unitn.webprog2018.ueb.shoppinglist.entities.Notification;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.SessionHandler;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implements access to persistence layer and active sessions for the notification module.
 *
 * @author Giulia Carocari
 */
public class NotificationSessionHandler extends SessionHandler {
	
	List<Notification> getNotifications(Integer userId) {
		return getDaoFactory().getNotificationDAO().getUnreadNotifications(userId);
	}
	
	Integer getUnreadCount(Integer userId) {
		return getDaoFactory().getNotificationDAO().getNotificationCount(userId);
	}
	
	List<Notification> getNextNotifications(Timestamp nextRequest) {
		return getDaoFactory().getNotificationDAO().getNextNotifications(nextRequest);
	}
}
