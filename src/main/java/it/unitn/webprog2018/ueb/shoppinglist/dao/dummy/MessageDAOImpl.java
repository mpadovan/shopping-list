/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.MessageDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Message;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giulia
 */
public class MessageDAOImpl implements MessageDAO {

	private java.util.List<Message> chat = new LinkedList<>();
	private Map<AbstractMap.SimpleEntry<Integer,Integer>, Integer> unreadCount = new HashMap<>();
	private DAOFactoryImpl factory;

	public MessageDAOImpl(DAOFactoryImpl factory) {
		try {
			this.factory = factory;
			Message m1 = new Message();
			m1.setId(1);
			m1.setList(factory.getListDAO().getList(1));
			m1.setSendTime(new Timestamp(System.currentTimeMillis()));
			m1.setSender(factory.getUserDAO().getById(1));
			m1.setText("Ciao Luigi, sto andando a fare la spesa, ti manca qualcosa?");
			
			Message m2 = new Message();
			m2.setId(2);
			m2.setList(factory.getListDAO().getList(1));
			m2.setSendTime(new Timestamp(System.currentTimeMillis()));
			m2.setSender(factory.getUserDAO().getById(2));
			m2.setText("Ciao Mario, sì grazie, prendi una motosega. Appena ci vediamo ti do i soldi");
			
			Message m3 = new Message();
			m3.setId(3);
			m3.setList(factory.getListDAO().getList(1));
			m3.setSendTime(new Timestamp(System.currentTimeMillis()));
			m3.setSender(factory.getUserDAO().getById(1));
			m3.setText("Sarà fatto, ci vediamo a casa ;)");
			
			chat.add(m1);
			chat.add(m2);
			chat.add(m3);
			unreadCount.put(new AbstractMap.SimpleEntry<>(1,1), 0);
			unreadCount.put(new AbstractMap.SimpleEntry<>(1,2), 3);
			unreadCount.put(new AbstractMap.SimpleEntry<>(2,1), 1);
		} catch (DaoException ex) {
			Logger.getLogger(MessageDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public java.util.List<Message> getLastMessages(List list, User user) throws DaoException {
		Map<Integer, Integer> unread = getUnreadCount(user.getId());
		Integer count = unread.get(list.getId());
		java.util.List<Message> sublist = chat.subList(Math.max(0, chat.size() - 30), chat.size());
		for (Message m : sublist) {
			m.setRead(true);
		}
		/*
		for (Message m : chat.subList(Math.max(30 - count-1, 0), 30-1)) {
			m.setRead(false);
		}
		*/
		unreadCount.put(new AbstractMap.SimpleEntry<>(user.getId(),list.getId()), 0);
		return sublist;
	}

	@Override
	public Boolean addMessage(Message message) throws DaoException {
		message.setId(chat.size() + 1);
		message.setSender(factory.getUserDAO().getById(message.getSender().getId()));
		chat.add(message);
		if (message.getSender().getId().equals(1)) {
			unreadCount.put(new AbstractMap.SimpleEntry<>(2,1), unreadCount.get(new AbstractMap.SimpleEntry<>(2,1))+1);
			return true;
		} else if(message.getSender().getId().equals(2)) {
			unreadCount.put(new AbstractMap.SimpleEntry<>(1,1), unreadCount.get(new AbstractMap.SimpleEntry<>(1,1))+1);
			return true;
		}
		throw new DaoException("Srry can't");
	}

	@Override
	public Map<Integer, Integer> getUnreadCount(Integer userId) throws DaoException {
		Map<Integer, Integer> ret = new HashMap<>();
		for (Entry<AbstractMap.SimpleEntry<Integer,Integer>, Integer> e : unreadCount.entrySet()) {
			if (e.getKey().getKey().equals(userId)) {
				ret.put(e.getKey().getValue(), e.getValue());
			}
		}
		return ret;
	}

}
