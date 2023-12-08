package processor.pipeline;

import generic.Simulator;
import generic.Statistics;
import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;

public class RegisterWrite {
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	Processor containingProcessor;

	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch,
			IF_EnableLatchType iF_EnableLatch) {
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.containingProcessor = containingProcessor;
	}

	public void performRW() {
		if (MA_RW_Latch.getIsNOP()) {
			MA_RW_Latch.setIsNOP(false);
		} else if (MA_RW_Latch.isRW_enable()) {
			Statistics.setnumberOfRegisterWriteInstructions(Statistics.getNumberOfRegisterWriteInstructions() + 1);
			Instruction inst = MA_RW_Latch.getInstruction();
			int alu_result = MA_RW_Latch.getALU_result();
			OperationType operand_Type = inst.getOperationType();
			System.out.println("RW is enabled with aluResult: " + alu_result + "..");
			switch (operand_Type) {
			case store:
			case jmp:
			case beq:
			case bne:
			case blt:
			case bgt:
				break;
			case load:
				int load_result = MA_RW_Latch.getLoad_result();
				int dest_reg = inst.getDestinationOperand().getValue();
				containingProcessor.getRegisterFile().setValue(dest_reg, load_result);
				break;
			case end:
				Simulator.setSimulationComplete(true);
				break;
			default:
				dest_reg = inst.getDestinationOperand().getValue();
				containingProcessor.getRegisterFile().setValue(dest_reg, alu_result);
				break;
			}

			if (operand_Type.ordinal() != 29) {
				IF_EnableLatch.setIF_enable(true);
			}
		}
	}

}
