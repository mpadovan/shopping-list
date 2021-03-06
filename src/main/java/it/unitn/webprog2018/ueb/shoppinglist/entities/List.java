/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.entities;

import com.google.gson.annotations.Expose;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;

/**
 *
 * @author simon
 */
public class List extends AbstractEntity {

	@Expose
	private String name;
	private User owner;
	private ListsCategory category;
	private String description;
	private String image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public ListsCategory getCategory() {
		return category;
	}

	public void setCategory(ListsCategory category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	protected void validateOnSave(DAOFactory dAOFactory) throws DaoException {
		if (name == null || name.equals("")) {
			setError("name", "Non può essere lasciato vuoto");
		}
		if (name.length() > 40)
		{
			setError("name", "Non può contenere più di 40 caratteri");
		}
		if (description.length() > 256)
		{
			setError("description", "Non può contenere più di 256 caratteri");
		}
		if (category==null || category.equals(""))
		{
			setError("name", "Non può essere lasciato vuoto");
		}
	}

	@Override
	protected void validateOnUpdate(DAOFactory dAOFactory) throws DaoException {
	}

	@Override
	protected void validateOnCreate(DAOFactory dAOFactory) throws DaoException {
	}

	@Override
	public String getHash() {
		int encr = this.id * this.id + 42;
		return "list-" + encr;
	}
	
	public static int getDecryptedId(String encr) {
		int id = Integer.parseInt(encr.substring(5));
		return (int)Math.sqrt(id-42);
	}

}
