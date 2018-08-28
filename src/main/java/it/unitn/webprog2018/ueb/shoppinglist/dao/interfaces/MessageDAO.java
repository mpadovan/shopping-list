/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Message;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.util.Map;

/**
 *
 * @author giulia
 */
public interface MessageDAO {
	/**
	 * Returns the last 30 messages for the list to which the user has access.
	 * It sets the "read" attribute of the message if the message belongs to the x most recent messages of that list 
	 * (where x is the number of unread messages for that list and user)
	 * IMPORTANTEST: It clears the unread count for this user and list.
	 * 
	 * @param list List to which the chat belongs to. Only Id will be set.
	 * @param user User that is making the request
	 * @return a list of the most recent messages in the chat. 
	 * Every field of the message will be set, as for the list, only its id is required, every other field null;
	 * for the user, id, name and last name will be set.
	 * @throws DaoException 
	 */
	public java.util.List<Message> getLastMessages(List list, User user) throws DaoException;
	
	/**
	 * Adds a message to its chat, every necessary field to identify a message must be set inside the message itself,
	 * (i.e. sender-id, list-id and current time).
	 * IMPORTANTEST: increases the unread count for each user joining the chat (aka list) by one.
	 * 
	 * @param message Message to be added to the chat
	 * @return True if the operation was successful.
	 * @throws DaoException 
	 */
	public Boolean addMessage(Message message) throws DaoException;
	
	/**
	 * Method that retrieves the number of unread messages for each chat (aka list) the user has joined or created.
	 * 
	 * @param userId id of the user making the request
	 * @return A map connecting every list to its unread messages count
	 * @throws DaoException 
	 */
	public Map<Integer, Integer> getUnreadCount(Integer userId) throws DaoException;
}
