package processor.pipeline;
import generic.*;
import configuration.Configuration;
import processor.Clock;
import processor.Processor;



public class InstructionFetch implements Element {
	
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	Processor containingProcessor;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor,
							IF_EnableLatchType iF_EnableLatch,
							IF_OF_LatchType iF_OF_Latch,
							EX_IF_LatchType eX_IF_Latch)
	{
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.containingProcessor = containingProcessor;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
	
		if(IF_EnableLatch.isIF_enable())
		{
			if(EX_IF_Latch.getIS_enable())
			{
				int fresh_ProgramCounter = EX_IF_Latch.getPC();
				containingProcessor.getRegisterFile().setProgramCounter(fresh_ProgramCounter);
				EX_IF_Latch.setIS_enable(false);
			}
			int curr_ProgramCounter = containingProcessor.getRegisterFile().getProgramCounter();
			int fresh_Inst = containingProcessor.getMainMemory().getWord(curr_ProgramCounter);
			// System.out.println("IF is enabled with instruction: " + Integer.toBinaryString(fresh_Inst) + "..");
			// System.out.println("currentPC " + Integer.toString(curr_ProgramCounter));
			
			//Try to add these at the end 
			IF_OF_Latch.setInstruction(fresh_Inst);
			containingProcessor.getRegisterFile().setProgramCounter(curr_ProgramCounter + 1);
			IF_OF_Latch.setOF_enable(true);


			if(containingProcessor.getCache_i().checkinCache(curr_ProgramCounter)==true)
			{
				Simulator.getEventQueue().addEvent(
					new MemoryReadEvent(
							Clock.getCurrentTime() + containingProcessor.getCache_i().latency,
							(Element) this,
							containingProcessor.getMainMemory(),
							curr_ProgramCounter)
			);
			Statistics.setNoOfInstAccesses(Statistics.getNoOfInstAccesses()+1);
			Statistics.setNoOfInstHits(Statistics.getNoOfInstHits()+1);
			}
			else
			{
				Simulator.getEventQueue().addEvent(
					new MemoryReadEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							(Element) this,
							containingProcessor.getMainMemory(),
							curr_ProgramCounter)
				);
				containingProcessor.getCache_i().addtoCache(curr_ProgramCounter);
				Statistics.setNoOfInstAccesses(Statistics.getNoOfInstAccesses()+1);

			
			}

			Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions() + 1);

			if(Configuration.mainMemoryLatency > containingProcessor.getMax_latency())
			{
				containingProcessor.setMax_latency(Configuration.mainMemoryLatency);
			}
	}


}

@Override

public void handleEvent(Event e) {

}

}
