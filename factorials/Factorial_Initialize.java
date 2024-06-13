package factorials;

import java.math.BigInteger;

public class Factorial_Initialize {
	
	public static void main(String[] args) {
		
		BigFactorial bf = new BigFactorial();
		BigInteger value = bf.calculate(new BigInteger("1000000"));
		
		//Print the value (this might take some extra time, remove this statement if you are testing speed)
		System.out.println(value);
		
	}
	
}
