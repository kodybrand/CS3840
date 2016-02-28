package rsa;

/**

 @author tianb
 */
import java.io.RandomAccessFile;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Calendar;
import javax.crypto.Cipher;
import org.bouncycastle.util.encoders.*;


public class JRSA
{

   public JRSA (String inputFile) throws Exception
   {
      Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

      long sTime, eTime;
      RandomAccessFile raf = new RandomAccessFile(inputFile, "r");

      int len = (int) raf.length();
      byte[] input = new byte[len];
      raf.read(input);
      int l = input.length;
      
      byte[] mine = "s".getBytes();

      Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
      SecureRandom random = new SecureRandom();
      KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

      generator.initialize(l, random);
      KeyPair pair = generator.generateKeyPair();
      Key pubKey = pair.getPublic();
      Key privKey = pair.getPrivate();

      cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);
      sTime = Calendar.getInstance().getTimeInMillis();
      byte[] cipherText = cipher.doFinal(input);
      eTime = Calendar.getInstance().getTimeInMillis();
      System.out.println(">> Cipher Text: " + new String(Hex.encode(cipherText)));
      System.out.println(">> Encryption Time : " + (eTime - sTime) + "ms");

      cipher.init(Cipher.DECRYPT_MODE, privKey);
      sTime = Calendar.getInstance().getTimeInMillis();
      byte[] plainText = cipher.doFinal(cipherText);
      eTime = Calendar.getInstance().getTimeInMillis();
      System.out.println(">> plain : " + new String(plainText));
      System.out.println(">> Decryption Time : " + (eTime - sTime) + "ms");
   }
}
