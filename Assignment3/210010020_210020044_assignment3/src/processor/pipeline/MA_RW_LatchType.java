package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int MA_RW_LatchType_alu_result;
	Instruction MA_RW_LatchType_instruction;
	int MA_RW_LatchType_load_result;
    public int overload_register;

	public void set_MA_RW_LatchType_Load_result(int result) {
		MA_RW_LatchType_load_result = result;
	}

	public void set_MA_RW_LatchType_Instruction(Instruction inst) {
		MA_RW_LatchType_instruction = inst;
	}

	public MA_RW_LatchType()
	{
		RW_enable = false;
	}
	public void set_MA_RW_LatchType_ALU_result(int result) {
		MA_RW_LatchType_alu_result = result;
	}


	public boolean isRW_enable() {
		return RW_enable;
	}

	public Instruction get_MA_RW_LatchType_Instruction() {
		return MA_RW_LatchType_instruction;
	}

	public int get_MA_RW_LatchType_ALU_result() {
		return MA_RW_LatchType_alu_result;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}
	
	public int get_MA_RW_LatchType_Load_result() {
		return MA_RW_LatchType_load_result;
	}

	public void set_MA_RW_Latch(int mA_RW_LatchType_alu_result2) {
	}
}
