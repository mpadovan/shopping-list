/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.chat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Message;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Web socket server that handles chat connections and messages.
 *
 * @author Giulia Carocari
 */
@ServerEndpoint("/restricted/messages/{userId}")
@ApplicationScoped
public class ChatWebSocketServer {

	private static final Gson GSON = new Gson().newBuilder().create();
	private static final JsonParser GSON_PARSER = new JsonParser();

	@Inject
	private ChatSessionHandler chatSessionHandler;

	/**
	 * At the opening of the connection the unread count of messages is sent to
	 * the user.
	 *
	 * @param session session that was opened by the client
	 * @param userId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 */
	@OnOpen
	public void open(Session session, @PathParam("userId") Integer userId) {
		try {
			chatSessionHandler.subscribe(userId, session);
			ChatWebSocketMessage msg = new ChatWebSocketMessage();
			msg.setOperation(ChatWebSocketMessage.Operation.SEND_UNREAD_COUNT);
			msg.setPayload(chatSessionHandler.getUnreadCount(userId));
			try {
				session.getBasicRemote().sendText(GSON.toJson(msg, ChatWebSocketMessage.class));
			} catch (IOException ex) {
				onError(session, ex, userId);
			}
		} catch (DaoException ex) {
			onError(session, ex, userId);
		}
	}

	/**
	 * Handles the closing of a session.
	 *
	 * @param session session that was closed
	 * @param userId unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User} that owns the
	 * session
	 */
	@OnClose
	public void close(Session session, @PathParam("userId") Integer userId) {
		if (session.isOpen()) {
			chatSessionHandler.unsubscribe(userId);
		}
	}

	/**
	 * Session error handling.
	 *
	 * @param session session that was being processed when the error was raised
	 * @param error	Error that was raised
	 * @param userId unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User} that owns the
	 * session
	 */
	@OnError
	public void onError(Session session, Throwable error, @PathParam("userId") Integer userId) {
		try {
			Logger.getLogger(ChatWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
			session.close(new CloseReason(new CloseReason.CloseCode() {
				@Override
				public int getCode() {
					return 500;
				}
			}, error.getMessage()));
		} catch (IOException ex) {
			Logger.getLogger(ChatWebSocketServer.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (session.isOpen()) {
			chatSessionHandler.unsubscribe(userId);
		}
	}

	/**
	 * Handles incoming messages.
	 *
	 * @param message	Json format of the chat message, includes sender_id,
	 * list_id, send_time and of course message
	 * @param session	Session the message was sent on
	 * @param userId unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User} that owns the
	 * session
	 */
	@OnMessage
	public void handleMessage(String message, Session session, @PathParam("userId") Integer userId) {
		try {
			JsonObject jsonMessage = GSON_PARSER.parse(message).getAsJsonObject();
			Integer operation = jsonMessage.get("operation").getAsInt();
			Message msg;
			Integer listId = 0;

			switch (operation) {
				case 0:
					msg = GSON.fromJson(jsonMessage.get("payload"), Message.class);
					if (msg.getText().length() <= 255 && chatSessionHandler.persistMessage(msg)) {
						listId = msg.getList().getId();
						chatSessionHandler.notifyNewMessage(userId, listId);
					}
					break;
				case 1:
					listId = jsonMessage.get("payload").getAsInt();
					break;
				default:
					break;
			}
			ChatWebSocketMessage exiting = new ChatWebSocketMessage();
			exiting.setOperation(ChatWebSocketMessage.Operation.SEND_CHAT);

			exiting.setPayload(chatSessionHandler.getMessages(userId, listId));
			session.getBasicRemote().sendText(GSON.toJson(exiting));
		} catch (DaoException | IOException | JsonSyntaxException ex) {
			onError(session, ex, userId);
		}
	}
}
