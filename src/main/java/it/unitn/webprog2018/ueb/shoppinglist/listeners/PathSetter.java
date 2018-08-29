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
 * Sets up the path to the upload folder of the app at servlet context
 * initialization. Distinguishes between Windows and Linux/Mac-OS through the
 * Files.separator attribute.
 *
 * @author Giulia Carocari
 */
public class PathSetter implements ServletContextListener {

	private static final String UPLOAD_PATH = "uploads";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String path = sce.getServletContext().getRealPath("/");
		for (int i = 0; i < 3; i++) {
			path = path.substring(0, path.lastIndexOf(File.separator));
		}
		path += File.separator + UPLOAD_PATH;
		sce.getServletContext().setInitParameter("uploadFolder", path);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
