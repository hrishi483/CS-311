package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numOfOFStageInsts;
	static int numOfCycles;
	static int numOfRegisterWriteInsts;
	static int numOfInsts;
	static int numOfBranchTaken;

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

}
