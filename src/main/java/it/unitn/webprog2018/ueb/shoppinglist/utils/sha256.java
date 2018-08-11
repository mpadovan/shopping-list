package it.unitn.webprog2018.ueb.shoppinglist.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author Michele
 */
public class sha256 {
	
	public static String doHash(String password)/*fa l'hash di una String in sha256 e la converte in base64*/{
		byte[] encodedhash = null;
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
		}
		catch(NoSuchAlgorithmException e){ System.err.println("Error during Hashing...\n"+e.toString()); }
		return Base64.getEncoder().encodeToString(encodedhash);
	}
	
	public static boolean checkPassword(String password,String hash)/*controlla una password con un hash*/{
		return doHash(password).equals(hash);
	}
}
