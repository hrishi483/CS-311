package generic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;

public class Simulator {

	static Processor processor;
	static boolean simulationComplete;
	static EventQueue eventQueue;
	private static boolean stop_pipeline;


	public static void setupSimulation(String assemblyProgramFile, Processor p) throws IOException {
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		eventQueue = new EventQueue();
		simulationComplete = false;
	}

	static void loadProgram(String assemblyProgramFile) throws IOException {

		InputStream input_file = null;
		try {
			input_file = new FileInputStream(assemblyProgramFile);

		} catch (FileNotFoundException error) {
			error.printStackTrace();

		}
		DataInputStream assembly_instructions = new DataInputStream(input_file);

		int current_address = -1;
		int move_offset = 1;
		while (assembly_instructions.available() > 0) {
			int current_inst = assembly_instructions.readInt();
			if (current_address == -1) {
				processor.getRegisterFile().setProgramCounter(current_inst);

			} else {
				processor.getMainMemory().setWord(current_address, current_inst);

			}
			current_address += move_offset;
		}
		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);

	}
	public static EventQueue getEventQueue()
	{
		return eventQueue;
	}

	public static void simulate() {
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

		
	}

	public static void setSimulationComplete(boolean value) {
		simulationComplete = value;
	}
}
