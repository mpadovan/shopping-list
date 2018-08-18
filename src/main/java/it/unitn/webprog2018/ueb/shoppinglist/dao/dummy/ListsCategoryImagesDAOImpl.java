/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
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
		ListsCategory cat = new ListsCategory();
		cat.setId(1);
		l.setImage("Immagine supermercato");
		l.setCategory(cat);
		listsCategoriesImage.add(l);
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

	
}
