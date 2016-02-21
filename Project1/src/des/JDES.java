package des;

/**

 @author tianb
 */
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.bouncycastle.util.encoders.*;
 
public class JDES
{    
	public static void main(String[] argv) {
 
      long sTime;
      long eTime;
      
		try{
 
			// This generate a DES key based on your key
			String key = "abcdefgh";
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec mydeskeyspec = new DESKeySpec(key.getBytes());
			SecretKey myDesKey = keyfactory.generateSecret(mydeskeyspec);
			
			// This automatically generate a DES key
		    //KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		    //SecretKey myDesKey = keygenerator.generateKey();
			
			System.out.println("My Key: " + key);
			System.out.println("DES Key: " + new String(Hex.encode(myDesKey.getEncoded())));
			
		    Cipher desCipher;
 
		    // Create the cipher 
		    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
 
		    // Initialize the cipher for encryption
		    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
 
		    //sensitive information
		    byte[] text = "12345678".getBytes();
 
		    System.out.println("Text [Hex Format] : " + new String(Hex.encode(text)) + " length = "+text.length);
		    System.out.println("Text : " + new String(text));
 
		    // Encrypt the text
          sTime = Calendar.getInstance().getTimeInMillis();
		    byte[] textEncrypted = desCipher.doFinal(text);
          eTime = Calendar.getInstance().getTimeInMillis();
 
		    System.out.println("Text Encryted : " + new String(Hex.encode(textEncrypted)));
          System.out.println("Encryption Time : " + (eTime - sTime) + "ms");
  
		    // Initialize the same cipher for decryption
          sTime = Calendar.getInstance().getTimeInMillis();
		    desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
          eTime = Calendar.getInstance().getTimeInMillis();
 
		    // Decrypt the text
		    byte[] textDecrypted = desCipher.doFinal(textEncrypted);
 
		    System.out.println("Text Decryted : " + new String(textDecrypted));
          System.out.println("Decryption Time : " + (eTime - sTime) + "ms");
 
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch(NoSuchPaddingException e){
			e.printStackTrace();
		}catch(InvalidKeyException e){
			e.printStackTrace();
		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		} 
		catch(InvalidKeySpecException e){
			e.printStackTrace();
		}
 
	}
	
	public static byte [] hexStringToByteArray (final String s) {
    if (s == null || (s.length () % 2) == 1)
      throw new IllegalArgumentException ();
    final char [] chars = s.toCharArray ();
    final int len = chars.length;
    final byte [] data = new byte [len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit (chars[i], 16) << 4) + Character.digit (chars[i + 1], 16));
    }
    return data;
  }
	
}
