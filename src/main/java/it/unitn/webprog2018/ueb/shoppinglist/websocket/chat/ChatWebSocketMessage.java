/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.chat;

import com.google.gson.annotations.SerializedName;

/**
 * Class that contains the message sent by the {@link it.unitn.webprog2018.ueb.shoppinglist.websocket.chat.ChatWebSocketServer}.
 *
 * @author Giulia Carocari
 */
public class ChatWebSocketMessage {
	/**
	 * Enum type for the operations that a message can perform.
	 */
	public enum Operation {
		/**
		 * Persist the payload as a chat message
		 */
		@SerializedName("0")
		ADD_MESSAGE,
		/**
		 * Retrieve the messages in a chat
		 */
		@SerializedName("1")
		FETCH_CHAT,
		/**
		 * Send the unread count for a user
		 */
		@SerializedName("2")
		SEND_UNREAD_COUNT,
		/**
		 * Send the latest messages in a chat
		 */
		@SerializedName("3")
		SEND_CHAT
	}
	
	private Operation operation;
	private Object payload;

	/**
	 * Getter method for the operation type
	 * @return current operation type of the message
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * Setter method for the operation type
	 * @param operation Operation type of the message
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	/**
	 * Getter method for the payload
	 * @return current payload of the message
	 */
	public Object getPayload() {
		return payload;
	}
	
	/**
	 * Setter method for the payload
	 * @param payload payload of the message
	 */
	public void setPayload(Object payload) {
		this.payload = payload;
	}
}
