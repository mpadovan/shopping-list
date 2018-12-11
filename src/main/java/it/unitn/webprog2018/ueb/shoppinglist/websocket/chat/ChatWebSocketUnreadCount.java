/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.chat;

/**
 * Message type used to notify a client of unread messages in a chat.
 *
 * @author Giulia Carocari
 */
public class ChatWebSocketUnreadCount {
	private Integer listId;
	private Integer unreadCount;

	/**
	 * Constructor that sets the list and the unread message count.
	 * 
	 * @param listId
	 * @param unreadCount 
	 */
	public ChatWebSocketUnreadCount(Integer listId, Integer unreadCount) {
		this.listId = listId;
		this.unreadCount = unreadCount;
	}
	
	/**
	 * Getter for the list id.
	 * @return list id
	 */
	public Integer getListId() {
		return listId;
	}

	/**
	 * Setter for the list id
	 * @param listId list id
	 */
	public void setListId(Integer listId) {
		this.listId = listId;
	}

	/**
	 * Getter for the unread count
	 * @return unread count
	 */
	public Integer getUnreadCount() {
		return unreadCount;
	}

	/**
	 * Setter for the unread count
	 * @param unreadCount unread count
	 */
	public void setUnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
	}
}
