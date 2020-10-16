// Java Program to Print Square Star Pattern
import java.util.Scanner;

public class SquareStar1 {
	private static Scanner sc;
	public static void main(String[] args) 
	{
		int side, i, j;
		sc = new Scanner(System.in);
		
		System.out.print(" Please Enter any Side of a Square : ");
		side = sc.nextInt();	
			
		for(i = 1; i <= side; i++)
		{
			for(j = 1; j <= side; j++)
			{
				System.out.print("*"); 
			}
			System.out.print("\n"); 
		}	
	}
}
