package generic;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.DataInputStream;
import java.io.FileInputStream;
import processor.Processor;
import generic.Statistics;
import processor.Clock;


public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	// As per the PDF
	static EventQueue eventQueue;
	private static boolean stop_pipeline;


	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		try
		{
			loadProgram(assemblyProgramFile);
			// As per the PDF
			eventQueue = new EventQueue();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile) throws IOException
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		InputStream input_strm = null;
		try
		{
			input_strm = new FileInputStream(assemblyProgramFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		DataInputStream data_inp_strm = new DataInputStream(input_strm);

		int address = -1;
		while(data_inp_strm.available() > 0)
		{
			int next = data_inp_strm.readInt();
			if(address == -1)
			{
				processor.getRegisterFile().setProgramCounter(next);
			}
			else
			{
				processor.getMainMemory().setWord(address, next);
			}
			address += 1;
		}
        
        processor.getRegisterFile().setValue(0, 0);
        processor.getRegisterFile().setValue(1, 65535);
        processor.getRegisterFile().setValue(2, 65535);
        
	}
	public static EventQueue getEventQueue()
	{
		return eventQueue;
	}
			
	public static void simulate()
	{
		while (simulationComplete == false) {
			processor.setMax_latency(0);
			if (eventQueue.queue.isEmpty()==true)
			{
				stop_pipeline=false;
			}
			else
			{
				stop_pipeline=true;
			}
			if(stop_pipeline==false)
			{
				processor.getRWUnit().performRW();
				processor.getMAUnit().performMA();
				processor.getEXUnit().performEX();
			}
			eventQueue.processEvents();

			if(stop_pipeline==false)
			{
				processor.getOFUnit().performOF();
				processor.getIFUnit().performIF();
			}

			Clock.incrementClock(1);
			Statistics.setNumberOfCycles(Statistics.getNumberOfCycles() + 1);

		}

		// TODO
		// set statistics
		
		//print statistics
		System.out.println("No. of of Stalls: " + (Statistics.getNumberOfInstructions() - Statistics.getNumberOfRegisterWriteInstructions()));
		System.out.println("No. of Wrong Branch Instructions: " + Statistics.getNumberOfBranchTaken());
		System.out.println("No. of Cycles: " + Statistics.getNumberOfCycles());
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
