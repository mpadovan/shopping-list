/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Message;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Giulia Carocari
 */
public class ChatSessionHandler extends SessionHandler {
	
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

}
