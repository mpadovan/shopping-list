package it.unitn.webprog2018.ueb.shoppinglist.entities.utils;

import com.google.gson.annotations.Expose;
import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author giuliapeserico
 */
public abstract class AbstractEntity implements Serializable {

	@Expose
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public abstract String getHash();

	protected final Map<String, Set<String>> errors;

	public AbstractEntity() {
		this.errors = new LinkedHashMap<>();
	}

	public Boolean isVaildOnCreate(DAOFactory dAOFactory) throws DaoException {
		this.clearErrors();
		this.validateOnCreate(dAOFactory);
		this.validateOnSave(dAOFactory);
		return this.errors.isEmpty();
	}

	public Boolean isVaildOnUpdate(DAOFactory dAOFactory) throws DaoException {
		this.clearErrors();
		this.validateOnUpdate(dAOFactory);
		this.validateOnSave(dAOFactory);
		return this.errors.isEmpty();
	}

	protected void validateOnCreate(DAOFactory dAOFactory) {
	}

	;
	protected void validateOnUpdate(DAOFactory dAOFactory) {
	}

	;
	protected void validateOnSave(DAOFactory dAOFactory) throws DaoException{
	}

	;
	protected void validateOnDestroy(DAOFactory dAOFactory) {
	}

	;

	public void setError(String field, String error) {
		Set<String> fieldErrors = this.errors.get(field);

		if (fieldErrors == null) {
			this.errors.put(field, new HashSet<String>());
			fieldErrors = this.errors.get(field);
		}

		fieldErrors.add(error);
	}

	public Map<String, Set<String>> getErrors() {
		return this.errors;
	}

	public Set<String> getFieldErrors(String field) {
		return this.errors.get(field);
	}

	private void clearErrors() {
		this.errors.clear();
	}

	public Boolean isVaildOnDestroy(DAOFactory dAOFactory) {
		this.clearErrors();
		this.validateOnDestroy(dAOFactory);
		return this.errors.isEmpty();
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass() == this.getClass()) {
			if (this.id.equals(((AbstractEntity)o).getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + Objects.hashCode(this.id);
		return hash;
	}
}
