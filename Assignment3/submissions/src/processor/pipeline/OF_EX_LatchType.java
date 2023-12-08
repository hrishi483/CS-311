package processor.pipeline;

import generic.Instruction;

public class OF_EX_LatchType {
	
	Instruction OF_EX_LatchType_instruction;
	boolean EX_enable;
	
	public void set_OF_EX_LatchType_Instruction(Instruction inst) {
		this.OF_EX_LatchType_instruction = inst;
	}
	
	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public OF_EX_LatchType()
	{
		EX_enable = false;
	}
	
	public Instruction get_OF_EX_LatchType_Instruction() {
		return this.OF_EX_LatchType_instruction;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}
}
