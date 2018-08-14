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
public class ListsCategoriesImage extends AbstractEntity {

	@Expose private String image;
	private ListsCategory category;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ListsCategory getCategory() {
		return category;
	}

	public void setCategory(ListsCategory category) {
		this.category = category;
	}

}
