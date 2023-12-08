package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {
	
	int MA_RW_LatchType_load_result;
	int MA_RW_LatchType_alu_result;
	boolean RW_enable;
	boolean MA_RW_LatchType_NOP;
	Instruction instrn;

	
	public void setLoad_result(int result) {
		MA_RW_LatchType_load_result = result;
	}

	public int getLoad_result() {
		return MA_RW_LatchType_load_result;
	}


	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public Instruction getInstruction() {
		return instrn;
	}

	public void setInstruction(Instruction inst) {
		instrn = inst;
	}

	
	public int getALU_result() {
		return MA_RW_LatchType_alu_result;
	}

	public void setALU_result(int result) {
		MA_RW_LatchType_alu_result = result;
	}
	
	public boolean getIsNOP() {
		return MA_RW_LatchType_NOP;
	}
	
	public void setIsNOP(boolean is_NOP) {
		MA_RW_LatchType_NOP = is_NOP;
	}


	public MA_RW_LatchType()
	{
		RW_enable = false;
		MA_RW_LatchType_NOP = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}
}
