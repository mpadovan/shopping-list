/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.unitn.webprog2018.ueb.shoppinglist.dao.mysql;

import it.unitn.webprog2018.ueb.shoppinglist.dao.DAOFactory;
import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.NotificationDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Notification;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Product;
import it.unitn.webprog2018.ueb.shoppinglist.entities.PublicProduct;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michele
 */
public class NotificationDAOImpl extends AbstractDAO implements NotificationDAO{
	
	public NotificationDAOImpl(Connection con, DAOFactory dAOFactory) {
		super(con, dAOFactory);
	}
	
	@Override
	public List<Notification> getNextNotifications(Timestamp nextRequest) throws DaoException {
		List<Notification> list = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		try{
			String query =	"select pn.id,pn.time,pn.idlist,l.name,l.iduser,u.email,pn.idproduct,p.name,pn.idpublicproduct,pp.name\n" +
					"from productsnotification pn\n" +
					"inner join lists l on pn.idlist = l.id\n" +
					"inner join users u on l.iduser = u.id\n" +
					"left join products p on pn.idproduct = p.id\n" +
					"left join publicproducts pp on pn.idpublicproduct = pp.id\n" +
					"where time <= "+dateFormat.format(nextRequest);
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Notification n;
			it.unitn.webprog2018.ueb.shoppinglist.entities.List l;
			User u;
			Product p;
			PublicProduct pp;
			int i;
			while(rs.next())
			{
				i = 1;
				n = new Notification();
				l = new it.unitn.webprog2018.ueb.shoppinglist.entities.List();
				u = new User();
				p = new Product();
				pp = new PublicProduct();
				
				n.setId(rs.getInt(i++));
				n.setTime(rs.getTimestamp(i++));
				l.setId(rs.getInt(i++));
				l.setName(rs.getString(i++));
				u.setId(rs.getInt(i++));
				u.setEmail(rs.getString(i++));
				Integer idprod = rs.getInt(i++);
				String nameprod = rs.getString(i++);
				Integer idpubprod = rs.getInt(i++);
				String namepubprod = rs.getString(i++);
				if(idprod != null){
					p.setId(idprod);
					p.setName(nameprod);
					n.setProduct(p);
				}
				else if(idpubprod != null){
					pp.setId(idpubprod);
					pp.setName(namepubprod);
					n.setProduct(pp);
				}
				else
					throw new DaoException("Notification without products");
				
				n.setUser(u);
				n.setList(l);
				list.add(n);
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
	
	@Override
	public List<Notification> getUnreadNotifications(Integer userId) throws DaoException {
		List<Notification> list = new ArrayList<>();
		try{
			String query =	"select pn.id,pn.time,pn.idlist,l.name,l.iduser,pn.idproduct,p.name,pn.idpublicproduct,pp.name\n" +
					"from productsnotification pn\n" +
					"inner join lists l on pn.idlist = l.id\n" +
					"inner join users u on l.iduser = u.id\n" +
					"left join products p on pn.idproduct = p.id\n" +
					"left join publicproducts pp on pn.idpublicproduct = pp.id\n" +
					"where pn.isread = 0 and pn.time <= now(1) and l.iduser = "+userId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Notification n;
			it.unitn.webprog2018.ueb.shoppinglist.entities.List l;
			User u;
			Product p;
			PublicProduct pp;
			int i;
			while(rs.next())
			{
				i = 1;
				n = new Notification();
				l = new it.unitn.webprog2018.ueb.shoppinglist.entities.List();
				u = new User();
				p = new Product();
				pp = new PublicProduct();
				
				n.setId(rs.getInt(i++));
				n.setTime(rs.getTimestamp(i++));
				l.setId(rs.getInt(i++));
				l.setName(rs.getString(i++));
				u.setId(rs.getInt(i++));
				Integer idprod = rs.getInt(i++);
				String nameprod = rs.getString(i++);
				Integer idpubprod = rs.getInt(i++);
				String namepubprod = rs.getString(i++);
				if(idprod != null){
					p.setId(idprod);
					p.setName(nameprod);
					n.setProduct(p);
				}
				else if(idpubprod != null){
					pp.setId(idpubprod);
					pp.setName(namepubprod);
					n.setProduct(pp);
				}
				else
					throw new DaoException("Notification without products");
				
				n.setUser(u);
				n.setList(l);
				list.add(n);
			}
			if(list.size() > 0)
			{
				query =	"update productsnotification pn\n" +
						"inner join lists l on pn.idlist = l.id\n" +
						"set pn.isread = 1\n" +
						"where pn.isread = 0 and l.iduser = "+userId;
				st.executeUpdate(query);
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
	
	@Override
	public Integer getNotificationCount(Integer userId) throws DaoException {
		try {
			String query = "select count(*) from productsnotification pn inner join lists l on pn.idlist = l.id where pn.time <= now(1) and pn.isread = 0 and l.iduser = "+userId;
			Statement st = this.getCon().createStatement();
			ResultSet rs = st.executeQuery(query);
			Integer count = 0;
			if(rs.first())
			{
				count = rs.getInt(1);
				
				rs.close();
				st.close();
			}
			return count;
		} catch (SQLException ex) {
			Logger.getLogger(UserDAOimpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new DaoException(ex);
		}
	}
	
}
