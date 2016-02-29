
import sha1.SHA1;
import rsa.JRSA;
import des.JDES;

/**

 @author Kody
 */
public class Project1
{

   private static String IV = null;
   private static String KEY = null;
   private static String inputFile = null;
   private static String outputFile = null;

   public static void main(String[] args)
   {
      Project1 p1 = new Project1();
      p1.initalize(args);
   }

   private void initalize(String[] args)
   {
      try
      {
         IV = args[0];
         KEY = args[1];
         inputFile = args[2];
         outputFile = args[3];

//         System.out.println("--- Program 1 : Encryption / Decryption");
//         System.out.println("> IV : " + IV);
//         System.out.println("> KEY: " + KEY);
//         System.out.println("> Input File : " + inputFile);
//         System.out.println("> Output File : " + outputFile + "\n");
         desSection();
         //rsaSection();
         //sha1Section();
      }
      catch (Exception e)
      {
         System.out.println("Error : " + e.getMessage());
      }
   }

   private void desSection()
   {
      System.out.println("-- Starting DES Section");
      try
      {
         JDES des = new JDES(IV, KEY, inputFile,outputFile);
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
      }
   }

   private void rsaSection()
   {
      System.out.println("\n-- Starting RSA Section");
      try
      {
         System.out.println("> InputFile : " + inputFile);
         JRSA rsa = new JRSA(inputFile);
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
      }
   }

   private void sha1Section()
   {
      System.out.println("\n-- Starting SHA-1 Section");
      try
      {
         System.out.println("> InputFile : " + inputFile);
         SHA1 sha = new SHA1(inputFile);
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
      }
   }

}
