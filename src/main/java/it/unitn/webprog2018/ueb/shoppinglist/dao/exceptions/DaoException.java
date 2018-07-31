package it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions;

/**
 *
 * @author giuliapeserico
 */
public class DaoException extends Exception {

	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(Throwable e) {
		super(e);
	}

	public DaoException(String msg, Throwable e) {
		super(msg, e);
	}

}
