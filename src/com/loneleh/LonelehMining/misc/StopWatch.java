package com.loneleh.LonelehMining.misc;


/*
 * Copyright 2003-2004 Michael Franken, Zilverline.
 *
 * The contents of this file, or the files included with this file, are subject to
 * the current version of ZILVERLINE Collaborative Source License for the
 * Zilverline Search Engine (the "License"); You may not use this file except in
 * compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.zilverline.org.
 *
 * See the License for the rights, obligations and
 * limitations governing use of the contents of the file.
 *
 * The Original and Upgraded Code is the Zilverline Search Engine. The developer of
 * the Original and Upgraded Code is Michael Franken. Michael Franken owns the
 * copyrights in the portions it created. All Rights Reserved.
 *
 */


/**
 * StopWatch reports elapsed time between start and stop.
 * 
 * @author Michael Franken
 * @version $Revision: 1.12 $
 */

/**
 * revision: by Darren Lin
 */

public class StopWatch {
	/** Time the Stopwatch started. */
	private long start = 0;
	
	/** Time the Stopwatch paused. */
	private long pause = 0;
	
	/** is timer paused? */
	private boolean paused = false;
	
	/** Time the Stopwatch stopped. */
	private long stop = 0;
	
	/**
	 * Creates a StopWatch that automatically starts
	 */
	public StopWatch()
	{
		start = System.currentTimeMillis();
		pause = 0;
		stop = 0;
		paused = false;
	}
	
	/**
	 * Creates a StopWatch given a start time (could be in the past or future)
	 * @param startTime start time of this StopWatch
	 */
	public StopWatch(long startTime)
	{
		new StopWatch();
		start = startTime;
	}
	
	/**
	 * Creates a StopWatch with the option of starting it paused
	 * @param startPaused boolean determining pause state
	 */
	public StopWatch(boolean startPaused)
	{
		new StopWatch();
		if (startPaused)
		{
			pause = start;
			paused = true;
		}
	}
	
	/**
	 * Creates a StopWatch with a given start time and the option of starting it paused
	 * @param startTime start time of this StopWatch
	 * @param startPaused boolean determining pause state
	 */
	public StopWatch(long startTime, boolean startPaused)
	{
		new StopWatch(startTime);
		if (startPaused)
		{
			pause = start;
			paused = true;
		}
	}
	
	/**
	 * Start ticking, resets the watch.
	 */
	public final void start()
	{
		if (pause == 0) //fresh start, or if it's not paused, it will set "now" as start time
		{
			start = System.currentTimeMillis();
		}
		else
		{
			start = System.currentTimeMillis() - pause;
			pause = 0; //resets pause counter
		}
		
		paused = false;
	}
	
	/**
	 * Pauses ticking.
	 */
	public final void pause() {
		if (start != 0)
		{
			pause = System.currentTimeMillis(); //works like stop, but can start from where you left off
			paused = true;
		}
	}
	
	/**
	 * Stop ticking.
	 */
	public final void stop() {
		stop = System.currentTimeMillis();
		pause = 0;
	}
	
	public final long getElapsedTime()
	{
		long difference;
		
		if (!paused && stop == 0) //stopwatch is still running; neither pause nor stop has been set
		{
			long now = System.currentTimeMillis();
			
			difference = (now - start); // in millis
		}
		else if (stop != 0) //stopped
		{
			difference = (stop - start); // in millis
		}
		else //(pause != 0) //paused instead of stopped
		{
			difference = (pause - start); //in millis
		}
		
		return difference;
	}
	
	/**
	 * Calculates time elapsed. If stop has not been called yet, the current time is taken to calculate elapsed time.
	 * 
	 * @return the time elapsed between start and stop as String. The string contains two of days, hours, minutes, seconds and
	 *         milliseconds.
	 * 
	 * @throws StopWatchException If StopWatch was never started.
	 */
	public final String getElapsedTimeString() 
	{
		long difference = getElapsedTime();
		
		
		long x;
		x = difference / 1000;
		long seconds = x % 60;
		x /= 60;
		long minutes = x % 60;
		x /= 60;
		long hours = x % 24;
		x /= 24;
		long days = x;
		
		String message;
		if (days > 0)
		{
			message = String.format("%dD:%dH:%dM:%dS",
					days,
					hours,
					minutes,
					seconds
					);
		}
		else
		{
			message = String.format("%dH:%dM:%dS",
					hours,
					minutes,
					seconds
					);
		}
		
		return message;
	}
}
