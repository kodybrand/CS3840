
package sha1;

/**

 @author tianb
 */
import java.security.Security;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.*;
import java.security.MessageDigest;
import java.util.Calendar;


/**
 * Basic IO example using SHA1
 */
public class SHA1 {
  public static void main(String[] args) throws Exception {
    Security.addProvider(new BouncyCastleProvider());        
    byte input[] = {0x30, 0x31, 0x32, 0x33, 0x34};
    long sTime, eTime;

      try
      {
            //prepare the input
            MessageDigest hash =
               MessageDigest.getInstance("SHA-1", "BC");
            hash.update(input);

            //proceed ....
            sTime = Calendar.getInstance().getTimeInMillis();
            byte[] digest = hash.digest();
            eTime = Calendar.getInstance().getTimeInMillis();

            //show us the result
            System.out.println("input: " +
                   new String(Hex.encode(input)));
            System.out.println("result: " +
                   new String(Hex.encode(digest)));
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

