package Converter;

public class BinaryInput extends Converter{

	@Override
	public void getInput() {
		//Must be 8 in size
		//1 for Sign bit
		//8 for E*
		//23 for mantissa.
		System.out.println("Binary input");
		
	}

	@Override
	public void convert() {
		System.out.println("Binary convert");
		
	}

	@Override
	public boolean inputErrorChecker(String input) {
		// TODO Auto-generated method stub
		return false;
	}

}
