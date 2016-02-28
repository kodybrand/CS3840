
/**

 @author Kody
 */
public class Project1
{

   private static String VI = null;
   private static  String KEY = null;
   private static  String inputFile = null;
   private static  String outputFile = null;

   public static void main(String[] args)
   {

      try
      {
         VI = args[0];
         KEY = args[1];
         inputFile = args[2];
         outputFile = args[3];

         System.out.println("--- Program 1 : Encryption / Decryption");
         System.out.println("> VI : " + VI);
         System.out.println("> KEY: " + KEY);
         System.out.println("> Input File : " + inputFile);
         desSection();
      }
      catch (Exception e)
      {
         System.out.println("Error : " + e.getMessage());
      }
   }

   private void desSection()
   {
      System.out.println("-- Starting DES Section");
   }

}
