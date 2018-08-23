/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket;

import com.google.gson.Gson;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Message;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giulia Carocari
 */
public class ChatSessionHandler extends SessionHandler {
	
	private static final Gson GSON = new Gson();
	
	public boolean persistMessage(Message message) throws DaoException {
		getDaoFactory().getMessageDAO().addMessage(message);
		return true;
	}

	public List<Message> getMessages(Integer userId, Integer listId) throws DaoException {
		User user = new User();
		user.setId(userId);
		it.unitn.webprog2018.ueb.shoppinglist.entities.List list = new it.unitn.webprog2018.ueb.shoppinglist.entities.List();
		list.setId(listId);
		return getDaoFactory().getMessageDAO().getLastMessages(list, user);
	}
	
	public List<ChatWebSocketUnreadCount> getUnreadCount(Integer userId) throws DaoException {
		Map<Integer,Integer> map = getDaoFactory().getMessageDAO().getUnreadCount(userId);
		List<ChatWebSocketUnreadCount> ret = new LinkedList<>();
		for (Entry<Integer,Integer> entry : map.entrySet()) {
			ret.add(new ChatWebSocketUnreadCount(entry.getKey(), entry.getValue()));
		}
		return ret;
	}

	void notifyNewMessage(Integer senderId, Integer listId) {
		NotificationThread notificationThread = new NotificationThread(senderId, listId);
		notificationThread.start();
	}
	
	private class NotificationThread extends Thread {
		private final Integer senderId;
		private final Integer listId;
		
		public NotificationThread(Integer senderId, Integer listId) {
			this.senderId = senderId;
			this.listId = listId;
		}
		
		@Override
		public void run() {
			try {
				List<Integer> sharedListUsers = getDaoFactory().getListDAO().getConnectedUsers(listId);
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

}
