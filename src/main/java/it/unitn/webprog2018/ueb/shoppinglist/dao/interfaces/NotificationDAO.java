/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.dao.interfaces;

import it.unitn.webprog2018.ueb.shoppinglist.entities.Notification;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Giulia Carocari
 */
public interface NotificationDAO {
	
	/**
	 * Returns all notifications that have not yet been handled and that expire before the next request.
	 * 
	 * @param nextRequest time at which the next poll to the database will be performed
	 * @return a list of the notifications that will expire before the next poll
	*/
	public List<Notification> getNextNotifications(Timestamp nextRequest);
	
	/**
	 * Retrieves all notifications that expired since the last time the user has accessed the notification system.
	 * Also resets the user's unread notification count (aka his last access to the notifications).
	 * 
	 * @param userId id of the User that needs the notifications
	 * @return The notifications that the user needs to read
	 */
	public List<Notification> getUnreadNotifications(Integer userId);
	
	/**
	 * Retrieves the number of expired notifications the user has
	 * 
	 * @param userId Id of the user that needs the notifications
	 * @return The number of unread expired notifications for the user
	 */
	public Integer getNotificationCount(Integer userId);
	
	// Not necessary I guess
	// public boolean setLastAccess(Integer userId,)
}
