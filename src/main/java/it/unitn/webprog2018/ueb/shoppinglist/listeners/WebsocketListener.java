/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.listeners;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.chat.ChatSessionHandler;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.chat.ChatWebSocketServer;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.SessionHandler;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.notification.NotificationSessionHandler;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.notification.NotificationTimer;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.notification.NotificationWebSocketServer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Giulia Carocari
 */
public class WebsocketListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DAOFactory factory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
		SessionHandler.setDaoFactory(factory);
		NotificationSessionHandler notificationSessionHandler = new NotificationSessionHandler();
		NotificationWebSocketServer.setNotificationSessionHandler(notificationSessionHandler);
		NotificationTimer notificationTimer = new NotificationTimer(1);
		NotificationTimer.setNotificationSessionHandler(notificationSessionHandler);
		sce.getServletContext().setAttribute("notificationTimer", notificationTimer);
		ChatWebSocketServer.setChatSessionHandler(new ChatSessionHandler());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		((NotificationTimer)sce.getServletContext().getAttribute("notificationTimer")).shutdownNow();
	}
	
}
