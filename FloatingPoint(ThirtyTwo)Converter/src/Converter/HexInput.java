package Converter;

import java.math.BigInteger;

public class HexInput extends Converter{
	
	private String[] hexArray;
	private String[] bit32Array;
	private String binary;

	@Override
	public void getInput() {
		do {
			System.out.print("Hex input: ");
			input = sc.nextLine();
		} while(!inputErrorChecker(input));
		
	}

	@Override
	public void convert() {
		System.out.println("Hex convert");
		hexArray = input.split("(?!^)");
		
		System.out.println();
		for(int i = 0; i < hexArray.length; i++) {
			System.out.println(hexArray[i] + " = " + Integer.parseInt(hexArray[i], 16));
		}
		System.out.println();
		
		
		binary = new BigInteger(input, 16).toString(2);
		
		if(binary.length() < 32) {
			binary = addLeadingZeros(binary);
		}
		
		System.out.println();
		System.out.println(binary + "|" + binary.length());
		System.out.println();
		
		System.out.println();
		BinaryToDecimal btc = new BinaryToDecimal();
		result = btc.startConversion(binary);
		System.out.println();
	}

	//Must be 8 in size.
	//Must be hex only. (0 - F)
	public boolean inputErrorChecker(String input) {
		
		if(input.matches("^[0-9A-F]{8}$")) {
			System.out.println("Its hex.");
			return true;
		}
		
		else {
			System.out.println("Wrong input!");
			return false;
		}

	}
	
	private String addLeadingZeros(String value) {
		
		while(value.length() < 32) {
			value = "0" + value;
		}
		
		return value;
	}

}
