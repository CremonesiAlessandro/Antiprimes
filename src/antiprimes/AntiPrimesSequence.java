package antiprimes;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;


/**
 * Represent the sequence of antiprimes found so far.
 */
public class AntiPrimesSequence {

    /**
     * The numbers in the sequence.
     */
    private List<Number> antiPrimes = new ArrayList<>();
    private NumberProcessor processor;
    private List<Observer> observers = new ArrayList<>();
    
    /**
     * Create a new sequence containing only the first antiprime (the number '1').
     */
    public AntiPrimesSequence() {
    	processor = new NumberProcessor(this);
        this.reset();
        processor.start();
    }
    
    /**
     * Register the new observer.
     * 
     * @param observer
     */
    public void addObserver(Observer observer) {
    	observers.add(observer);
    }

    /**
     * Clear the sequence so that it contains only the first antiprime (the number '1').
     */
    public void reset() {
        antiPrimes.clear();
        antiPrimes.add(new Number(1, 1));
    }

    /**
     * Find a new antiprime and add it to the sequence.
     */
    public void computeNext() {
    	try {
    		processor.nextAntiPrime(getLast());
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Return the last antiprime found.
     */
    synchronized public Number getLast() {
        int n = antiPrimes.size();
        return antiPrimes.get(n - 1);
    }

    /**
     * Return the last k antiprimes found.
     */
    synchronized public List<Number> getLastK(int k) {
        int n = antiPrimes.size();
        if (k > n)
            k = n;
        return antiPrimes.subList(n - k, n);
    }

    /**
     * Extend the sequence to include a new antiprime.
     * 
     * @param res
     */
	synchronized public void addAntiPrimes(Number res) {
		antiPrimes.add(res);
	}
}
