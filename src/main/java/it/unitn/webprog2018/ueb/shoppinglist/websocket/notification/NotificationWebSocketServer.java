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
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Giulia Carocari
 */
@ServerEndpoint("/restricted/notifications/{userId}")
public class NotificationWebSocketServer {
	
	private static NotificationSessionHandler notificationSessionHandler;
	
	public static void setNotificationSessionHandler(NotificationSessionHandler notificationSessionHandler) {
		NotificationWebSocketServer.notificationSessionHandler = notificationSessionHandler;
	}
	
	@OnOpen
	public void onOpen() {
	}

	@OnError
	public void onError(Throwable t) {
	}

	@OnClose
	public void onClose() {
	}

	@OnMessage
	public void onMessage() {
	}
	
}
