/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket.notification;

import com.google.gson.annotations.SerializedName;

/**
 * Message sent on the websocket connection to notify users of products that
 * should be inserted into a list soon. This class is currently not used and is
 * therefore not testes. It is implemented for future use.
 *
 * @author Giulia Carocari
 */
public class NotificationWebSocketMessage {

	public enum Operation {
		@SerializedName("0")
		SEND_UNREAD_COUNT,
		@SerializedName("1")
		SEND_NOTIFICATIONS,
		@SerializedName("2")
		FETCH_NOTIFICATIONS
	}

	Operation operation;
	Object payload;

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
