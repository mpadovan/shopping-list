/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * STatic class that handles the creation of different Gson objects based on the
 * specific usage that is to be made of them.
 *
 * @author Giulia Carocari
 */
public class CustomGsonBuilder {

	/**
	 * Creates a Gson object with specific exclusion strategies based on the
	 * compact parameter. It is intended to be used in webServices to choose the
	 * right serialization strategy based on the use that is to be made of the
	 * entities.
	 *
	 * @param compact if true then only the name and id fields of an
	 * <code>AbstractEntity</code> are serialized, otherwise it will serialize
	 * only those fields annotated with @Expose
	 * @return the required GSON object
	 * 
	 * @see it.unitn.webprog2018.ueb.shoppinglist.entities
	 */
	public static Gson create(boolean compact) {
		GsonBuilder builder = new GsonBuilder();
		if (compact) {
			return builder.addSerializationExclusionStrategy(new ExclusionStrategy() {
				@Override
				public boolean shouldSkipField(FieldAttributes fa) {
					return !fa.getName().equals("name") && !fa.getName().equals("id");
				}

				@Override
				public boolean shouldSkipClass(Class<?> type) {
					return false;
				}
			}).create();
		} else {
			return builder.excludeFieldsWithoutExposeAnnotation().create();
		}
	}
}
