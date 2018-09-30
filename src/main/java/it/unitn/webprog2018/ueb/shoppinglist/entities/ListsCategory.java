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
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simon
 */
public class ListsCategory extends AbstractEntity {

	@Expose
	private String name;
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
	public String getHash() {
		return CookieCipher.encrypt(id+name);
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
			setError("description", "Non può contenere più di 40 caratteri");
		}
	}

	@Override
	protected void validateOnUpdate(DAOFactory dAOFactory) throws DaoException {
		if (errors.isEmpty()) {
			ListsCategoryDAO listsCategoryDAO = ((DAOFactory) dAOFactory).getListsCategoryDAO();
			try {
				ListsCategory listsCategory = listsCategoryDAO.getByName(name);
				if (id != listsCategory.getId()) {
					setError("name", "Nome già esistente");
				}
			} catch (RecordNotFoundDaoException ex) {
				//tutto andato a buon fine, nessun duplicato
			}
		}
	}

	@Override
	protected void validateOnCreate(DAOFactory dAOFactory) throws DaoException {
		if (errors.isEmpty()) {
			ListsCategoryDAO listsCategoryDAO = ((DAOFactory) dAOFactory).getListsCategoryDAO();
			try {
				listsCategoryDAO.getByName(name);
				setError("name", "Nome già esistente");
			} catch (RecordNotFoundDaoException ex) {
				//tutto andato a buon fine, nessun duplicato
			}
		}
	}

}
