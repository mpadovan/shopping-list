/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.entities;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.RecordNotFoundDaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.UserDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.utils.AbstractEntity;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entity class that implements the User entity (aka user table in database)
 *
 * @author Giulia Carocari
 */
public class User extends AbstractEntity {

	private String email;
	private String password;
	private String checkpassword;
	private String name;
	private String lastname;
	private String image;
	private Boolean administrator;
	private String tokenpassword;
	
	public User() {
		
	}
	
	public String getCheckpassword() {
		return checkpassword;
	}

	public void setCheckpassword(String checkpassword) {
		this.checkpassword = checkpassword;
	}
	
	/**
	 * @return the email string associated with the user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 
	 * @param email email to associate with the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the encrypted password string associated with the user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 
	 * @param password encrypted password to associate with the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the name string associated with the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name first name to associate with the user
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the last name string associated with the user
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * 
	 * @param lastname last name to associate with the user
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the path of the avatar image associated with the user
	 */
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return true if the user is an administrator of the page
	 */
	public boolean isAdministrator() {
		return administrator;
	}
	
	/**
	 * 
	 * @param administrator if true then the user becomes an administrator to the page-
	 */
	public void setAdministrator(Boolean administrator) {
		this.administrator = administrator;
	}
	
	public String getTokenpassword() {
		return tokenpassword;
	}

	public void setTokenpassword(String tokenpassword) {
		this.tokenpassword = tokenpassword;
	}
	
	@Override
	protected void validateOnSave(DAOFactory dAOFactory) throws DaoException{
		if (name == null || name.equals("")) {
			setError("name", "Non può essere lasciato vuoto");
		}
		if (lastname == null || lastname.equals("")) {
			setError("lastname", "Non può essere lasciato vuoto");
		}
		if (email == null || email.equals("")) {
			setError("email", "Non può essere lasciato vuoto");
		}
		/*if(errors.isEmpty())
		{
			UserDAO userDAO = ((DAOFactory) dAOFactory).getUserDAO();
			try {
				userDAO.getByEmail(email);
				User user = userDAO.getById(id);
				if(id!=user.getId())
				{
					setError("email", "email già esistente");
					System.out.println("ciao");
				}
			} catch (RecordNotFoundDaoException ex) {
				//tutto andato a buon fine, nessun duplicato
				System.out.println("RNFDE");
			}
		}*/
	}

	@Override
	protected void validateOnUpdate(DAOFactory dAOFactory) {
		/*if (password != null && !password.equals("") && !(password.equals(checkpassword))) {
			setError("checkpassword", "Deve coincidere con password");
		}*/
	}

	@Override
	protected void validateOnCreate(DAOFactory dAOFactory) {
		if (password == null || password.equals("")) {
			setError("password", "Non può essere lasciato vuoto");
		}
		if (checkpassword == null || checkpassword.equals("")) {
			setError("checkpassword", "Non può essere lasciato vuoto");
		}
		if (password != null && !password.equals("") && !(password.equals(checkpassword))) {
			setError("checkpassword", "Deve coincidere con password");
		}
	}
}