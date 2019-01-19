/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.Session;

/**
 * Abstract class that implements the common methods for
 * {@link it.unitn.webprog2018.ueb.shoppinglist.websocket.chat.ChatSessionHandler}
 * and
 * {@link it.unitn.webprog2018.ueb.shoppinglist.websocket.notification.NotificationSessionHandler}.
 * It handles DAO access and session management.
 *
 * @author Giulia Carocari
 */
public abstract class SessionHandler {

	// Maps the user id to the corresponding websocket session
	private final Map<Integer, Session> sessions = new HashMap<>();

	private static DAOFactory daoFactory;

	/**
	 * Get the DAOFactory of the application used by the SessionHandler
	 *
	 * @return DAOFactory to be used to access the database
	 */
	public static DAOFactory getDaoFactory() {
		return daoFactory;
	}

	/**
	 * Set the DAOFactory of the application to be used by the SessionHandler.
	 * Should be set once and for all at application startup (in a
	 * ServletContextListener).
	 *
	 * @param daoFactory DAOFactory to be used to access the database.
	 */
	public static void setDaoFactory(DAOFactory daoFactory) {
		SessionHandler.daoFactory = daoFactory;
	}

	/**
	 * Checks if a user has a current session.
	 *
	 * @param userId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 * @return true if there is a current session going for this user
	 */
	public boolean isConnected(Integer userId) {
		return sessions.containsKey(userId);
	}

	/**
	 * Retrieves the current session of the user.
	 *
	 * @param userId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 * @return The session associated with the user
	 */
	public Session getSession(Integer userId) {
		return sessions.get(userId);
	}

	/**
	 * Adds a session to the ones managed by the session handler.
	 *
	 * @param userId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 * @param session Web socket session of the user
	 */
	public void subscribe(Integer userId, Session session) {
		sessions.put(userId, session);
	}

	/**
	 * Removes the session of the user from the ones managed by the session
	 * handler.
	 *
	 * @param userId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 */
	public void unsubscribe(Integer userId) {
		sessions.remove(userId);
	}
}
