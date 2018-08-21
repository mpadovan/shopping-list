/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategoriesImage;
import java.util.List;

/**
 *
 * @author simon
 */
public interface ListsCategoryImagesDAO {
	public Boolean addListsCategoriesImage(ListsCategoriesImage listCategoriesImage) throws DaoException;
	public List<ListsCategoriesImage> getAll() throws DaoException;
	public Boolean updateListsCategoriesImage(Integer categoryId, ListsCategoriesImage listCategoriesImage) throws DaoException;
	public ListsCategoriesImage getById(Integer id) throws DaoException;
}
