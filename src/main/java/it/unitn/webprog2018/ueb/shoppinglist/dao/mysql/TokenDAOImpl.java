/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Dummy implementation of token DAO
 * Persistence is handled during runtime through a <code>List</code>
 * 
 * @author Giulia Carocari
 */
public class TokenDAOImpl implements TokenDAO {
	private static Map<String, Token> tokens;
	
	public TokenDAOImpl() {
		tokens = new HashMap<>();
	}
	
	@Override
	public Token getByTokenString(String token) {
		return tokens.get(token);
	}

	@Override
	public void addToken(Token token) {
		tokens.put(token.getToken(), token);
	}

	@Override
	public void removeExpiredTokens() {
		Date now = new Date(System.currentTimeMillis());
		for (Map.Entry<String,Token> entry : tokens.entrySet()) {
			if (entry.getValue().getExpirationDate().before(now)) {
				tokens.remove(entry.getKey());
			}
		}
	}

	@Override
	public void removeToken(Token token) {
		tokens.remove(token.getToken());
	}
}
