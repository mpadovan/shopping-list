/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.entities;

import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;

/**
 *
 * @author simon
 */
public class Product extends AbstractEntity {

	private String name;
	private String note;
	private String logo;
	private String photografy;
	private String email;
	private String category;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
    }

    public String getLogo() {
	return logo;
    }

    public void setLogo(String logo) {
	this.logo = logo;
    }

    public String getPhotografy() {
	return photografy;
    }

    public void setPhotografy(String photografy) {
	this.photografy = photografy;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }
    
    
    
    
    
    
}
