/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.notification;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Websocket server endpoint to notify a user when he has a new notification about expiring products.
 * Messages are sent in Json format.
 *
 * @author Giulia Carocari
 */
@ServerEndpoint("/restricted/notifications/{userId}")
public class NotificationWebSocketServer {
	
	private static NotificationSessionHandler notificationSessionHandler;
	
	public static void setNotificationSessionHandler(NotificationSessionHandler notificationSessionHandler) {
		NotificationWebSocketServer.notificationSessionHandler = notificationSessionHandler;
	}
	
	/**
	 * Method executed at the opening of the connection.
	 * Adds the user's session to session handler and sends the first update about the notification count
	 *
	 * @param session
	 * @param userId
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("userId") Integer userId) {
		notificationSessionHandler.subscribe(userId, session);
		// TODO send uread notification count
	}

	@OnError
	public void onError(Throwable t) {
	}

	@OnClose
	public void onClose(Session session, @PathParam("userId") Integer userId) {
		notificationSessionHandler.unsubscribe(userId);
	}

	@OnMessage
	public void onMessage(String message, Session session, @PathParam("userId") Integer userId) {
		// TODO send unread notifications
	}
	
}
