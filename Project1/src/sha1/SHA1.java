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

   public static void main(String[] args) throws Exception
   {
      Security.addProvider(new BouncyCastleProvider());
      byte input[] =
      {
         0x30, 0x31, 0x32, 0x33, 0x34
      };
      byte b[] = null;
      long sTime, eTime;

      RandomAccessFile raf = new RandomAccessFile("c:/test1.txt", "r");

      int len = (int) raf.length();
      byte[] buffer = new byte[len];
      raf.read(buffer);

      try
      {
         //prepare the input
         
         System.out.println(Hex.toHexString(buffer));
         MessageDigest hash
               = MessageDigest.getInstance("SHA-1", "BC");
         hash.update(buffer);

         //proceed ....
         sTime = Calendar.getInstance().getTimeInMillis();
         byte[] digest = hash.digest();
         eTime = Calendar.getInstance().getTimeInMillis();

         //show us the result
         System.out.println("input: "
               + new String(Hex.encode(buffer)));
         System.out.println("result: "
               + new String(Hex.encode(digest)));
         System.out.println("Hash Time : " + (eTime - sTime) + "ms");
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
