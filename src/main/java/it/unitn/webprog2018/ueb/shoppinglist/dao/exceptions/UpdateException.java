/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions;

/**
 *
 * @author Michele
 */
public class UpdateException extends DaoException{
	
	public UpdateException(String msg) {
		super(msg);
	}
	
	public UpdateException(Throwable e) {
		super(e);
	}
	
	public UpdateException(String msg, Throwable e) {
		super(msg, e);
	}
	
}
