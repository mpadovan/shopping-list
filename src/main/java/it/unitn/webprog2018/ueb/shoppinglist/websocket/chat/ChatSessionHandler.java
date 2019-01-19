/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.chat;

import com.google.gson.Gson;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Message;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.websocket.SessionHandler;
import it.unitn.webprog2018.ueb.shoppinglist.ws.ListWebService;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 * Session handler for sessions opened with the
 * {@link it.unitn.webprog2018.ueb.shoppinglist.websocket.chat.ChatWebSocketServer}.
 * It is responsible for retrieving chats, sending messages and notifying other
 * connected users.
 *
 * @author Giulia Carocari
 */
@ApplicationScoped
public class ChatSessionHandler extends SessionHandler {

	// Used for serialization and desearlization
	private static final Gson GSON = new Gson();

	/**
	 * Adds a the message to the chat of a list.
	 *
	 * @param message Message to be added
	 * @return	true if the operation was successful, false otherwise
	 * @throws DaoException if persisting the message fails in some ways
	 */
	public boolean persistMessage(Message message) throws DaoException {
		if (checkViewPermission(message.getList().getId(), message.getSender().getId())) {
			getDaoFactory().getMessageDAO().addMessage(message);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retrieves the most recent messages of the chat of a list.
	 *
	 * @param userId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 * @param listId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.List}
	 * @return	A list of the messages in the chat
	 * @throws DaoException if retrieving fails in some ways
	 */
	public List<Message> getMessages(Integer userId, Integer listId) throws DaoException {
		if (checkViewPermission(listId, userId)) {
			User user = new User();
			user.setId(userId);
			it.unitn.webprog2018.ueb.shoppinglist.entities.List list = new it.unitn.webprog2018.ueb.shoppinglist.entities.List();
			list.setId(listId);
			return getDaoFactory().getMessageDAO().getLastMessages(list, user);
		} else {
			return null;
		}
	}

	/**
	 * Retrieves the number of unread messages for a user.
	 *
	 * @param userId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 * @return	A list of chat - count associations represented as a
	 * ChatWebsocketUnreadCount
	 * @throws DaoException if retrieving fails in some ways
	 */
	public List<ChatWebSocketUnreadCount> getUnreadCount(Integer userId) throws DaoException {
		Map<Integer, Integer> map = getDaoFactory().getMessageDAO().getUnreadCount(userId);
		List<ChatWebSocketUnreadCount> ret = new LinkedList<>();
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			ret.add(new ChatWebSocketUnreadCount(entry.getKey(), entry.getValue()));
		}
		return ret;
	}

	/**
	 * Sends an "unread count" notifications to users in the list chat currently
	 * connected to the web socket server. The notifications run in a distinct
	 * thread to improve performance.
	 *
	 * @param senderId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.User}
	 * @param listId Unique identifier of the
	 * {@link it.unitn.webprog2018.ueb.shoppinglist.entities.List}
	 */
	void notifyNewMessage(Integer senderId, Integer listId) {
		if (checkViewPermission(listId, senderId)) {
			NotificationThread notificationThread = new NotificationThread(senderId, listId);
			notificationThread.start();
		}
	}

// -------------------------------------------------------------------------- //
////////////////////// PRIVATE CLASSES AND METHODS /////////////////////////////
// -------------------------------------------------------------------------- //
	/**
	 * Thread that notifies connected users of the new incoming message. A
	 * separated thread is used to allow the session handler to accept new
	 * incoming messages in case of slow connections or errors.
	 */
	private class NotificationThread extends Thread {

		private final Integer senderId;
		private final Integer listId;

		public NotificationThread(Integer senderId, Integer listId) {
			this.senderId = senderId;
			this.listId = listId;
		}

		/**
		 * Retrieves all users connected to a list and notifies them of the new
		 * incoming message. Formally, it sends them their unread message count,
		 * including the new incoming message.
		 */
		@Override
		public void run() {
			try {
				List<Integer> sharedListUsers = getDaoFactory().getListDAO().getConnectedUsersIds(listId);
				for (Integer userId : sharedListUsers) {
					if (!Objects.equals(userId, senderId) && isConnected(userId)) {
						ChatWebSocketMessage msg = new ChatWebSocketMessage();
						msg.setOperation(ChatWebSocketMessage.Operation.SEND_UNREAD_COUNT);
						msg.setPayload(getUnreadCount(userId));
						getSession(userId).getBasicRemote().sendText(GSON.toJson(msg, msg.getClass()));
					}
				}
			} catch (DaoException | IOException ex) {
				Logger.getLogger(ChatSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}

	/**
	 * Checks if the user can access the chat
	 *
	 * @param listId id of the list to be checked
	 * @param userId id of the user to be checked
	 * @return true if the permission is granted, false otherwise
	 */
	private boolean checkViewPermission(int listId, int userId) {
		try {
			ListDAO listDAO = SessionHandler.getDaoFactory().getListDAO();
			it.unitn.webprog2018.ueb.shoppinglist.entities.List list = listDAO.getList(listId);

			if (list.getOwner().getId().equals(userId)
					|| listDAO.hasViewPermission(listId, userId)) {
				return true;
			}
		} catch (DaoException ex) {
			Logger.getLogger(ListWebService.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

}
