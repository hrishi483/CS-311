package processor.pipeline;

import generic.Simulator;
import processor.Processor;

import generic.Instruction.OperationType;
import generic.Instruction;

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
		if(MA_RW_Latch.isRW_enable())
		{
			Instruction fetched_instruction = MA_RW_Latch.get_MA_RW_LatchType_Instruction();

			int fetched_alu_result = MA_RW_Latch.get_MA_RW_LatchType_ALU_result();

			OperationType fetched_op_type = fetched_instruction.getOperationType();
				
			switch(fetched_op_type)
			{
				case store:
				case jmp:
				case beq:
				case bne:
				case blt:
				case bgt:
					break;
				case load:
					int lr = MA_RW_Latch.get_MA_RW_LatchType_Load_result();

					int rd = fetched_instruction.getDestinationOperand().getValue();

					containingProcessor.getRegisterFile().setValue(rd, lr);
					break;
				case end:
					Simulator.setSimulationComplete(true);
					break;
				default:
					rd = fetched_instruction.getDestinationOperand().getValue();
					containingProcessor.getRegisterFile().setValue(rd, fetched_alu_result);
					break;
			}
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
