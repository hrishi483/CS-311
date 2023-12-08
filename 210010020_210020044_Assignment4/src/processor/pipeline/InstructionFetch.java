package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class InstructionFetch {
	
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
			System.out.println("IF is enabled with instruction: " + Integer.toBinaryString(fresh_Inst) + "..");
			System.out.println("currentPC " + Integer.toString(curr_ProgramCounter));
			IF_OF_Latch.setInstruction(fresh_Inst);
			containingProcessor.getRegisterFile().setProgramCounter(curr_ProgramCounter + 1);
			IF_OF_Latch.setOF_enable(true);
		}
	}

}
