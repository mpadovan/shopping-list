/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;

/**
 *
 * @author Giulia Carocari
 */
public abstract class SessionHandler {
	/**
	 * DaoFactory created in the DBListener.
	 */
	private static DAOFactory daoFactory;

	public static DAOFactory getDaoFactory() {
		return daoFactory;
	}

	public static void setDaoFactory(DAOFactory daoFactory) {
		SessionHandler.daoFactory = daoFactory;
	}
	
	/**
	 * Maps users to their webSocket sessions.
	*/
	private final Map<Integer, Session> sessions = new HashMap<>();
	
	/**
	 * Checks if a user has a current session
	 * @param userId User you want to check the connection of
	 * @return true if there is a current session going for this user
	*/
	public boolean isConnected(Integer userId) {
		return sessions.containsKey(userId);
	}
	
	public Session getSession(Integer userId) {
		return sessions.get(userId);
	}
	
	public void subscribe(Integer userId, Session session) {
		sessions.put(userId, session);
	}
	
	public void unsubscribe(Integer userId) {
		sessions.remove(userId);
	}
}
