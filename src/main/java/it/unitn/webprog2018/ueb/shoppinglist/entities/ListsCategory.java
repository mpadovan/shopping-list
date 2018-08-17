/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.entities;

import com.google.gson.annotations.Expose;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simon
 */
public class ListsCategory extends AbstractEntity {
	
	@Expose private String name;
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	protected void validateOnSave(DAOFactory dAOFactory) throws DaoException {
		if (name==null || name.equals(""))
		{
			setError("name", "Non può essere lasciato vuoto");
		}
		if(errors.isEmpty())
		{
			ListsCategoryDAO listsCategoryDAO = ((DAOFactory) dAOFactory).getListsCategoryDAO();
			try {
				listsCategoryDAO.getByName(name);
				setError("name", "name già esistente");
			} catch (RecordNotFoundDaoException ex) {
				//tutto andato a buon fine, nessun duplicato
			}
		}
	}

	@Override
	protected void validateOnUpdate(DAOFactory dAOFactory) {
	}

	@Override
	protected void validateOnCreate(DAOFactory dAOFactory) {
	}

}
