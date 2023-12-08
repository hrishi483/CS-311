package generic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import processor.Clock;
import processor.Processor;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 */
		FileInputStream inputFile = null;
		try{
			inputFile = new FileInputStream(assemblyProgramFile);
			BufferedInputStream binaryInput = new BufferedInputStream(inputFile);
			DataInputStream dataInput = new DataInputStream(binaryInput);

			int location = 0;
			try{
				if(dataInput.available()>0){
			//2. set PC to the address of the first instruction in the main
					Simulator.processor.getRegisterFile().setProgramCounter(dataInput.readInt());
				}
				// if(dataInput.available()<0){
				// 	Simulator.processor.getRegisterFile().setProgramCounter(dataInput.readInt());
				// }
			}
			catch(IOException e){
				Misc.printErrorAndExit("DataInput FIle not read");
			}
			while(true){
				
				try {
					if (dataInput.available()<=0){
						break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (dataInput.available()>0){
						Simulator.processor.getMainMemory().setWord(location, dataInput.readInt());
						location=location+1;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 /*
			*
			* 3. set the following registers:
			*     x0 = 0 default is always zero so no need of this statement
			*     x1 = 65535
			*     x2 = 65535
			*/
		
		// processor.getRegisterFile().setValue(0,0);
			int reg1 = processor.getRegisterFile().getValue(1);
		
			if (reg1 != 65535){
				processor.getRegisterFile().setValue(1,0);
			}
			
			int reg2 = processor.getRegisterFile().getValue(1);
			
			if (reg2 != 65535){
				processor.getRegisterFile().setValue(2,0);
			}

			try {
				inputFile.close();
				binaryInput.close();
				dataInput.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			} 
		}
		catch(FileNotFoundException  e){
			Misc.printErrorAndExit(assemblyProgramFile+"Not found");
		}
		System.out.println(processor.getMainMemory().getContentsAsString(0, 65535));
	}
	
	public static void simulate()
	{
		int total_cycles=0;
		int total_instructions = 0;
		
		try
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
				total_instructions++;
				total_cycles++;
			}
			Statistics.setNumberOfInstructions(total_instructions);
			Statistics.setNumberOfCycles(total_cycles);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Misc.printErrorAndExit("error in clock incrementing\n" + e.toString());
		}
		
		
		// TODO
		// set statistics
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
