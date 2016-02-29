package des;

/**

 @author tianb
 */
import java.io.RandomAccessFile;
import javax.crypto.Cipher;
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
         RandomAccessFile rafout = new RandomAccessFile("decrypted.txt", "rw");

         // This generate a DES key based on your key
         SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
         DESKeySpec mydeskeyspec = new DESKeySpec(hexStringToByteArray(key));
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
         int fileSize = (int) rafread.length();
         System.out.println("FileSize = " + fileSize + " :: " + (fileSize / 8));

         
         byte[] message = new byte[fileSize + 8];
         int loc = 0;
         boolean first1 = true;
         while (rafread.read(data) != -1)
         {
            // data is set with the 8bits of stuff
            byte[] ivprms = iv.getIV();

            byte xord[] = new byte[8];

            int i = 0;
            while (i < 8)
            {
               if (first1 == true || enData == null)
               {
                  xord[i] = (byte) (ivprms[i] ^ data[i]);
               }
               else
               {
                  xord[i] = (byte) (enData[i] ^ data[i]);
               }
               i++;
            }
            first1 = false;

            // Doing Encryption -> enData = IV for next go round
            enData = desCipher.update(xord);
            for (int w = 0; w < enData.length; w++)
            {
               message[loc] = enData[w];
               loc++;
            }
         }
         System.out.println(message.length);
         desCipher.doFinal();
         rafwrite.write(message);
         //System.out.println()
         System.out.println("Message : " + desCipher.doFinal().length);
         
         desCipher.init(Cipher.DECRYPT_MODE, myDesKey, iv);
         
         boolean first2 = true;
         
         System.out.println(rafwrite.length());
         
         int dFileSize = (int)rafwrite.length();
         int dNumberOfBlocks = ((int)rafwrite.length()/8);
         int dExtraBlocks = dFileSize % 8;
         byte [] message1 = new byte[dFileSize + (8 - ( dFileSize % 8))];
         byte [] oldCT = new byte[8];
                 
         for(int q = 0; q < dNumberOfBlocks; q++) {
            rafwrite.read(data);
            deData = desCipher.update(data);
            oldCT = data;
            byte xord2[] = new byte[8];
            byte[] ivprms = iv.getIV();
            boolean firstLoop = true;
            for(int k = 0; k < 8; k++) {
               if(firstLoop) {
                  xord2[k] = (byte) (ivprms[k] ^ data[k]);
               } else {
                  xord2[k] = (byte) (oldCT[k] ^ data[k]);
               }
            }
            message1 = xord2;
            rafout.write(xord2);
         }
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

}
