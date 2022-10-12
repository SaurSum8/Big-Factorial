package factorials;

import java.math.BigInteger;

public class Fact_Threads extends Thread {
	
	BigInteger starter;
	BigInteger limit;
	BigInteger result = BigInteger.ONE;
	
	boolean simple = false;
	boolean finished = false;
	
	BigFactorial bF;
	
	public Fact_Threads(int multiplier, BigInteger base, BigInteger extra, boolean simpMul, BigFactorial bf) {
		
		simple = simpMul;
		
		if(!simple) {
			
			starter = new BigInteger(Integer.toString(multiplier - 1));
			starter = starter.multiply(base);
			
			limit = new BigInteger(Integer.toString(multiplier));
			limit = limit.multiply(base);
			limit = limit.add(extra);
			
		} else {
			
			starter = base;
			limit = extra;
			
		}
		
		bF = bf;
		
	}
	
	@Override
	public void run() {
		
		if(!simple) {
			
			while(!starter.equals(limit)) {
				
				starter = starter.add(BigInteger.ONE);
				result = result.multiply(starter);
				
			}
			
		} else {
			
			result = starter.multiply(limit);
			
		}
		
		finished = true;
		bF.completionAdd();
		
	}
	
}
