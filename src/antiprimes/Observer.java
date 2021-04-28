package antiprimes;

/**
 * Interface for object that want to be notified when a AntiprimeSequence changes.
 */
public interface Observer {
	
	/**
	 * Notify the observer that the sequence changed.
	 */
	void update();
}
