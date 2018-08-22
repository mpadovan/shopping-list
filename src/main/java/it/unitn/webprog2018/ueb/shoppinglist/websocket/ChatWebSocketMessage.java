/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket;

import com.google.gson.annotations.SerializedName;

/**
 * Representation of the message sent by the ChatWebSocket.
 *
 * @author Giulia Carocari
 */
public class ChatWebSocketMessage {
	public enum Operation {
		@SerializedName("0")
		ADD_MESSAGE,
		@SerializedName("1")
		FETCH_CHAT,
		@SerializedName("2")
		SEND_UNREAD_COUNT,
		@SerializedName("3")
		SEND_CHAT
	}
	
	private Operation operation;
	private Object payload;

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}
}
