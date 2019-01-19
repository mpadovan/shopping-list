/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.uploads;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Serves the uploaded files to the client requesting them. It maps the URIs of
 * all uploaded files.
 *
 * @author Giulia Carocari
 */
@WebServlet(name = "UploadsServlet", urlPatterns = {"/Uploads/*"})
public class UploadsServlet extends HttpServlet {

	/**
	 * Handles the HTTP <code>GET</code> method. Sends the requested file to the
	 * user.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String filename = request.getPathInfo().substring(1);
			String uploadPath = (String) getServletContext().getAttribute("uploadFolder") + File.separator + "Uploads";
			File file = new File(uploadPath, filename);
			response.setHeader("Content-Type", getServletContext().getMimeType(filename));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
			Files.copy(file.toPath(), response.getOutputStream());
		} catch (FileNotFoundException | NoSuchFileException ex) {
			response.sendError(404, "The resource you are looking for does not exist in our system");
		}
		//Exception thrown by Files.copy()

	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Provides user and admin uploaded images";
	}// </editor-fold>

}
