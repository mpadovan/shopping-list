package it.unitn.webprog2018.ueb.shoppinglist.servlets.auth;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.EmailSender;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet that handles the registration of a new user
 *
 * @author Giulia Carocari
 */
@WebServlet("/SignUp")
@MultipartConfig
public class SignUpServlet extends HttpServlet {

	private static final long TOKEN_EXP = 1000 * 60 * 60 * 24;
	private UserDAO userDAO;
	private TokenDAO tokenDAO;

	/**
	 * Method to be executed at servlet initialization. Handles connections with
	 * persistence layer.
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
		String context = getServletContext().getContextPath();
		if (!context.endsWith("/")) {
			context += "/";
		}

		String name = request.getParameter("name");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		String avatarURI = "";

		User user = new User();
		user.setName(name);
		user.setLastname(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setCheckpassword(checkPassword);
		user.setImage(avatarURI);
		user.setAdministrator(false);

		try {
			if (user.isVaildOnCreate((DAOFactory) this.getServletContext().getAttribute("daoFactory"))) {
				user.setPassword(Sha256.doHash(password));
				user.setCheckpassword(Sha256.doHash(checkPassword));
				// Retrieving user avatar
				File file = null;
				String avatarFileName = "";
				String avatarsFolder = getServletContext().getInitParameter("uploadFolder") + "/restricted/tmp/";
				Part avatar = request.getPart("image");
				if ((avatar != null) && (avatar.getSize() > 0)) {
					avatarFileName = Paths.get(avatar.getSubmittedFileName()).getFileName().toString();
					int ext = avatarFileName.lastIndexOf(".");
					int noExt = avatarFileName.lastIndexOf(File.separator);
					avatarFileName = avatarsFolder + email + (ext > noExt ? avatarFileName.substring(ext) : "");
					try (InputStream fileContent = avatar.getInputStream()) {
						file = new File(avatarFileName);
						Files.copy(fileContent, file.toPath());
						avatarURI = "localhost:8080" + context + "uploads/restricted/tmp/"
								+ avatarFileName.substring(avatarFileName.lastIndexOf(email));

					} catch (FileAlreadyExistsException ex) {
						response.sendError(500, "Server could not store your avatar, "
								+ "please retry the sign up process. "
								+ "Notice that you can also upload the image later in you user page.");
						getServletContext().log("impossible to upload the file", ex);
					}
				}
				if (!response.isCommitted()) {
					user.setImage(avatarURI);

					// Creating the token for the account confirmation
					Token token = new Token();

					token.generateToken();
					token.setExpirationFromNow(TOKEN_EXP);
					token.setUser(user);

					String link = "http://localhost:8080" + context + "AccountConfirmation?token=" + token.getToken();

					if (tokenDAO.addToken(token)) {
						if (EmailSender.send(user.getEmail(), "Conferma account",
								"Ciao " + name + ",\nPer favore clicca sul seguente link per confermare il tuo account:\n" + link)) {
							request.getRequestDispatcher("/WEB-INF/views/auth/CheckSignUp.jsp").forward(request, response);
						} else {
							response.sendError(500, "The server could not reach your email address. Please try again later.");
							if (file != null) {
								file.delete();
							}
						}
					} else {
						response.sendError(429, "You already have a pending sign up request for this email."
								+ " Please check your mailbox");
						if (file != null) {
							file.delete();
						}
					}
				}
			} else {
				request.setAttribute("user", user);
				request.getRequestDispatcher("/WEB-INF/views/auth/SignUp.jsp").forward(request, response);
			}
		} catch (DaoException ex) {
			Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/auth/SignUp.jsp").forward(request, response);
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
