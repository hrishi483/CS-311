package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	int type;
	int opcode;
	int immx;
	int rs1;
	int rs2;
	int rd;
	int branchTarget;
	int imm;
	int pc;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public int get_Type(){
		return type;  // add this.type if necessary
	}  
	public int get_Opcode(){
		return opcode;
	}
	public int get_rs1(){
		return rs1;
	}
	public int get_rs2(){
		return rs2;
	}
	public int get_rd(){
		return rd;
	}
	public int get_branchTarget(){
		return branchTarget;
	}
	public int get_pc(){
		return pc;
	}
	public int get_immx(){
		return immx;
	}
	public int get_imm(){
		return imm;
	}

}
