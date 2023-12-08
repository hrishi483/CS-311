package processor.pipeline;

import generic.Instruction;

public class OF_EX_LatchType {
	
	Instruction instrn;
	boolean OF_EX_LatchType_NOP;
	boolean EX_enable;


	public Instruction getInstruction() {
		return this.instrn;
	}
	
	public boolean getIsNOP() {
		return OF_EX_LatchType_NOP;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public void setInstruction(Instruction instruction) {
		this.instrn = instruction;
	}

	
	public void setIsNOP(boolean is_NOP) {
		OF_EX_LatchType_NOP = is_NOP;
	}

	
	public OF_EX_LatchType()
	{
		EX_enable = false;
		OF_EX_LatchType_NOP = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

}
