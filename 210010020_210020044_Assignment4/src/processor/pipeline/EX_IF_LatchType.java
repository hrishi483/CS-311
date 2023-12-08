package processor.pipeline;

public class EX_IF_LatchType {

	int ProgramCounter;
	boolean EX_IF_LatchType_IS_enable;
	
	public boolean getIS_enable() {
		return EX_IF_LatchType_IS_enable;
	}

	public void setIS_enable(boolean iS_enable) {
		EX_IF_LatchType_IS_enable = iS_enable;
	}

	public int getPC() {
		return ProgramCounter;
	}

	public void setIS_enable(boolean iS_enable, int newPC) {
		EX_IF_LatchType_IS_enable = iS_enable;
		ProgramCounter = newPC;
	}

	public EX_IF_LatchType()
	{
		EX_IF_LatchType_IS_enable = false;
	}

}
