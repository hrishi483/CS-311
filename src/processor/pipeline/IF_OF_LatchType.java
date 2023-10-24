package processor.pipeline;

public class IF_OF_LatchType {
	
	int instrn;
	boolean OF_enable;
	
	
	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int getInstruction() {
		return instrn;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setInstruction(int instruction) {
		this.instrn = instruction;
	}

	public IF_OF_LatchType()
	{
		OF_enable = false;
	}


}
