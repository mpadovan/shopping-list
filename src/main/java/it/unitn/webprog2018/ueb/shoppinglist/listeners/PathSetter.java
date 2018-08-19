/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.listeners;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author giulia
 */
public class PathSetter implements ServletContextListener {
	private static final String uploadPath = "uploads/";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String path = sce.getServletContext().getRealPath("/");
		for (int i = 0; i < 3; i++) {
			path = path.substring(0, path.lastIndexOf(File.separator));
		}
		path += "/" + uploadPath;
		sce.getServletContext().setInitParameter("uploadFolder", path);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
