package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numOfOFStageInsts;
	static int numOfCycles;
	static int numOfRegisterWriteInsts;
	static int numOfInsts;
	static int numOfBranchTaken;
	static int noOfDataInterlocks;
	static int noOfBranchTaken;
	static int noOfInstAccesses;
	static int noOfMemoryAccesses;
	static int noOfInstHits;
	static int noOfMemoryHits;

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("No. of instructions executed = " + numOfInsts);
			writer.println("No. of cycles taken = " + numOfCycles);
			
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics

	public static int getNumberOfBranchTaken() {
		return numOfBranchTaken;
	}
	
	public static void setnumberOfRegisterWriteInstructions(int numberOfRegisterWriteInstructions) {
		Statistics.numOfRegisterWriteInsts = numberOfRegisterWriteInstructions;
	}
	
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numOfInsts = numberOfInstructions;
	}

	
	public static int getNumberOfCycles() {
		return numOfCycles;
	}
	
	public static void setNumberOfOFInstructions(int numberOfOFStageInstructions) {
		Statistics.numOfOFStageInsts = numberOfOFStageInstructions;
	}
	
	public static int getNumberOfOFInstructions() {
		return numOfOFStageInsts;
	}
	
	public static void setNumberOfBranchTaken(int numberOfBranchTaken) {
		Statistics.numOfBranchTaken = numberOfBranchTaken;
	}
		
	public static int getNumberOfRegisterWriteInstructions() {
		return numOfRegisterWriteInsts;
	}

	public static void setNumberOfCycles(int numberOfCycles) {
		Statistics.numOfCycles = numberOfCycles;
	}

	public static int getNumberOfInstructions() {
		return numOfInsts;
	}
	public static int getNumberOfDataInterlocks ()
	{
		return noOfDataInterlocks;
	}
	public static void setNumberOfDataInterlocks(int noOfDataInterlocks)
	{
		Statistics.noOfDataInterlocks = noOfDataInterlocks;

	}
	public static void setNoOfInstAccesses(int value)
	{
		Statistics.noOfInstAccesses=value;
	}
	public  static int getNoOfInstAccesses()
	{
		return noOfInstAccesses;
	}
	public static void setNoOfMemoryAccesses(int value)
	{
		Statistics.noOfMemoryAccesses=value;
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
