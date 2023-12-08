package processor.pipeline;

import configuration.Configuration;
import generic.*;
import generic.Instruction.OperationType;
import processor.Clock;
import processor.Processor;

public class MemoryAccess implements Element {
	Configuration configuration;
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performMA()
	{
		if(EX_MA_Latch.getIsNOP())
		{
			//System.out.println("Entered MA Stage, detected NOP at EX_MA Latch");
			MA_RW_Latch.setIsNOP(true);
			MA_RW_Latch.setInstruction(null);
			EX_MA_Latch.setIsNOP(false);
		}
		else if(EX_MA_Latch.isstatus())
		{
			//System.out.println("MA Stage");
			//System.out.println("Entered MA Stage, Normally");
			Instruction instruction = EX_MA_Latch.getInstruction();
			OperationType operation = instruction.getOperationType();
			int alu=EX_MA_Latch.getALU();
			//System.out.println("Operation is " + operation +" ALU received is " + alu);
			MA_RW_Latch.setALU(alu);
			

			if(operation==OperationType.store)
			{
				int store= EX_MA_Latch.getOperand1();
				containingProcessor.getMainMemory().setWord(alu, store);
				//System.out.println(" Storing value " + store + " at address " +alu  );

				Simulator.getEventQueue().addEvent(
						new MemoryWriteEvent(
								Clock.getCurrentTime() + Configuration.mainMemoryLatency,
								this,
								containingProcessor.getMainMemory(),
								alu,
								store)
				);
				if(containingProcessor.getCache_d().checkinCache(alu)==false)
				{
					containingProcessor.getCache_d().addtoCache(alu);
				}
				if(containingProcessor.getMax_latency()< configuration.mainMemoryLatency)
				{
					containingProcessor.setMax_latency(configuration.mainMemoryLatency);
				}
				
			}
			else if (operation==OperationType.load)
			{
				int load = containingProcessor.getMainMemory().getWord(alu);
				MA_RW_Latch.setLoad(load);
				if(containingProcessor.getCache_d().checkinCache(alu)==true)
				{
					Simulator.getEventQueue().addEvent(
							new MemoryReadEvent(
									Clock.getCurrentTime() + containingProcessor.getCache_d().latency,
									this,
									containingProcessor.getMainMemory(), alu)
					);
					System.out.println("Found at Cache Value = "+ Integer.toString(alu));
					Statistics.setNoOfMemoryAccesses(Statistics.getNoOfMemoryAccesses()+1);
					Statistics.setNoOfMemoryHits(Statistics.getNoOfMemoryHits()+1);
				}
				else
				{
					Simulator.getEventQueue().addEvent(
							new MemoryReadEvent(
									Clock.getCurrentTime() + Configuration.mainMemoryLatency,
									this,
									containingProcessor.getMainMemory(), alu)
					);
					containingProcessor.getCache_d().addtoCache(alu);
					System.out.println("Did not find at Cache Value = "+ Integer.toString(alu));
					Statistics.setNoOfMemoryAccesses(Statistics.getNoOfMemoryAccesses()+1);
				}

				//System.out.println(" From " + alu + " value retrieved is " + load);
				if(containingProcessor.getMax_latency()< configuration.mainMemoryLatency)
				{
					containingProcessor.setMax_latency(configuration.mainMemoryLatency);

				}
			}

			if(operation.ordinal()==29)
			{
				IF_EnableLatch.setIF_enable(false);
			}


			MA_RW_Latch.setInstruction(instruction);
			MA_RW_Latch.setRW_status(true);
		}
	}
	@Override
	public void handleEvent(Event e) {

	}

}
