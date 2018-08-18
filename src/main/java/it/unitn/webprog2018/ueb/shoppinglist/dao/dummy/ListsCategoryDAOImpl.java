/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListsCategoryDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ListsCategory;
import it.unitn.webprog2018.ueb.shoppinglist.entities.ProductsCategory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giulia
 */
public class ListsCategoryDAOImpl implements ListsCategoryDAO {
	private DAOFactory dAOFactory;
	private ArrayList<ListsCategory> listsCategories;

	public ListsCategoryDAOImpl(DAOFactory dAOFactory) {
		this.dAOFactory=dAOFactory;
		listsCategories = new ArrayList<>();
		ListsCategory l1 = new ListsCategory();
		l1.setId(1);
		l1.setName("Supermercato");
		l1.setDescription("Un luogo per la spesa di tutti i giorni, prodotti comuni e talvolta cibi esotici");

		ListsCategory l2 = new ListsCategory();
		l2.setName("Ferramenta");
		l2.setDescription("Dove comprare prodotti per il fai-da-te, il giardinaggio e molto altro");
		l2.setId(2);
		ListsCategory l3 = new ListsCategory();
		l3.setName("Fruttivendolo");
		l3.setDescription("Piccolo negozio in cui comprare frutta e verdura fresca. Pezzo forte: motoseghe.");
		l3.setId(3);
		ListsCategory l4 = new ListsCategory();
		l4.setName("Erboristeria");
		l4.setDescription("Luogo in cui comprare prodotti naturali e medicinali omeopatici");
		l4.setId(4);
		ListsCategory l5 = new ListsCategory();
		l5.setName("Determarket");
		l5.setDescription("Venditore di prodotti per le pulizie della casa, di igiene personale e cura della persona");
		l5.setId(5);
		listsCategories.add(l5);
		listsCategories.add(l4);
		listsCategories.add(l3);
		listsCategories.add(l2);
		listsCategories.add(l1);
	}

	@Override
	public List<ListsCategory> getFromQuery(String query) throws DaoException{
		List<ListsCategory> matching = new ArrayList<ListsCategory>();

		if (query == null) {
			return listsCategories;
		}

		for (ListsCategory l : listsCategories) {
			if (l.getName().toLowerCase().contains(query.toLowerCase())) {
				matching.add(l);
			}
		}
		return matching;
	}

	@Override
	public Boolean addListCategory(ListsCategory listCategory) throws DaoException {
		listCategory.setId((int)(Math.random() * 10000));
		for(ListsCategory c : listsCategories) {
			if (c.getName().equals(listCategory.getName())) {
				return false;
			}
		}
		listsCategories.add(listCategory);
		return true;
	}
	
	public List<ListsCategory> getAll() throws DaoException{
		return listsCategories;
		
	}

	@Override
	public ListsCategory getByName(String name) throws DaoException {
		for (ListsCategory c : listsCategories) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		throw new RecordNotFoundDaoException("List category with name: " + name + " not found");
	}

	@Override
	public ListsCategory getById(Integer id) throws DaoException {
		for (ListsCategory c : listsCategories) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		throw new RecordNotFoundDaoException("Product category with id: " + id + " not found");
	}

	@Override
	public Boolean deleteListsCategory(Integer id) throws DaoException {
		ListsCategory c = getById(id);
		removeListsCategory(c);
		return true;
	}
	private synchronized void removeListsCategory(ListsCategory c) {
		listsCategories.remove(c);
	}
}
