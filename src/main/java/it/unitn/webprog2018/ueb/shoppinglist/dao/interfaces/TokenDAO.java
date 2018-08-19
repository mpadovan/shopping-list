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
	Token getByTokenString(String token);
	
	Token getByEmail(String token);

	boolean addToken(Token token);
	
	void removeExpiredTokens();
	
	public void removeToken(Token token);
}
