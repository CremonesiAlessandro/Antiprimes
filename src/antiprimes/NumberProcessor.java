package antiprimes;

import java.util.logging.Logger;

/**
 * Class representing a thread which searches antiprime numbers in background.
 * 
 * After the thread is started the clients will ask to search for the successor of a given antiprime
 * (which is stored in the 'request' attribute).
 * 
 * When 'request' is changed the thread starts searching a bigger antiprime. When the search finishes the result
 * is notified to the sequence object associated to the thread.
 * 
 * The attribute 'request' is set to null when there are no request.
 */

public class NumberProcessor extends Thread {
	
	private final static Logger LOGGER = Logger.getLogger(NumberProcessor.class.getName());
	
	/**
	 * The sequence of antiprimes that is extended by the processor.
	 */
	private AntiPrimesSequence sequence;
	
	/**
	 * The antiprime of which the successor must be computed.
	 */
	private Number request = null;
	
	/**
	 * Initialize the processor.
	 * 
	 * @param sequence , the sequence where the new number found are added
	 */
	public NumberProcessor(AntiPrimesSequence sequence) {
		this.sequence = sequence;
	}
	
	/**
	 * Main method of the thread.
	 */
	public void run() {
		LOGGER.info("Processor ready");
		acceptRequest();
		
		while(true) {
			try {
				LOGGER.info("Waiting a new request");
				Number num = this.getNextToProcess();
				LOGGER.info("Searching the successor of " + num.getValue() + " ...");
				Number res = AntiPrimes.nextAntiPrimeAfter(num);
				LOGGER.info("Found " + res.getValue() + " with " + res.getDivisors() + " divisors");
				sequence.addAntiPrimes(res);
				acceptRequest();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}	
		}
		
	}
	
	/**
	 * Ask the processor to compute the successor of n in the antiprime sequence.
	 * 
	 * If the processor is busy the caller will block until the processor can recieve the request.
	 * The method will return without waiting the end of the computation.
	 * 
	 * @param n
	 * @throws InterruptedException
	 */
	synchronized public void nextAntiPrime(Number n) throws InterruptedException {
		while(request != null) {
			if(request.getValue() == n.getValue())
				return;
			wait();
		}
		request = n;
		notify();
	}
	
	/**
	 * Retrieve the next request. Wait for a new if there are none.
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	synchronized private Number getNextToProcess() throws InterruptedException {
		while(request == null)
			wait();
		return request;
	}
	
	/**
	 * Make the processor ready for the new computation.
	 */
	synchronized private void acceptRequest() {
		request = null;
		notify();
	}
}
