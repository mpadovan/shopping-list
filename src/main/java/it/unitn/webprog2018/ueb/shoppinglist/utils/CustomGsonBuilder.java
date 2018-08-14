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
 *
 * @author Giulia Carocari
 */
public class CustomGsonBuilder {
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
