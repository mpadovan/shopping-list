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
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;

/**
 *
 * @author simon
 */
public class List extends AbstractEntity {

	@Expose
	private Integer id;
	@Expose
	private String name;
	private User owner;
	private ListsCategory category;
	private String description;
	private String image;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
		if (image==null || image.equals(""))
		{
			setError("image", "image può essere lasciato vuoto");
		}
		if(errors.isEmpty())
		{
			ListDAO listDAO = ((DAOFactory) dAOFactory).getListDAO();
			try {
				listDAO.getList(name, owner);
				List list = listDAO.getList(id);
				if(id!=list.getId())
				{
					setError("name", "name già esistente");
				}
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
