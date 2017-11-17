import java.util.Scanner;

import Converter.Converter;

public class Driver {

	public static void main(String[] args) {
		Converter converter;
		int choice;
		Scanner sc = new Scanner(System.in);
		System.out.println("1 - Hex");
		System.out.println("2 - Binary");
		
		//Choose whether 8-digit hex input OR 32-bit partition binary
		System.out.print("Choice of input: ");
		choice = sc.nextInt();
		
		
		//Convert
		converter = Converter.getConverter(choice);
		converter.start();
		

	}


}
