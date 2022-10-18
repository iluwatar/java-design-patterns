
/* 
Outputs:

      *
      **
      ***
      ****
 */
//Number - 01
import java.util.*;
 
public class Patterns {
   public static void main(String args[]) {
       int n = 4;
      
       for(int i=1; i<=n; i++) {
           for(int j=1; j<=i; j++) {
                   System.out.print("*");
           }
           System.out.println();
       }
   }
}

// Number -02

/*  
      this is a reverse of an triangle       
         ****
         ***
         **
         * 
 */
import java.util.*;
 
public class Patterns {
   public static void main(String args[]) {
       int n = 4;
      
       for(int i=n; i>=1; i--) {
           for(int j=1; j<=i; j++) {
                   System.out.print("*");
           }
           System.out.println();
       }
   }
}

// Number - 03 

/*            *
             **    
            ***  
           ****
 */
import java.util.*;
 
public class Patterns {
   public static void main(String args[]) {
       int n = 4;
      
       for(int i=n; i>=1; i--) {
           for(int j=1; j<i; j++) {
               System.out.print(" ");
           }
 
           for(int j=0; j<=n-i; j++) {
               System.out.print("*");
           }
           System.out.println();
       }
   }
}
