/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.websocket;

import java.util.Map;
import javax.websocket.Session;

/**
 *
 * @author Giulia Carocari
 */
public abstract class SessionHandler {
	Map<Integer, Session> sessions;
}
