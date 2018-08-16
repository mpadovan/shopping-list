/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.ListDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.List;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import java.sql.Connection;
import java.util.Map;

/**
 *
 * @author simon
 */
public class ListDAOImpl extends AbstractDAO implements ListDAO{
	private DAOFactory dAOFactory;
	
	public ListDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}

	@Override
	public List getList(Integer id) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Map<PublicProduct, Integer> getPublicProductsOnList(Integer listId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Map<Product, Integer> getProductsOnList(Integer listId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean addProduct(Integer listId, Product product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean addPublicProduct(Integer listId, PublicProduct product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean isOnList(Integer listId, PublicProduct product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean isOnList(Integer listId, Product product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean updateAmount(Integer listId, PublicProduct product, Integer newAmount) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean updateAmount(Integer listId, Product product, Integer newAmount) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean updateAmount(Integer listId, PublicProduct product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean updateAmount(Integer listId, Product product) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean hasAddDeletePermission(Integer listId, Integer userId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean hasModifyPermission(Integer listId, Integer userId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Boolean hasDeletePermission(Integer listId, Integer userId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public java.util.List<List> getByUser(Integer userId) throws DaoException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
