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
public class Notification extends AbstractEntity {
	private User user;
	private List list;
	private Object product;
	private Timestamp time;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Object getProduct() {
		return product;
	}

	public void setProduct(Object product) {
		this.product = product;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	@Override
	public String getHash() {
		return CookieCipher.encrypt(id+time.toString());
	}
}
