/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.UpdateException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.TokenDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Token;
import it.unitn.webprog2018.ueb.shoppinglist.utils.CookieCipher;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Persistence is handled at runtime through a <code>List</code>
 *
 * @author Giulia Carocari
 */
public class TokenDAOImpl extends AbstractDAO implements TokenDAO {

	private static Map<String, Token> tokens;

	public TokenDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con,dAOFactory);
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
	public String getAnonimousToken() throws UpdateException {
		try{
				String query = "INSERT INTO anonusers () VALUES ()";
				PreparedStatement st = this.getCon().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				st.executeUpdate();
				ResultSet rs = st.getGeneratedKeys();
				Integer token;
				if(rs.next())
					token = rs.getInt(1);
				else
					throw new UpdateException("Token not Generated");
				st.close();
				// System.out.println(token.toString());
				return CookieCipher.encrypt(token.toString());
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new UpdateException(ex);
			}
	}
}
