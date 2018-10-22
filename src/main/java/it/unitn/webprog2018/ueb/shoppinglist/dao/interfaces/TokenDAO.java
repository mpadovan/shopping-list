/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import java.util.List;

/**
 * Interface for the TokenDAOImpl object
 *
 * @author Giulia Carocari
 */
public interface TokenDAO {
	public Token getByTokenString(String token);
	
	public Token getByEmail(String token);

	public boolean addToken(Token token);
	
	public void removeExpiredTokens();
	
	public void removeToken(Token token);
	
	public String getAnonimousToken(Token token);
}
