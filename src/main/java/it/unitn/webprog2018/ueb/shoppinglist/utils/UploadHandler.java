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
	 *
	 * @param filePart
	 * @param type
	 * @param entity 
	 * @param context
	 * @return
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
			Files.copy(fileContent, file.toPath());
			return fileName.substring(fileName.lastIndexOf(uploadFolder) + uploadFolder.length());
		}
		return null;
	}
	
	/**
	 * 
	 * @param URI
	 * @param context
	 * @return 
	 */
	public boolean deleteFile(String URI, ServletContext context) {
		String uploadFolder = (String) context.getAttribute("uploadFolder");
		if (URI != null && !URI.equals("") && !URI.equals("null")) {
			File oldFile = new File(uploadFolder + URI);
			if (oldFile.exists()) {
				if (!oldFile.delete()) { // file exists but deleting failed
					return false;
				}
			}
		}
		return true;
	}
}
