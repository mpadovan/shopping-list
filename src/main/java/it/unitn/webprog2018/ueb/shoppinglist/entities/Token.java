/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.entities;

import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;
import java.sql.Date;
import java.util.UUID;

/**
 *
 * @author Giulia Carocari
 */
public class Token extends AbstractEntity {
	
	private String token;
	private Date expirationDate;
	private User user;
	
	public Token() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void generateToken() {
		token = UUID.randomUUID().toString();
	}
	
	public void setExpirationFromNow(long delay) {
		Date date = new Date(System.currentTimeMillis() + delay);
		expirationDate = date;
	}
}
