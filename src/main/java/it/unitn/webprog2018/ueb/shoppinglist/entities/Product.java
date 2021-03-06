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
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;

/**
 *
 * @author simon
 */
public class Product extends AbstractEntity {

	@Expose
	private String name;
	@Expose
	private String note;
	@Expose
	private String logo;
	@Expose
	private String photography;
	@Expose
	private User owner;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public ProductsCategory getCategory() {
		return category;
	}

	public void setCategory(ProductsCategory category) {
		this.category = category;
	}
	
	@Override
	public String getHash() {
		return CookieCipher.encrypt(id+name);
	}
	
	@Override
	protected void validateOnSave(DAOFactory dAOFactory) throws DaoException {
		if (name==null || name.equals(""))
		{
			setError("name", "Non può essere lasciato vuoto");
		}
		if (name.length() > 40)
		{
			setError("name", "Non può contenere più di 40 caratteri");
		}
		if (note.length() > 256)
		{
			setError("note", "Non può contenere più di 256 caratteri");
		}
		if (category==null || category.equals(""))
		{
			setError("name", "Non può essere lasciato vuoto");
		}
	}

	@Override
	protected void validateOnUpdate(DAOFactory dAOFactory) {
	}

	@Override
	protected void validateOnCreate(DAOFactory dAOFactory) {
	}

}
