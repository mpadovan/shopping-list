/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.entities;

import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;

/**
 * Entity class that implements the User entity (aka user table in database)
 *
 * @author Giulia Carocari
 */
public class User extends AbstractEntity {

	private String email;
	private String password;
	private String name;
	private String lastName;
	private String image;
	private boolean admin;
	
	public User() {
		
	}
	
	/**
	 * @return the email string associated with the user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 
	 * @param email email to associate with the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the encrypted password string associated with the user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 
	 * @param password encrypted password to associate with the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the name string associated with the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name first name to associate with the user
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the last name string associated with the user
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName last name to associate with the user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the path of the avatar image associated with the user
	 */
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return true if the user is an administrator of the page
	 */
	public boolean isAdmin() {
		return admin;
	}
	
	/**
	 * 
	 * @param admin if true then the user becomes an administrator to the page-
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}