package Converter;

import java.math.BigDecimal;
import java.math.BigInteger;


public class BinaryToDecimal {
	public final int BIAS_NUMBER = 127;
	
	private String signBit;
	private String exponentBits;
	private String mantissaBits;
	
	private int exponent;
	private String mantissa;
	
	private String answer;
	
	private int specialCase;
	
	private boolean infinity,
	                zero,
	                sNaN,
	                qNaN;
	
	
	public BinaryToDecimal() {
		this.signBit = "";
		this.exponentBits = "";
		this.mantissaBits = "";
		this.mantissa = "1.";
		this.specialCase = 0;
		
		this.infinity = false;
		this.zero = false;
		this.sNaN = false;
		this.qNaN = false;
	}
	

	/**
	 * Accepts a 32 bit binary IEEE-754 binary partition and returns its decimal / special value.
	 * @param binary - the IEEE-754 binary partition value.
	 * @return the decimal / special value
	 */
	public String startConversion(String binary) {
		
		//Get sign bit (first bit)
		//Get the power (next 8 bits)	
		//Get mantissa (Remaining 32 bits)
		//(S)ign (E)xponent (M)antissa
		getSEM(binary);
		
		//calculate exponent
		calculateExponentValue();
		
		//get realy manitssa
		calculateMantissaValue();
		
		//caluclate decimal/special value;
		if(!checkForSpecialCase()) {
			try {
				calculateDeciamlorSpecial();
			}
			catch(Exception e){
				//e.printStackTrace();
				System.out.println("\n\n\n");
				System.out.println("Number too big!");
				System.out.println("Mantissa: " + mantissa);
				System.out.println("Exponent: " + exponent);
				answer="Too big!";
			}
			
		}
		
		else {
			//Special case.
			if(infinity) {
				System.out.println("infinity!");
				answer="Infinity";
			}
			
			else if(zero) {
				System.out.println("Zero!");
				answer="0";
			}
			
			else if(sNaN) {
				System.out.println("sNaN");
				answer="sNaN";
			}
			
			else if(qNaN) {
				System.out.println("qNaN");
				answer="qNaN";
			}
			
			else {
				System.out.println("Special error.");
			}
		}


		
		//Checck if negative or positive.
		if(signBit.equals("1") && 
		   !answer.equals("Too big!") &&
		   !sNaN &&
		   !qNaN) {
			answer = "-" + answer;
		}
		return answer;
	}
	
	private boolean checkForSpecialCase() {
		// TODO Auto-generated method stub
		boolean flag = false;
		
		//Infinity
		if(exponentBits.matches("[1]+") &&
		   mantissaBits.matches("[0]+")) {
			infinity = true;
			flag = true;
		}
		
		//Zero
		else if(exponentBits.matches("[0]+") &&
				mantissaBits.matches("[0]+")) {
			zero = true;
			flag = true;
		}
		
		//sNan
		else if(exponentBits.matches("[1]+") &&
				mantissaBits.charAt(0) == '0' &&
				!mantissaBits.substring(1).matches("[0]+")) {
			sNaN = true;
			flag = true;
		}
		
		//qNaN
		else if(exponentBits.matches("[1]+") &&
				mantissaBits.charAt(0) == '1' &&
				!mantissaBits.substring(1).matches("[0]+")) {
			qNaN = true;
			flag = true;
		}
		
		return flag;
	}


	private void calculateDeciamlorSpecial() {
		int pos = 0;
		char cArr[];
		String newMantissa = "";
		
		
		//No moving of decimal point.
		if(exponent == 0) {
			convertBinaryToDecimal(mantissa);
		}
		
		//Positive, move decimal to the right.
		else if (exponent > 0) {
			pos = (exponent + 2) - 1; //-1 for index.
			
			if(pos > 23) {
				addMoreZeroes(pos);
			}
			
			cArr = mantissa.toCharArray();
			
			
			if(cArr[pos] == '0') {
				cArr[pos] = 'Z';
			}
			
			else {
				cArr[pos] = 'O';
			}
			
			for(int i = 0; i < cArr.length; i++) {
				if(cArr[i] == 'Z' || cArr[i] == 'O' ) {
					if(cArr[i] == 'Z')
						newMantissa+= "0.";
					else
						newMantissa+="1.";
				}

				else {
					if(cArr[i] != '.')
						newMantissa+= cArr[i];
				}
			}
			
			System.out.println("Applide exponent to mantissa: " + newMantissa);
			convertBinaryToDecimal(newMantissa);
		}
		
		//Negative, move decimal to the left
		else {
			String negMantissa = "";
			int max = Math.abs(exponent);
			int ctr = 0;
			
			//add zero to head of mantissa.
			while(ctr < max) {
				
				if(ctr == 0) 
					negMantissa = "1" + mantissaBits;
				else {
					negMantissa = "0" + negMantissa;
				}
				
				ctr++;
			}
			negMantissa = "0." + negMantissa;
			System.out.println("Negative mantissa (exponent applied): " + negMantissa);
			convertBinaryToDecimal(negMantissa);
		}
		
	}
	
	//adds trailing zeroes.
	private void addMoreZeroes(int pos) {
		// TODO Auto-generated method stub
		int ctr = 0;
		while(ctr < pos) {
			mantissa+="0";
			ctr++;
		}
		System.out.println("New Mantissa size: " + mantissa.length());
		System.out.println("Mantissa: " + mantissa);
		
	}


	//converts the given binary number into decimal.
	private void convertBinaryToDecimal(String newMantissa) {
		String[] value;
		BigInteger rightSide, leftSide;
		String answer ="";
		
		
		//Split the left side and right side
		value = newMantissa.split("\\.");
		System.out.println("left side: " + value[0]); 
		System.out.println("right side: " + value[1]);
		
		
		//Perform conversion left side
		if(value[0].matches("[0]+")) {
			System.out.println("(LEFT)Only zeroes, dont do anything.");
			answer+="0";
		}
		else {
			leftSide = new BigInteger(value[0], 2);
			answer += leftSide;
			System.out.println("Left side value: " + leftSide);			
		}

		
		
		//Perform conversion RIGHT side
		if(value[1].matches("[0]+")) { //if only zeroes, dont do anything.
			System.out.println("(RIGHT)Only zeroes, dont do anything.");
			answer+=".0";
		}
		else {
			String right = performRightConversion(value[1]);
			answer += right;
		}

		
		this.answer = answer;
	}
	
	private String performRightConversion(String value) {
		int mult = 2;
		int index = 0; //index for char.
		int num = 0; //holder of the converted string.
		BigDecimal sum = new BigDecimal("0.0");
		String result = "";
		
		System.out.println("rightSide length: " + value.length());
		while(index < value.length()) {
			//Convert the 
			num = Integer.parseInt(value.charAt(index)+"");
			//System.out.println(num);
			
			if(num != 0) {
				//sum += (1.0 * num) / mult;
				
				sum = sum.add(new BigDecimal((1.0 * num) / mult));
			}
			
			mult *= 2;
			index++;
		}
		System.out.println("Right side value: " + sum);
		
		//remove first 0 
		result=sum+"";
		result = result.substring(1);
		return result;
	}


	private void calculateMantissaValue() {
		mantissa += mantissaBits;		
		System.out.println("Real Mantissa: " + mantissa);
	}
	
	
	//Calculates the IEEE-754 single precision(32 bit) exponent value given the exponent bit.
	private void calculateExponentValue() {
		int ePrimeValue = Integer.parseInt(exponentBits, 2);
		exponent = ePrimeValue - BIAS_NUMBER;
		System.out.println("Real exponent: " + exponent);
	}


	//Organizes bits to Sign(1) | Exponent* (8) | Mantissa (23)
	public void getSEM(String binary) {

		for(int i = 0; i < 32; i++) {
			if(i == 0) {
				signBit += binary.charAt(i);
			}
			
			else if (i >= 1 && i <= 8) {
				exponentBits += binary.charAt(i);
			}
			
			else {
				mantissaBits += binary.charAt(i);
			}
		}
		
		System.out.println("Sign: " + signBit);
		System.out.println("Exponent: " + exponentBits);  
		System.out.println("Mantissa bits: " + mantissaBits);
	}



}