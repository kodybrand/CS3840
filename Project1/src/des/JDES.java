package des;

/**

 @author tianb
 */
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Calendar;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.util.encoders.*;

public class JDES
{

   public JDES(String initVal, String key, String inputFile, String outputFile)
   {

      long sTime;
      long eTime;
      try
      {
         RandomAccessFile rafread = new RandomAccessFile(inputFile, "r");
         RandomAccessFile rafwrite = new RandomAccessFile(outputFile, "rw");

         // This generate a DES key based on your key
         SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
         DESKeySpec mydeskeyspec = new DESKeySpec(key.getBytes());
         SecretKey myDesKey = keyfactory.generateSecret(mydeskeyspec);

         System.out.println("My Key: " + key);
         System.out.println("DES Key: " + new String(Hex.encode(myDesKey.getEncoded())));

         Cipher desCipher;
         byte data[] = new byte[8];
         byte enData[] = null;
         byte deData[] = null;

         // Create the cipher 
         desCipher = Cipher.getInstance("DES/CBC/NoPadding");

         // Initialize the cipher for encryption
         IvParameterSpec iv = new IvParameterSpec(hexStringToByteArray(initVal));

         desCipher.init(Cipher.ENCRYPT_MODE, myDesKey, iv);

         //sensitive information
         String message = "";
         byte end [];
         boolean first = true;
         while (rafread.read(data) != -1)
         {
            // data is set with the 8bits of stuff
            byte[] ivprms = iv.getIV();

            byte xord[] = new byte[8];

            int i = 0;
            while (i < 8)
            {
               if (first == true || enData == null)
               {
                  xord[i] = (byte) (ivprms[i] ^ data[i]);
               }
               else
               {
                  xord[i] = (byte) (enData[i] ^ data[i]);
               }
               i++;
            }
            first = false;

            // Doing Encryption -> enData = IV for next go round
            enData = desCipher.update(xord);
            rafwrite.write(enData);

            message = message + new String(Hex.encode(enData));
            System.out.println(new String(Hex.encode(enData)));
         }

         rafwrite.write(desCipher.doFinal());
         //System.out.println()
         System.out.println("Message : " + desCipher.doFinal().length);

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

   }

   public static byte[] hexStringToByteArray(final String s)
   {
      if (s == null || (s.length() % 2) == 1)
      {
         throw new IllegalArgumentException();
      }
      final char[] chars = s.toCharArray();
      final int len = chars.length;
      final byte[] data = new byte[len / 2];
      for (int i = 0; i < len; i += 2)
      {
         data[i / 2] = (byte) ((Character.digit(chars[i], 16) << 4) + Character.digit(chars[i + 1], 16));
      }
      return data;
   }

   private byte[] hexStringToByteArray(IvParameterSpec iv)
   {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

}
