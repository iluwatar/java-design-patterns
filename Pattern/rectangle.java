/*  
Output: 
         *****
         *****   
         *****
         *****
         *****
 */
 
// This is the implimantation of java pattern question implimentation asked in many company.
import java.util.*;
 
public class Patterns {
   public static void main(String args[]) {
       int n = 5;
       int m = 4;
       for(int i=0; i<n; i++) {
           for(int j=0; j<m; j++) {
               System.out.print("*");
           }
           System.out.println();
       }
   }
}

