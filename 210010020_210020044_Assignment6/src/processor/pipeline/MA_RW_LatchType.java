package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {
	
	boolean RW_status;
	Instruction instruction;
	int load;
	int alu;
	int operand1;
	int operand2;
	int immediate;
	boolean NOP;
	int destination_operand;
	
	public MA_RW_LatchType() {
	
		this.RW_status = false;
		NOP=false;
	}

	public MA_RW_LatchType(boolean rw_status) {

		this.RW_status = rw_status;
		NOP=false;
	}

	public MA_RW_LatchType(boolean rw_status, Instruction instruction) {

		this.RW_status = rw_status;
		this.instruction = instruction;
		NOP=false;
	}

	public MA_RW_LatchType(boolean rw_status, Instruction instruction, int load) {

		this.RW_status = rw_status;
		this.instruction = instruction;
		this.load = load;
		NOP=false;
	}

	public MA_RW_LatchType(boolean rw_status, Instruction instruction, int load, int alu) {

		this.RW_status = rw_status;
		this.instruction = instruction;
		this.load = load;
		this.alu = alu;
		NOP=false;
	}

	public boolean IS_status() {
	
		return RW_status;
	}

	public void setRW_status(boolean rw_status) {
	
		this.RW_status = rw_status;
	}

	public Instruction getInstruction() {
	
		return instruction;
	}

	public void setInstruction(Instruction inst) {
	
		this.instruction = inst;
	}

	public void setLoad(int result) {
	
		this.load = result;
	}

	public int getLoad() {
	
		return load;
	}

	public int getALU() {
	
		return alu;
	}

	public void setALU(int result) {
	
		this.alu = result;
	}
	public boolean getIsNOP() {
		return NOP;
	}
	
	public void setIsNOP(boolean status) {
		NOP = status;
	}

}
