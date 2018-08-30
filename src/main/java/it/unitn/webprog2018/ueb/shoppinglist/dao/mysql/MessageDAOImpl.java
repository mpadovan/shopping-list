/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.*;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.MessageDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Message;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Michele
 */
public class MessageDAOImpl extends AbstractDAO implements MessageDAO{
	
	public MessageDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}
	
	/**
	 * dopo molteplici bestemmie ho scelto per la query piú ovvia anche se probabilmente molto lenta perché (in teoria) deve fare una query annidata per ogni record
	 * quando scarica gli ultimi messaggi viene settata la data di accesso = al sendtime del messaggio piú vecchio scaricato
	 */
	@Override
	public List<Message> getLastMessages(it.unitn.webprog2018.ueb.shoppinglist.entities.List list, User user) throws DaoException {
		List<Message> listOut = new ArrayList<>();
		try{
			String query =	"(SELECT id,sendtime,text FROM messages WHERE idlist = "+list.getId()+" AND " +
					"(SELECT lastchataccess FROM sharedlists WHERE iduser = "+user.getId()+" AND idlist = "+list.getId()+" limit 1) < sendtime " +
					"ORDER BY sendtime limit 30)";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Message m = null;
			int i;
			while(rs.next())
			{
				i = 1;
				m = new Message();
				m.setId(rs.getInt(i++));
				m.setSendTime(rs.getTimestamp(i++));
				m.setText(rs.getString(i++));
				m.setList(list);
				m.setSender(user);
				
				listOut.add(m);
			}
			if(m != null){
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				st.executeUpdate("CALL setLastAccess("+user.getId()+","+list.getId()+",\""+dateFormat.format(m.getSendTime())+"\")");
			}
			rs.close();
			st.close();
			return listOut;
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
	/**
	 * ATTENZIONE: il sendtime di message non viene guardato dato che l'orario viene preso dal DB attraverso now(1)
	 * serve solo l'id del sender e l'id della lista
	 * quando invia un messaggio in una certa chat viene settato il tempo di accesso a quel momento
	 */
	@Override
	public Boolean addMessage(Message message) throws DaoException {
		Boolean valid = message.isVaildOnCreate(dAOFactory);
		if(valid)
		{
			try{
				String query = "INSERT INTO messages (iduser,idlist,sendtime,text) VALUES " +
						"("+message.getSender().getId()+","+message.getList().getId()+",now(1),\""+message.getText()+"\");\n";
				query += "CALL setLastAccess("+message.getSender().getId()+","+message.getList().getId()+",now(1));";
				
				PreparedStatement st = this.getCon().prepareStatement(query);
				st.executeUpdate();
				st.close();
				return valid;
			}
			catch(SQLException ex){
				Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
				throw new UpdateException(ex);
			}
		}
		return valid;
	}
	
	@Override
	public Map<Integer, Integer> getUnreadCount(Integer userId) throws DaoException {
		Map<Integer, Integer> list = new HashMap<>();
		try{
			String query =	"SELECT s.idlist,count(*) " +
					"FROM sharedlists s RIGHT JOIN messages m ON s.idlist = m.idlist " +
					"WHERE s.iduser = 1 AND s.lastchataccess < m.sendtime " +
					"GROUP BY(s.idlist)";
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			int i,idlist,unreadcount;
			while(rs.next())
			{
				i = 1;
				idlist = rs.getInt(i++);
				unreadcount = rs.getInt(i++);
				
				list.put(idlist,unreadcount);
			}
			rs.close();
			st.close();
			return list;
		}
		catch(SQLException ex){
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
}
