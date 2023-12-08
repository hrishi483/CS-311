package processor;

public class Clock {
	static long currentTime = 0;

	
	public static void incrementClock(int max_latency)
	{

		currentTime=currentTime+max_latency;
	}
	
	public static long getCurrentTime()
	{
		return currentTime;
	}
}
