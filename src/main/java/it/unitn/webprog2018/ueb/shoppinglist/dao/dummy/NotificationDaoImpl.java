/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.dummy;

import it.unitn.webprog2018.ueb.shoppinglist.dao.exceptions.DaoException;
import it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces.NotificationDAO;
import it.unitn.webprog2018.ueb.shoppinglist.entities.Notification;
import it.unitn.webprog2018.ueb.shoppinglist.entities.User;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giulia Carocari
 */
public class NotificationDaoImpl implements NotificationDAO {

	private final DAOFactoryImpl factory;
	private final List<Notification> notifications;
	private final boolean[] read = new boolean[3];
	private final boolean[] sent = new boolean[3];

	public NotificationDaoImpl(DAOFactoryImpl aThis) {
		factory = aThis;
		notifications = new LinkedList<>();

		Notification n = new Notification();
		n.setId(1);
		n.setList(new it.unitn.webprog2018.ueb.shoppinglist.entities.List());
		n.getList().setId(0);
		n.setUser(new User());
		n.getUser().getId();
		try {
			n.setProduct(factory.getProductDAO().getByUser(1).get(1));
		} catch (DaoException ex) {
			Logger.getLogger(NotificationDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		n.setTime(new Timestamp(System.currentTimeMillis() + 62 * 1000));

		Notification n2 = new Notification();
		n2.setId(1);
		n2.setList(new it.unitn.webprog2018.ueb.shoppinglist.entities.List());
		n2.getList().setId(1);
		n2.setUser(new User());
		n2.getUser().getId();
		try {
			n2.setProduct(factory.getProductDAO().getByUser(1).get(2));
		} catch (DaoException ex) {
			Logger.getLogger(NotificationDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		n2.setTime(new Timestamp(System.currentTimeMillis() + 95 * 1000));

		Notification n3 = new Notification();
		n3.setId(2);
		n3.setList(new it.unitn.webprog2018.ueb.shoppinglist.entities.List());
		n3.getList().setId(1);
		n3.setUser(new User());
		n3.getUser().getId();
		try {
			n3.setProduct(factory.getPublicProductDAO().getById(3));
		} catch (DaoException ex) {
			Logger.getLogger(NotificationDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		n3.setTime(new Timestamp(System.currentTimeMillis() + 20 * 1000));

		notifications.add(n);
		notifications.add(n2);
		notifications.add(n3);
	}

	@Override
	public List<Notification> getNextNotifications(Timestamp nextRequest) {
		List<Notification> ret = new LinkedList<>();
		for (Notification n : notifications) {
			if (!sent[notifications.indexOf(n)] && n.getTime().before(nextRequest)) {
				ret.add(n);
				sent[notifications.indexOf(n)] = true;
			}
		}
		return ret;
	}

	@Override
	public List<Notification> getUnreadNotifications(Integer userId) {
		List<Notification> ret = new LinkedList<>();
		for (Notification n : notifications) {
			if (!read[notifications.indexOf(n)] && userId.equals(n.getUser().getId())) {
				ret.add(n);
				read[notifications.indexOf(n)] = true;
			}
		}
		return ret;
	}

	@Override
	public Integer getNotificationCount(Integer userId) {
		int count = 0;
		for (Notification n : notifications) {
			if (!read[notifications.indexOf(n)] && userId.equals(n.getUser().getId())) {
				count++;
			}
		}
		return count;
	}

}
