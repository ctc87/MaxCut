package test.timer;


/**
 * <h1>Timer</h1> Timer its a class to calculate time of 
 * any algorithm execution.
 * 
 * @author Carlos Troyano Carmona
 * @version 1.0
 * @date 1 mar. 2017
 * @see System
 */
public class Timer {
	
	/**
	 * Needed attributes for timer.
	 */
	static private long startTime, stopTime;

	/**
	 * Start the timer
	 */
	static public void start() {
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Stop the timer and return the time
	 * @return time of timer.
	 */
	static public float  stop() {
		 stopTime = System.currentTimeMillis() - startTime;
		 return (float)(stopTime)/1000;
	}
	
}
