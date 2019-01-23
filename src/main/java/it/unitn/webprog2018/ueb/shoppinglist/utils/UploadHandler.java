/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.utils;

import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;

/**
 * Class that handles the various types of file upload.
 *
 * @author Giulia Carocari
 */
@ApplicationScoped
public class UploadHandler {

	/**
	 * Type of image file that can be uploaded to the application
	 */
	public static enum FILE_TYPE {
		AVATAR,
		LIST_IMAGE,
		LIST_CATEGORY_IMAGE,
		PRODUCT_IMAGE,
		PRODUCT_LOGO,
		PUBLIC_PRODUCT_IMAGE,
		PUBLIC_PRODUCT_LOGO,
		PRODUCT_CATEGORY_LOGO;
	}

	/**
	 * Handles the upload of a file given its part, the {@link FILE_TYPE} and the entity type it is
	 * associated to.
	 *
	 * @param filePart File part that contains the file to be saved on the server
	 * @param type	Type of the image to be uploaded
	 * @param entity 	The entity to which this image is associated
	 * @param context	Servlet context of the application
	 * @return	The URI of the uploaded image
	 * @throws java.io.IOException
	 */
	public String uploadFile(@NotNull Part filePart, @NotNull FILE_TYPE type, @NotNull AbstractEntity entity, ServletContext context)
			throws IOException {
		String folder;
		String uploadFolder = (String) context.getAttribute("uploadFolder");
		switch (type) {
			case AVATAR:
				folder = (String) context.getAttribute("avatarFolder");
				break;
			case LIST_IMAGE:
				folder = (String) context.getAttribute("listFolder");
				break;
			case PRODUCT_IMAGE:
				folder = (String) context.getAttribute("productFolder");
				break;
			case PRODUCT_LOGO:
				folder = (String) context.getAttribute("productLogoFolder");
				break;
			case PUBLIC_PRODUCT_IMAGE:
				folder = (String) context.getAttribute("publicProductFolder");
				break;
			case PUBLIC_PRODUCT_LOGO:
				folder = (String) context.getAttribute("publicProductLogoFolder");
				break;
			case PRODUCT_CATEGORY_LOGO:
				folder = (String) context.getAttribute("categoryLogoFolder");
				break;
			case LIST_CATEGORY_IMAGE:
				folder = (String) context.getAttribute("listCategoryFolder");
				break;
			default:
				folder = null;
		}

		if (folder != null) { // The upload location has been correctly selected
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			// Extension handling
			int ext = fileName.lastIndexOf(".");
			int noExt = fileName.lastIndexOf(File.separator);
			fileName = folder + entity.getHash() + (ext > noExt ? fileName.substring(ext) : "");

			InputStream fileContent = filePart.getInputStream();
			File file = new File(fileName);
			if (file.exists()) {	// avoid fileAlreadyExistsException
				file.delete();
			}
			try {
				Files.copy(fileContent, file.toPath());
			} catch(NoSuchFileException ex) {
				return null;
			}
			return (fileName.substring(fileName.lastIndexOf(uploadFolder) + uploadFolder.length())).replace(File.separatorChar, '/');
		}
		// System.out.println("Returning null when uploading");
		return null;
	}
	
	/**
	 * Deletes a file from the server given its URI.
	 * 
	 * @param URI	URI of the file to be deleted
	 * @param context	Servlet context of the application
	 * @return True if deletion was successful
	 */
	public boolean deleteFile(String URI, ServletContext context) {
		String uploadFolder = (String) context.getAttribute("uploadFolder");
		if (URI != null && !URI.equals("") && !URI.equals("null")) {
			File oldFile = new File((uploadFolder + URI).replace('/', File.separatorChar));
			if (oldFile.exists()) {
				if (!oldFile.delete()) { // file exists but deleting failed
					return false;
				}
			}
		}
		return true;
	}
}
