package processor.pipeline;

import generic.Instruction;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	Instruction EX_MA_LatchType_instruction;
	int EX_MA_LatchType_alu_result;

	public void set_EX_MA_LatchType_Instruction(Instruction inst) {
		EX_MA_LatchType_instruction = inst;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	public void set_EX_MA_LatchType_ALU_result(int res) {
		EX_MA_LatchType_alu_result = res;
	}

	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public Instruction get_EX_MA_LatchType_Instruction() {
		return EX_MA_LatchType_instruction;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public int get_EX_MA_LatchType_ALU_result() {
		return EX_MA_LatchType_alu_result;
	}



}
