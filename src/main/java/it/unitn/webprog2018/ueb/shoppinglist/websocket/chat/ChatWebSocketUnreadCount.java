/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.chat;

/**
 *
 * @author Giulia Carocari
 */
public class ChatWebSocketUnreadCount {
	private Integer listId;
	private Integer unreadCount;

	public ChatWebSocketUnreadCount(Integer listId, Integer unreadCount) {
		this.listId = listId;
		this.unreadCount = unreadCount;
	}
	
	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public Integer getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
	}
}
