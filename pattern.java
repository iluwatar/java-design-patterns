import java.util.Scanner;

public class JigSawAcademy

{

public static void main(String[] args)

{

Scanner sc = new Scanner(System.in);

System.out.println(“Enter the number of rows: “);

int rows = sc.nextInt();

for (int i= 0; i<= rows-1 ; i++)

{

for (int j=0; j <i; j++)

{

System.out.print(” “);

}

for (int k=i; k<=rows-1; k++) { System.out.print(“*” + ” “); } System.out.println(“”); } for (int i= rows-1; i>= 0; i–)

{

for (int j=0; j< i ;j++)

{

System.out.print(” “);

}

for (int k=i; k<=rows-1; k++)

{

System.out.print(“*” + ” “);

}

System.out.println(“”);

}

sc.close();

}

}
