package processor.pipeline;

import generic.Instruction;

public class EX_MA_LatchType {
	
	int EX_MA_LatchType_alu_result;
	boolean EX_MA_LatchType_NOP;
	boolean MA_enable;
	Instruction instr;

		
	public boolean getIsNOP() {
		return EX_MA_LatchType_NOP;
	}
	
	public void setIsNOP(boolean is_NOP) {
		EX_MA_LatchType_NOP = is_NOP;
	}


	public EX_MA_LatchType()
	{
		MA_enable = false;
		EX_MA_LatchType_NOP = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	
	public void setInstruction(Instruction inst) {
		instr = inst;
	}

	public int getALU_result() {
		return EX_MA_LatchType_alu_result;
	}

	public void setALU_result(int result) {
		EX_MA_LatchType_alu_result = result;
	}


	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	public Instruction getInstruction() {
		return instr;
	}

}
