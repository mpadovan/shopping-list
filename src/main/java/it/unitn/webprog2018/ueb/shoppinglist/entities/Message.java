/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.entities;

import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;
import java.sql.Timestamp;

/**
 *
 * @author Giulia Carocari	
 */
public class Message extends AbstractEntity {
	private User sender;
	private List list;
	private Timestamp sendTime;
	private String text;
	private boolean read;

	

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
	@Override
	public String getHash() {
		return CookieCipher.encrypt(id+text);
	}
	
}
