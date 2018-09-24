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
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ProductsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.PublicProductDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	@Override
	protected void validateOnSave(DAOFactory dAOFactory) throws DaoException{
		if (name==null || name.equals(""))
		{
			setError("name", "Non può essere lasciato vuoto");
		}
		if (category==null || category.equals(""))
		{
			setError("category", "Non può essere lasciato vuoto");
		}
	}

	@Override
	protected void validateOnUpdate(DAOFactory dAOFactory) throws DaoException {
		if(errors.isEmpty())
		{
			PublicProductDAO publicProductDAO = ((DAOFactory) dAOFactory).getPublicProductDAO();
			try {
				PublicProduct publicProduct = publicProductDAO.getByName(name);
				if(id!=publicProduct.getId())
				{
					setError("name", "Nome già esistente");
				}
			} catch (RecordNotFoundDaoException ex) {
				//tutto andato a buon fine, nessun duplicato
			}
		}
	}

	@Override
	protected void validateOnCreate(DAOFactory dAOFactory) throws DaoException {
		if(errors.isEmpty())
		{
			PublicProductDAO publicProductDAO = ((DAOFactory) dAOFactory).getPublicProductDAO();
			try {
				publicProductDAO.getByName(name);
				setError("name", "Nome già esistente");
				/*PublicProduct publicProduct = publicProductDAO.getById(id);
				if(id!=publicProduct.getId())
				{
					setError("name", "Nome già esistente");
				}*/
			} catch (RecordNotFoundDaoException ex) {
				//tutto andato a buon fine, nessun duplicato
			}
		}
	}

}
