/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.entities;

import com.google.gson.annotations.Expose;
import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;

/**
 *
 * @author simon
 */
public class PublicProduct extends AbstractEntity {

	@Expose
	private String name;
	@Expose
	private String note;
	@Expose
	private String logo;
	@Expose
	private String photography;
	@Expose
	private ProductsCategory category;

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

	public String getPhotography() {
		return photography;
	}

	public void setPhotography(String photography) {
		this.photography = photography;
	}

	public ProductsCategory getCategory() {
		return category;
	}

	public void setCategory(ProductsCategory category) {
		this.category = category;
	}

}
