/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.servlets.uploads;

import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Giulia Carocari
 */
@WebServlet("/uploads/*")
public class UploadsServlet extends HttpServlet {

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String filename = request.getPathInfo().substring(1);
			String uploadPath = getServletContext().getInitParameter("uploadFolder");
			File file = new File(uploadPath, filename);
			response.setHeader("Content-Type", getServletContext().getMimeType(filename));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
			Files.copy(file.toPath(), response.getOutputStream());
		} catch (FileNotFoundException ex) {
			response.sendError(404, "The resource you are lookin for does not exist in our system");
		} catch (NoSuchFileException ex) { //Exception thrown by Files.copy()
			response.sendError(404, "The resource you are lookin for does not exist in our system");
		}
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
