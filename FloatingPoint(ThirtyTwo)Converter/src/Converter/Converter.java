package Converter;

import java.util.Scanner;

public abstract class Converter {
	protected Scanner sc = new Scanner(System.in);
	protected String input;
	protected String result;
	
	public void start() {
		getInput();
		convert();
		displayResult();
	}
	
	protected abstract void getInput();
	protected abstract void convert();
	protected abstract boolean inputErrorChecker(String input);
	
	
	public void displayResult(){
		System.out.println("\n\n\n");
		System.out.println("Resulting decimal: " + result);
	}
	
	public static Converter getConverter(int input) {
		// TODO Auto-generated method stub
		
		if(input == 1) {
			return new HexInput();
		}
		
		else {
			return new BinaryInput();
		}
		
	}

}
