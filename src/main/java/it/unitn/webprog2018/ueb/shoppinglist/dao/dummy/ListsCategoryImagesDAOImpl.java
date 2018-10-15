/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryImagesDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategoriesImage;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simon
 */
public class ListsCategoryImagesDAOImpl implements ListsCategoryImagesDAO {
	private DAOFactory dAOFactory;
	private List<ListsCategoriesImage> listsCategoriesImage;

	public ListsCategoryImagesDAOImpl(DAOFactory dAOFactory) {
		this.dAOFactory = dAOFactory;
		listsCategoriesImage = new ArrayList<>();
		ListsCategoriesImage l = new ListsCategoriesImage();
		ListsCategoriesImage l2 = new ListsCategoriesImage();
		ListsCategory cat = new ListsCategory();
		cat.setId(1);
		l.setId(1);
		l.setImage("Immagine");
		l.setCategory(cat);
		listsCategoriesImage.add(l);
		/**l2.setId(2);
		l2.setImage("Immagine2");
		l2.setCategory(cat);
		listsCategoriesImage.add(l2);**/

	}

	@Override
	public Boolean addListsCategoriesImage(ListsCategoriesImage listCategoriesImage) throws DaoException {
		listCategoriesImage.setId((int)(Math.random() * 10000));
		boolean valid = listCategoriesImage.isVaildOnCreate(dAOFactory);
		if(valid){
			listsCategoriesImage.add(listCategoriesImage);
		}
		return valid;
	}

	@Override
	public List<ListsCategoriesImage> getAll() throws DaoException {
		return listsCategoriesImage;
	}
	
	@Override
	public ListsCategoriesImage getById(Integer id) throws DaoException {
		for (ListsCategoriesImage c : listsCategoriesImage) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		throw new RecordNotFoundDaoException("List category image with id: " + id + " not found");
	}

	@Override
	public Boolean updateListsCategoriesImage(Integer id, ListsCategoriesImage listCategoriesImage) throws DaoException {
		getById(id);

		Boolean valid = listCategoriesImage.isVaildOnUpdate(dAOFactory);
		if (valid) {
			updateListsCategorydummy(id, listCategoriesImage);
		}
		return valid;
	}
	
	private synchronized void updateListsCategorydummy(Integer id, ListsCategoriesImage c) throws DaoException {
		ListsCategoriesImage found = getById(id);

		found.setId(c.getId());
		found.setImage(c.getImage());

	}

	@Override
	public List<ListsCategoriesImage> getByCategoriesID(Integer CategoryID) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	
	
}
