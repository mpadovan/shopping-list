package it.unitn.webprog2018.ueb.shoppinglist.servlets.auth;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.EmailSender;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet that handles the registration of a new user
 *
 * @author Giulia Carocari
 */
@MultipartConfig
public class SignUpServlet extends HttpServlet {

	private static final long TOKEN_EXP = 1000 * 60 * 60 * 24;
	private UserDAO userDAO;
	private TokenDAO tokenDAO;

	/**
	 * Method to be executed at servlet initialization.
	 * Handles connections with persistence layer.
	 */
	@Override
	public void init() {
		DAOFactory factory = (DAOFactory) this.getServletContext().getAttribute("daoFactory");
		userDAO = factory.getUserDAO();
		tokenDAO = factory.getTokenDAO();
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String lastName = request.getParameter("lastName");

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");

		// TODO check for not null values, maybe client-side?
		if (!password.equals(checkPassword)) {

			request.setAttribute("passwordError", "true");
			// TODO add redirection to sign up form
			response.sendRedirect("");

		} else if (userDAO.getByEmail(email) != null) {

			request.setAttribute("emailTaken", "true");
			// TODO add redirection to sign up form
			response.sendRedirect("signUp.html");

		} else {

			// Retrieving user avatar
			/*
			String avatarFileName = "";
			String avatarsFolder = getServletContext().getInitParameter("avatarsFolder");
			Part avatar = request.getPart("image");
			if ((avatar != null) && (avatar.getSize() > 0)) {
				avatarFileName = Paths.get(avatar.getSubmittedFileName()).getFileName().toString();
				try (InputStream fileContent = avatar.getInputStream()) {
					File file = new File(avatarsFolder + File.separator + avatarFileName);
					// file.createNewFile();
					Files.copy(fileContent, file.toPath());
					
				} catch (FileAlreadyExistsException ex) {
					getServletContext().log("File \"" + avatarFileName + "\" already exists on the server");
				} catch (Exception ex) {
					response.sendError(500, "Server could not store your avatar, "
							+ "please retry the sign up process. "
							+ "Notice that you can also upload the image later in you user page.");
					getServletContext().log("impossible to upload the file", ex);
				}
			}
			*/
			if (!response.isCommitted()) {
				// Creating the new user
				User user = new User();
				user.setEmail(email);
				user.setPassword(password);
				user.setName(name);
				user.setLastname(lastName);
				user.setAdministrator(false);
				user.setImage("");	// TODO set this correctly
				
				// Creating the token for the account confirmation
				Token token = new Token();

				token.generateToken();
				token.setExpirationFromNow(TOKEN_EXP);
				token.setUser(user);

				tokenDAO.addToken(token);

				// Sending the email to the new user
				EmailSender emailSender = new EmailSender();

				String context = getServletContext().getContextPath();
				if (!context.endsWith("/")) {
					context += "/";
				}
				String link = "http://localhost:8080" + context + "AccountConfirmation?token=" + token.getToken();
				if (emailSender.send(user.getEmail(), "Account Confirmation",
						"Hello " + name + ",\nPlease click on the following link to valiate your account:\n" + link)) {
					// TODO redirect response to successful registration
				} else {
					response.sendError(500, "The server could not reach your email address. Please try again later.");
				}

			}
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Servlet for registration purposes. Handles only POST requests";
	}

}
