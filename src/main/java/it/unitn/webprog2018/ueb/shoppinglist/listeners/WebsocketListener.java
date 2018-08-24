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
		ChatWebSocketServer.setChatSessionHandler(new ChatSessionHandler());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
}
