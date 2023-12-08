package generic;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.DataInputStream;

import processor.Clock;
import processor.Processor;
import generic.Statistics;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		try
		{
			loadProgram(assemblyProgramFile);
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
		InputStream inputstrm = null;

		try
		{
			inputstrm = new FileInputStream(assemblyProgramFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		DataInputStream datainputstrm = new DataInputStream(inputstrm);

		int alocation = -1;

		while(datainputstrm.available() > 0)
		{
			int next = datainputstrm.readInt();
			if(alocation == -1)
			{
				processor.getRegisterFile().setProgramCounter(next);
			}
			else
			{
				processor.getMainMemory().setWord(alocation, next);
			}
			alocation += 1;
		}
        
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);
        processor.getRegisterFile().setValue(0, 0);
        
	}
			
	public static void simulate()
	{
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();

			Statistics.setNumberOfCycles(Statistics.getNumberOfCycles() + 1);
			Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions() + 1);
		}
		
		// TODO
		// set statistics
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
