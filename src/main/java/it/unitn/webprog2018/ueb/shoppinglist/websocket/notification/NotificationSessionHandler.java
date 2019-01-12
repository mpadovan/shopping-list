/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.notification;

import com.google.gson.Gson;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Notification;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.SessionHandler;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 * Implements access to persistence layer and active sessions for the
 * notification module.
 *
 * @author Giulia Carocari
 */
public class NotificationSessionHandler extends SessionHandler {
	private static final Gson GSON = new Gson();
	
	protected List<Notification> getNotifications(Integer userId) throws DaoException {
		return getDaoFactory().getNotificationDAO().getUnreadNotifications(userId);
	}

	protected Integer getUnreadCount(Integer userId) throws DaoException {
		return getDaoFactory().getNotificationDAO().getNotificationCount(userId);
	}

	protected List<Notification> getNextNotifications(Timestamp nextRequest) throws DaoException {
		return getDaoFactory().getNotificationDAO().getNextNotifications(nextRequest);
	}
	/**
	 * Checks if a user is connected on the notification WebSocket and if so sends him an update on his unread notification count
	 * @param userId 
	 */
	protected void notifyUser(Integer userId) {
		Session session = getSession(userId);
		try {
			NotificationWebSocketMessage msg = new NotificationWebSocketMessage();
			msg.setOperation(NotificationWebSocketMessage.Operation.SEND_UNREAD_COUNT);
			msg.setPayload(getUnreadCount(userId));
			session.getBasicRemote().sendText(GSON.toJson(msg));
		} catch (DaoException | IOException ex) {
			Logger.getLogger(NotificationWebSocketServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
