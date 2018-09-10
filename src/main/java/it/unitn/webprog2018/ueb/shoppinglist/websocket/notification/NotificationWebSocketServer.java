/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.notification;

import com.google.gson.Gson;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Websocket server endpoint to notify a user when he has a new notification
 * about expiring products. Messages are sent in Json format.
 *
 * @author Giulia Carocari
 */
@ApplicationScoped
@ServerEndpoint("/restricted/notifications/{userId}")
public class NotificationWebSocketServer {

	private static final Gson GSON = new Gson();
	
	@Inject
	private NotificationSessionHandler notificationSessionHandler;
	/*
	public static void setNotificationSessionHandler(NotificationSessionHandler notificationSessionHandler) {
		NotificationWebSocketServer.notificationSessionHandler = notificationSessionHandler;
	}
	*/
	/**
	 * Method executed at the opening of the connection. Adds the user's session
	 * to session handler and sends the first update about the notification
	 * count
	 *
	 * @param session
	 * @param userId
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("userId") Integer userId) {
		notificationSessionHandler.subscribe(userId, session);
		notificationSessionHandler.notifyUser(userId);
	}

	@OnError
	public void onError(Session session, Throwable t, @PathParam("userId") Integer userId) {
		try {
			session.close(new CloseReason(new CloseReason.CloseCode() {
				@Override
				public int getCode() {
					return 500;
				}
			}, t.getMessage()));
		} catch (IOException ex) {
			Logger.getLogger(NotificationWebSocketServer.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (session.isOpen()) {
			notificationSessionHandler.unsubscribe(userId);
		}
	}

	@OnClose
	public void onClose(Session session, @PathParam("userId") Integer userId) {
		if (session.isOpen()) {
			notificationSessionHandler.unsubscribe(userId);
		}
	}

	@OnMessage
	public void onMessage(String message, Session session, @PathParam("userId") Integer userId) {
		NotificationWebSocketMessage incoming = GSON.fromJson(message, NotificationWebSocketMessage.class);
		if (incoming.getOperation().equals(NotificationWebSocketMessage.Operation.FETCH_NOTIFICATIONS)) {
			NotificationWebSocketMessage msg = new NotificationWebSocketMessage();
			msg.setOperation(NotificationWebSocketMessage.Operation.SEND_NOTIFICATIONS);
			try {
				msg.setPayload(notificationSessionHandler.getNotifications(userId));
				session.getBasicRemote().sendText(GSON.toJson(msg));
			} catch (DaoException | IOException ex) {
				Logger.getLogger(NotificationWebSocketServer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
