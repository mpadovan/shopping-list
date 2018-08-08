/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Dummy implementation of token DAO
 * Persistence is handled during runtime through a <code>List</code>
 * 
 * @author Giulia Carocari
 */
public class TokenDAOImpl implements TokenDAO {
	
	private static List<Token> tokens = new LinkedList<>();
	
	@Override
	public Token getByTokenString(String token) {
		for (Token tok : tokens) {
			if (tok.getToken().equals(token)) {
				return tok;
			}
		}
		return null;
	}

	@Override
	public void addToken(Token token) {
		tokens.add(token);
	}

	@Override
	public void removeExpiredTokens() {
		Date now = new Date(System.currentTimeMillis());
		for (Token tok : tokens) {
			if (tok.getExpirationDate().before(now)) {
				tokens.remove(tok);
			}
		}
	}

	@Override
	public void removeToken(Token token) {
		tokens.remove(token);
	}
	
}
