package ras;

/**

 @author tianb
 */
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Calendar;
import org.bouncycastle.util.encoders.*;

import javax.crypto.Cipher;

public class JRSA {
  public static void main(String[] args) throws Exception {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    
    long sTime, eTime;

    byte[] input = "abcdefghijklmnopqrstuvwxyz0123456789".getBytes();
    Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
    SecureRandom random = new SecureRandom();
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

    generator.initialize(1024, random);
    KeyPair pair = generator.generateKeyPair();
    Key pubKey = pair.getPublic();
    Key privKey = pair.getPrivate();

    cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);
    sTime = Calendar.getInstance().getTimeInMillis();
    byte[] cipherText = cipher.doFinal(input);
    eTime = Calendar.getInstance().getTimeInMillis();
    System.out.println("cipher: " + new String(Hex.encode(cipherText)));
    System.out.println("Encryption Time : " + (eTime - sTime) + "ms");
    
    cipher.init(Cipher.DECRYPT_MODE, privKey);
    sTime = Calendar.getInstance().getTimeInMillis();
    byte[] plainText = cipher.doFinal(cipherText);
    eTime = Calendar.getInstance().getTimeInMillis();
    System.out.println("plain : " + new String(plainText));
    System.out.println("Decryption Time : " + (eTime - sTime) + "ms");
  }
}