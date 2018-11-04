package it.unitn.webprog2018.ueb.shoppinglist.servlets.auth;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import it.unitn.webprog2018.ueb.shoppinglist.utils.EmailSender;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String context = getServletContext().getContextPath();
		if (!context.endsWith("/")) {
			context += "/";
		}
		
		String name = request.getParameter("name");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		String privacy = request.getParameter("privacy");
		String avatarURI = "";
		
		User user = new User();
		user.setName(name);
		user.setLastname(lastName);
		user.setEmail(email);
		user.setPassword(Sha256.doHash(password));
		user.setCheckpassword(Sha256.doHash(checkPassword));
		user.setImage(avatarURI);
		user.setAdministrator(false);

		try {
			if (user.isVaildOnCreate((DAOFactory) this.getServletContext().getAttribute("daoFactory")) && privacy != null) {
				user.setPassword(Sha256.doHash(password));
				user.setCheckpassword(Sha256.doHash(checkPassword));
				if (!response.isCommitted()) {
					// Creating the token for the account confirmation
					Token token = new Token();
					
					token.generateToken();
					token.setExpirationFromNow(TOKEN_EXP);
					token.setUser(user);
					
					String link = "http://" + InetAddress.getLocalHost().getHostAddress()+ 
							":8080" + context + "AccountConfirmation?token=" + token.getToken();
					
					if (tokenDAO.addToken(token)) {
						if (EmailSender.send(user.getEmail(), "Conferma account",
								"Ciao " + name + ",\nPer favore clicca sul seguente link per confermare il tuo account:\n" + link)) {
							request.getRequestDispatcher("/WEB-INF/views/auth/CheckSignUp.jsp").forward(request, response);
						} else {
							response.sendError(500, "The server could not reach your email address. Please try again later.");
						}
					} else {
						response.sendError(429, "You already have a pending sign up request for this email."
								+ " Please check your mailbox");
					}
				}
			} else {
				if (privacy == null || privacy.equals("")) {
					request.setAttribute("privacy", "Confermare privacy");
				}
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
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
