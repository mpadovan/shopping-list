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
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;
import it.unitn.webprog2018.ueb.shoppinglist.utils.Sha256;
import java.util.HashMap;
import java.util.Map;
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
	 * @return the path of t@Override
	 * public String getHash() {
	 * return Sha256.doHash(token);
	 * }he avatar image associated with the user
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
	public String getHash() {
		return CookieCipher.encrypt(id+"_"+email);
	}
	
	public static int getDecryptedId(String encr) {
		String res = CookieCipher.decrypt(encr);
		int id = Integer.parseInt(res.substring(0, res.indexOf('_')));
		return id;
	}
	
	@Override
	protected void validateOnSave(DAOFactory dAOFactory) throws DaoException{
		if (name == null || name.equals("")) {
			setError("name", "Non può essere lasciato vuoto");
		}
		if (name.length() > 40)
		{
			setError("name", "Non può contenere più di 40 caratteri");
		}
		if (lastname == null || lastname.equals("")) {
			setError("lastname", "Non può essere lasciato vuoto");
		}
		if (lastname.length() > 40)
		{
			setError("lastname", "Non può contenere più di 40 caratteri");
		}
		if (email == null || email.equals("")) {
			setError("email", "Non può essere lasciato vuoto");
		}
		if (email.length() > 255)
		{
			setError("email", "Non può contenere più di 40 caratteri");
		}
		if (password == null || password.equals("") || password.equals(Sha256.doHash(""))) {
			setError("password", "Non può essere lasciato vuoto");
		}
		if(scorePassword(password) <= 60){
			setError("password","Password troppo debole, prova aggiungendo lettere maiuscole,numeri o caratteri");
		}
		if (checkpassword == null || checkpassword.equals("") || password.equals(Sha256.doHash(""))) {
			setError("checkpassword", "Non può essere lasciato vuoto");
		}
		if (!(password.equals(checkpassword))) {
			setError("checkpassword", "Deve coincidere con password");
		}
	}
	
	@Override
	protected void validateOnUpdate(DAOFactory dAOFactory) throws DaoException {
	}
	
	@Override
	protected void validateOnCreate(DAOFactory dAOFactory) throws DaoException{
		if(errors.isEmpty())
		{
			UserDAO userDAO = ((DAOFactory) dAOFactory).getUserDAO();
			try {
				userDAO.getByEmail(email);
				setError("email", "Email già esistente, sembra che tu sia già registrato. Effettua il login");
				/*User user = userDAO.getById(id);
				if(id!=user.getId())
				{
				setError("email", "email già esistente");
				}*/
			} catch (RecordNotFoundDaoException ex) {
				//tutto andato a buon fine, nessun duplicato
			}
		}
	}
	
	private Integer scorePassword(String pass){
		float score = 0;
		if(pass == null || pass.length() < 1)
			return 0;
		
		Map<Character, Integer> letters = new HashMap();
		for (int i=0; i<pass.length(); i++) {
			Character c =(Character)pass.charAt(i);
			letters.put(c, letters.containsKey(c)? letters.get(c)+1 : 1);
			score += 5.0 / letters.get(c);
		}
		
		Boolean[] variations = new Boolean[4];
		variations[0] = pass.matches("(.)*(\\d)(.)*");
		variations[1] = pass.matches("(.)*([a-z])(.)*");
		variations[2] = pass.matches("(.)*([A-Z])(.)*");
		variations[3] = pass.matches("(.)*(\\W)(.)*");
		
		int variationCount = 0;
		for(int i = 0; i < variations.length; i++)
			variationCount += (variations[i] ? 1 : 0);
				
		score += (variationCount - 1)*10;
		//System.out.println((int)score);
		return (int) score;
	}
	
	
}