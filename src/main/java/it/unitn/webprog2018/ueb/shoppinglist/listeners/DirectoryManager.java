/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.listeners;

import it.unitn.webprog2018.ueb.shoppinglist.utils.HttpErrorHandler;
import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener that handles the creation of the upload folder located in the CATALINA_BASE directory.
 * The directory and/or its subdirectories are newly created if and only if they do not already exist.
 *
 * @author Giulia Carocari
 */
@WebListener
public class DirectoryManager implements ServletContextListener {
	
	/**
	 * Creates the directories at application startup.
	 * If the outermost directory exists ($CATALINA_BASE/ShoppingList/Uploads), it assumes that also its sub-directories exist,
	 * otherwise it creates the whole nested structure.
	 * Eventually it sets the uploads folder path and its innermost directories 
	 * (allowing for structural revision) as context attributes.
	 * One may safely assume that the path string set by this method is always terminated by <code>File.separator</code>.
	 * 
	 * @param sce 
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// HttpErrorHandler.setContextPath(sce.getServletContext().getContextPath());
		String path = System.getProperty("catalina.home") + sce.getServletContext().getContextPath();
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		path += "Uploads" + File.separator;
		File folders = new File(path);
		String publicPath = path + "public" + File.separator;
		String restrictedPath = path + "restricted" + File.separator;
		if(!folders.exists()) {	// The upload folder does not exist -> I have to create it from the start
			if (! folders.mkdirs()) {
				System.err.println("directory creation failed");
			}
			new File(publicPath).mkdir();
			new File(publicPath + "listCategoryImage").mkdir();
			new File(publicPath + "productCategoryLogo").mkdir();
			new File(publicPath + "productImage").mkdir();
			new File(publicPath + "productLogo").mkdir();
			
			new File(restrictedPath).mkdir();
			new File(restrictedPath + "avatar").mkdir();
			new File(restrictedPath + "shared" + File.separator + "listImage").mkdirs();
			new File(restrictedPath + "shared" + File.separator + "productImage").mkdirs();
		}
		// Path adjustment to allow it to be used with an image uri
		// Sample usage: < getServletContext().getAttribute("uploadFolder") + entity.getImage() > is the absolute path of the file
		path = path.substring(0,path.lastIndexOf(File.separator));
		path = path.substring(0,path.lastIndexOf(File.separator)) + File.separator;
		
		sce.getServletContext().setAttribute("uploadFolder", path.substring(0,path.lastIndexOf(File.separator)));
		sce.getServletContext().setAttribute("avatarFolder", restrictedPath + "avatar" + File.separator);
		sce.getServletContext().setAttribute("listFolder", restrictedPath + "shared" + File.separator + "listImage" + File.separator);
		sce.getServletContext().setAttribute("productFolder", restrictedPath + "shared" + File.separator + "productImage" + File.separator);
		sce.getServletContext().setAttribute("publicProductFolder", publicPath + "productImage" + File.separator);
		sce.getServletContext().setAttribute("productLogoFolder", publicPath + "productLogo" + File.separator);
		sce.getServletContext().setAttribute("categoryLogoFolder", publicPath + "productCategoryLogo" + File.separator);
		sce.getServletContext().setAttribute("listCategoryFolder", publicPath + "listCategoryImage" + File.separator);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
}
