package it.unitn.webprog2018.ueb.shoppinglist.entities.utils;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

/**
 *
 * @author giuliapeserico
 */
public abstract class AbstractEntity implements Serializable {
	@Expose private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
