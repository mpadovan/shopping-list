/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Giulia Carocari
 */
@ApplicationScoped
@ServerEndpoint("/restricted/messages/{userId}")
public class ChatWebSocketServer {
	
	@Inject
	ChatSessionHandler chatSessionHandler;

	@OnOpen
	public void open(Session session, @PathParam("userId") Integer userId) {
		chatSessionHandler.subscribe(userId, session);
		// TODO send list of unread count messages
	}

	@OnClose
	public void close(Session session, @PathParam("userId") Integer userId) {
		chatSessionHandler.unsubscribe(Integer.SIZE);
	}

	@OnError
	public void onError(Throwable error) {
		error.printStackTrace();
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		
	}
}
