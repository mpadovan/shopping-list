/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entirely static class that can be used to obtain the server's IPv4 address.
 *
 * @author Giulia Carocari
 */
public class Network {
	
	public static String getServerAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			
			while (networkInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> addresses = networkInterfaces.nextElement().getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress add = addresses.nextElement();
					if (!add.isLinkLocalAddress() 
                         && !add.isLoopbackAddress()
                         && add instanceof Inet4Address) {
						return add.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
		}
		// if no other address is available, we return the loopback address
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException ex) {
			Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
		}
		// everything fails, i give up.
		return null;
	}
	
	public static String getDatabaseAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			
			while (networkInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> addresses = networkInterfaces.nextElement().getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress add = addresses.nextElement();
					if (add.isLoopbackAddress()
                         && add instanceof Inet4Address) {
						return add.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
}
