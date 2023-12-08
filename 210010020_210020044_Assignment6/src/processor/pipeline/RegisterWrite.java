package processor.pipeline;

import generic.Instruction;
import generic.Simulator;
import generic.Instruction.OperationType;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if (MA_RW_Latch.getIsNOP())
		{
			MA_RW_Latch.setIsNOP(false);
			//System.out.println("Entered RW stage , NOP was detected, now normalized");
		}
		else if(MA_RW_Latch.IS_status())
		{
			//System.out.println("Entered RW Stage Normally");
			Instruction instruction = MA_RW_Latch.getInstruction();
			int alu = MA_RW_Latch.getALU();
			OperationType operation = instruction.getOperationType();

			switch(operation)
			{
				
				case bne:
				case blt:
				case bgt:
				case store:
				case jmp:
				case beq:
					break;
				case load:
					int load= MA_RW_Latch.getLoad();
					int destination_register= instruction.getDestinationOperand().getValue();
					containingProcessor.getRegisterFile().setValue(destination_register, load);
					//System.out.println("Loading to register " + destination_register + " load value  "+ load);
					break;
				case end:
					Simulator.setSimulationComplete(true);
					break;
				default:
					destination_register = instruction.getDestinationOperand().getValue();
					containingProcessor.getRegisterFile().setValue(destination_register, alu);
					//System.out.println("Loading to register " + destination_register + " alu value  "+ alu);

					break;

			}
			if(operation.ordinal()!=29)
			{
				IF_EnableLatch.setIF_enable(true);
			}
			
		}
	}

}
