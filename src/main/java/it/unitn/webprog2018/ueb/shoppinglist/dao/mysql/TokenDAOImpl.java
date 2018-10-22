/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Persistence is handled at runtime through a <code>List</code>
 *
 * @author Giulia Carocari
 */
public class TokenDAOImpl implements TokenDAO {

	private static Map<String, Token> tokens;

	public TokenDAOImpl() {
		tokens = new HashMap<>();
	}

	@Override
	public Token getByEmail(String email) {
		return tokens.get(email);
	}

	@Override
	public boolean addToken(Token token) {
		Token tok = tokens.get(token.getUser().getEmail());
		Date now = new Date(System.currentTimeMillis());
		if (tok != null && tok.getExpirationDate().before(now)) {
			removeToken(token);
			tokens.put(token.getUser().getEmail(), token);
			return true;
		} else if (tok != null) {
			return false;
		} else {
			tokens.put(token.getUser().getEmail(), token);
			return true;
		}

	}

	@Override
	public void removeExpiredTokens() {
		Date now = new Date(System.currentTimeMillis());
		for (Map.Entry<String, Token> entry : tokens.entrySet()) {
			if (entry.getValue().getExpirationDate().before(now)) {
				tokens.remove(entry.getKey());
			}
		}
	}

	@Override
	public void removeToken(Token token) {
		tokens.remove(token.getUser().getEmail());
	}

	@Override
	public Token getByTokenString(String token) {
		for (Map.Entry<String, Token> entry : tokens.entrySet()) {
			if (entry.getValue().getToken().equals(token)) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public String getAnonimousToken(Token token) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
