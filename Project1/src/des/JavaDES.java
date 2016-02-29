/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

/**

 @author tianb
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.util.encoders.*;

public class JavaDES
{

   public JavaDES(String initVal, String key, String inf, String outf) throws FileNotFoundException, IOException
   {

      String IV = initVal;
      String KEY = key;
      String INFILE = inf;
      String OUTFILE = outf;

      RandomAccessFile inFile = new RandomAccessFile(INFILE, "rw");
      RandomAccessFile outFile = new RandomAccessFile(OUTFILE, "rw");

      try
      {

         // This generate a DES key based on your key
         SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
         DESKeySpec mydeskeyspec = new DESKeySpec(hexStringToByteArray(KEY));
         SecretKey myDesKey = keyfactory.generateSecret(mydeskeyspec);

         Cipher desCipher;

         // Create the cipher 
         desCipher = Cipher.getInstance("DES/CBC/NoPadding");

         byte data[] = new byte[8];
         byte enData[] = hexStringToByteArray(IV);
         byte deData[] = hexStringToByteArray(IV);
         byte viData[] = hexStringToByteArray(IV);

         int file_size = (int) inFile.length();

         IvParameterSpec iv = new IvParameterSpec(hexStringToByteArray(IV));

         try
         {
            byte xord[] = new byte[8];
            while (inFile.read(data) != -1)
            {
               desCipher.init(Cipher.ENCRYPT_MODE, myDesKey, iv);

               int i = 0;
               for (byte b : enData)
               {
                  xord[i] = (byte) (b ^ data[i++]);
               }

               //desCipher.update(xord);
               outFile.write(desCipher.update(xord)); 
            }
         }
         catch (Exception e)
         {

         }

         byte xord[] = new byte[8];
         while (outFile.read(data) != -1)
         {
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey, iv);
            deData = desCipher.update(data);

            int i = 0;
            for (byte b : deData)
            {
               xord[i] = (byte) (b ^ viData[i++]);
            }

               //desCipher.update(xord);
            //outFile.write(desCipher.update(xord));
            System.out.println("Text Decryted : " );
         }

      }
      catch (Exception e)
      {

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
