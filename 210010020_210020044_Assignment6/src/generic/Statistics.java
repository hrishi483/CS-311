package generic;

import java.io.PrintWriter;

public class Statistics {
	
	
	static int numberOfInstructions;
	static int numberOfCycles;
	static int noOfBranchTaken;
	static int noOfDataInterlocks;
	static int noOfInstAccesses;
	static int noOfMemoryAccesses;
	static int noOfInstHits;
	static int noOfMemoryHits;

	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Number of branch taken = " + noOfBranchTaken);
			writer.println("Number of datainterlocks = " + noOfDataInterlocks);
			
			
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
	public static int getNumberOfInstructions() {
		return numberOfInstructions;
	}

	public static int getNumberOfCycles() {
		return numberOfCycles;
	}
	public static int getNumberOfDataInterlocks ()
	{
		return noOfDataInterlocks;
	}
	public static int getNumberOfBranchTaken()
	{
		return noOfBranchTaken;
	}
	public static void setNumberOfBranchTaken (int noOfBranchTaken)
	{
		Statistics.noOfBranchTaken = noOfBranchTaken;
	}
	public static void setNumberOfDataInterlocks(int noOfDataInterlocks)
	{
		Statistics.noOfDataInterlocks = noOfDataInterlocks;

	}
	public static void setNoOfInstAccesses(int value)
	{
		Statistics.noOfInstAccesses=value;
	}
	public static void setNoOfMemoryAccesses(int value)
	{
		Statistics.noOfMemoryAccesses=value;

	}
	public  static int getNoOfInstAccesses()
	{
		return noOfInstAccesses;
	}
	public static int getNoOfMemoryAccesses()
	{
		return noOfMemoryAccesses;
	}
	public static void setNoOfMemoryHits(int value)
	{
		Statistics.noOfMemoryHits=value;
	}
	public static void setNoOfInstHits(int value) {
		Statistics.noOfInstHits = value;
	}
	public static int getNoOfInstHits()
	{
		return noOfInstHits;
	}
	public static int getNoOfMemoryHits()
	{
		return noOfMemoryHits;
	}




}
