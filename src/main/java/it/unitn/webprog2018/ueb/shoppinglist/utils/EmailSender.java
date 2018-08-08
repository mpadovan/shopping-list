/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.utils;

import java.util.Date;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Utility class for sending simple text messages using emails.
 * This action is performed through the gmail smtp server.
 *
 * @author Giulia Carocari
 */
public class EmailSender {
	private static String SENDER_MAIL = "noreply.shoppinglist2018@gmail.com";
	private static String SENDER_PW = "gyociqpbhiieqrkm";
	private static final String HOST = "smtp.gmail.com";
	private static final Integer PORT = 465;
	
	public EmailSender() {		
	}
	
	/**
	 * Method for sending simple text email messages.
	 * 
	 * @param recipient Recipient of the message
	 * @param subject Subject of the email
	 * @param text String with the message to be sent
	 * @return Returns true if the email is successfully sent, false otherwise.
	 */
	public boolean send(String recipient, String subject, String text) {
		Email email = new SimpleEmail();
		email.setHostName(HOST);
		email.setSmtpPort(PORT);
		email.setAuthentication(SENDER_MAIL, SENDER_PW);
		email.setSSL(true);
		try {
			email.setFrom(SENDER_MAIL);
			email.setSubject(subject);
			email.setMsg(text);
			email.addTo(recipient);		
			email.setSentDate(new Date());
			email.send();
		} catch (EmailException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
}
	
