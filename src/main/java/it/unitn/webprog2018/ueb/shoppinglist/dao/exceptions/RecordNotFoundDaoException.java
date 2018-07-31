/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions;

/**
 *
 * @author umberto
 */
public class RecordNotFoundDaoException extends DaoException {

	public RecordNotFoundDaoException(String msg) {
		super(msg);
	}

	public RecordNotFoundDaoException(Throwable e) {
		super(e);
	}

	public RecordNotFoundDaoException(String msg, Throwable e) {
		super(msg, e);
	}

}
