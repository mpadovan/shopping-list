/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webprog2018.ueb.shoppinglist.utils;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class for encrypting purposes. It is used to encrypt usernames to be used in cookies.
 */
public class CookieCipher {

	private static final String KEY = "b#e^s&t*g!r5o2up"; //must be with exactly 16 characters
	private static final String ALGORITHM = "AES";
	private static SecretKeySpec secretKey;
	private static Cipher cipher;

	/**
	 * Encrypts a string
	 * @param value String to be encrypted
	 * @return the encrypted string
	 */
	public static String encrypt(String value) {
		generateKey();
		byte[] output = new byte[value.length()];
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			output = cipher.doFinal(value.getBytes("UTF-8"));
		} catch (Exception e) {
			System.err.println("Error during encription...\n" + e.toString());
		}
		// Making it URL and (linux) file name safe
		return Base64.getEncoder().encodeToString(output).replace('+', '-').replace('/', '_').replaceAll("%", "%25").replaceAll("\n", "%0A").replaceAll("=", "%3D");
	}

	/**
	 * Decrypts a string encrypted using this cypher
	 * @param value the encrypted string to be decryped
	 * @return the decryped string
	 */
	public static String decrypt(String value) {
		generateKey();
		value = value.replaceAll("%0A", "\n").replaceAll("%25", "%").replaceAll("%3D","=").replace('_', '/').replace('-', '+');
		byte[] output = new byte[value.length()];
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			output = cipher.doFinal(Base64.getDecoder().decode(value));
		} catch (Exception e) {
			System.err.println("Error during decription...\n" + e.toString());
		}
		return new String(output);
	}

	private static void generateKey() {
		byte[] keyByte = new byte[KEY.length()];
		try {
			keyByte = KEY.getBytes("UTF-8");
			secretKey = new SecretKeySpec(keyByte, ALGORITHM);
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (Exception e) {
			System.err.println("Error during key generation...\n" + e.toString());
		}
	}
}
