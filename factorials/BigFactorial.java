package factorials;

import java.math.BigInteger;

public class BigFactorial {
	
	private int totalThreads = 1;
	private int tThrFM = totalThreads;
	private int completionVal = 0;
	
	private Fact_Threads[] ft;
	
	private Object o = new Object();
	private boolean allDone = false;
	
	public BigFactorial() {
		
		totalThreads = Runtime.getRuntime().availableProcessors(); //Get max threads available for max efficiency
		tThrFM = totalThreads;
		
	}
	
	/**
	 * Example: <br><br>
	 * {@code BigFactorial bf = new BigFactorial();} <br>
	 * {@code bf.calculate(new BigInteger("100"));} <br><br>
	 * Returns Value in BigInteger
	 */
	public BigInteger calculate(BigInteger input) {
		
		//Initialize
		completionVal = 0;
		allDone = false;
		ft = new Fact_Threads[totalThreads];
		
		//Get How Much Each Thread Should Compute
		BigInteger[] eachThreadCompVal = input.divideAndRemainder(new BigInteger(Integer.toString(totalThreads)));
		
		//Start threads
		for(int i = 1; i <= totalThreads; i++) {
			
			if(i != totalThreads)
				ft[i - 1] = new Fact_Threads(i, eachThreadCompVal[0], BigInteger.ZERO, false, this);
			
			else
				ft[i - 1] = new Fact_Threads(i, eachThreadCompVal[0], eachThreadCompVal[1], false, this);
			
			ft[i - 1].start();
			
		}
		
		//Check if all calculations are done
		synchronized (o) {
			
			try {
				
				while(!allDone)
					o.wait();
				
			} catch(Exception e) {e.printStackTrace();}
			
		}
		
		//Multiply all of them, with multi-threading
		int m = 0;
		
		while(true) {
			
			completionVal = 0;
			allDone = false;
			
			if(tThrFM != 1)
				m = tThrFM % 2;
			else
				break;
			
			tThrFM /= 2;
			
			for(int i = 0; i < tThrFM; i++) {
				
				ft[i] = new Fact_Threads(i, ft[i].result, ft[tThrFM + i].result, true, this);
				ft[i].start();
				
			}
			
			synchronized (o) {

				try {
					
					while(!allDone)
						o.wait();
					
				} catch(Exception e) {e.printStackTrace();}
				
			}
			
			if(m == 1) {
				
				ft[0].result = ft[0].result.multiply(ft[2 * tThrFM].result);
				
			}
			
		}
		
		return ft[0].result;
	
	}
	
	/**<b>Do not call this function! <br><br>
	 * This will be automatically called by Fact_Threads!</b>*/
	protected void completionAdd() {
		
		completionVal++;
		
		if(completionVal == tThrFM) {
			
			synchronized (o) {
				allDone = true;
				o.notify();
			}
			
		}
		
	}
	
}
