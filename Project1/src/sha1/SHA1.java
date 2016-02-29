package sha1;

/**

 @author tianb
 */
import java.io.RandomAccessFile;
import java.security.Security;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.*;
import java.security.MessageDigest;
import java.util.Calendar;

/**
 Basic IO example using SHA1
 */
public class SHA1
{

   public SHA1(String input) throws Exception
   {
      Security.addProvider(new BouncyCastleProvider());
      long sTime, eTime;

      RandomAccessFile raf = new RandomAccessFile(input, "r");

      int len = (int) raf.length();
      byte[] buffer = new byte[len];
      
      try
      {
         //prepare the input
         MessageDigest hash
               = MessageDigest.getInstance("SHA-1", "BC");
         hash.update(buffer);

         //proceed ....
         sTime = Calendar.getInstance().getTimeInMillis();
         byte[] digest = hash.digest();
         eTime = Calendar.getInstance().getTimeInMillis();

         System.out.println(">> SHA-1 Hash: "
               + new String(Hex.encode(digest)));
         System.out.println(">> Hash Time : " + (eTime - sTime) + "ms");
      }
      catch (NoSuchAlgorithmException e)
      {
         System.err.println("No such algorithm");
         e.printStackTrace();
      }
      catch (NoSuchProviderException e)
      {
         System.err.println("No such provider");
         e.printStackTrace();
      }
   }
}
