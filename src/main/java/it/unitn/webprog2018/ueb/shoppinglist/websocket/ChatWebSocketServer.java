/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Message;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Giulia Carocari
 */
@ServerEndpoint("/restricted/messages/{userId}")
public class ChatWebSocketServer {

	private static final Gson GSON = new Gson();
	
	private static ChatSessionHandler chatSessionHandler;

	public static void setChatSessionHandler(ChatSessionHandler chatSessionHandler) {
		ChatWebSocketServer.chatSessionHandler = chatSessionHandler;
	}

	public ChatWebSocketServer() {
		System.out.println("ChatServer created");
	}
	
	@OnOpen
	public void open(Session session, @PathParam("userId") Integer userId) throws DaoException {
		chatSessionHandler.subscribe(userId, session);
		ChatWebSocketMessage msg = new ChatWebSocketMessage();
		msg.setOperation(ChatWebSocketMessage.Operation.SEND_UNREAD_COUNT);
		msg.setPayload(chatSessionHandler.getUnreadCount(userId));
		try {
			session.getBasicRemote().sendText(GSON.toJson(msg, ChatWebSocketMessage.class));
		} catch (IOException ex) {
			Logger.getLogger(ChatWebSocketServer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@OnClose
	public void close(Session session, @PathParam("userId") Integer userId) {
		chatSessionHandler.unsubscribe(userId);
	}

	@OnError
	public void onError(Throwable error, @PathParam("userId") Integer userId) {
		Logger.getLogger(ChatWebSocketServer.class
				.getName()).log(Level.SEVERE, null, error);
	}

	@OnMessage
	public void handleMessage(String message, Session session, @PathParam("userId") Integer userId) throws DaoException {
		Gson gson = new Gson();

		try {
			ChatWebSocketMessage entering = gson.fromJson(message, ChatWebSocketMessage.class
			);
			Message msg = null;
			Integer listId = 0;

			switch (entering.getOperation()) {
				case ADD_MESSAGE:
					// msg = gson.fromJson(entering.getPayload(), Message.class);
					if (chatSessionHandler.persistMessage(msg)) {
						listId = msg.getList().getId();
					}
					break;
				case FETCH_CHAT:
					// listId = Integer.parseInt(entering.getPayload());
					break;
				default:
					break;
			}
			String payload = gson.toJson(chatSessionHandler.getMessages(userId, listId));
			ChatWebSocketMessage exiting = new ChatWebSocketMessage();
			exiting.setOperation(ChatWebSocketMessage.Operation.SEND_CHAT);
			exiting.setPayload(payload);
			session.getBasicRemote().sendText(gson.toJson(exiting));
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();

		} catch (IOException ex) {
			Logger.getLogger(ChatWebSocketServer.class
					.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
