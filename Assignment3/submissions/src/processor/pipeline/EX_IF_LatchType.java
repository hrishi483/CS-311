package processor.pipeline;

public class EX_IF_LatchType {

	boolean EX_IF_LatchType_IS_enable;
	int ProgramCounter;
	
	public void set_EX_IF_LatchType_IS_enable(boolean isEnableSignal, int newProgramCounter) {
		EX_IF_LatchType_IS_enable = isEnableSignal;
		ProgramCounter = newProgramCounter;
	}

	public EX_IF_LatchType()
	{
		EX_IF_LatchType_IS_enable = false;
	}

	public int get_EX_IF_LatchType_PC() {
		return ProgramCounter;
	}


	public void set_EX_IF_LatchType_IS_enable(boolean isEnableSignal) {
		EX_IF_LatchType_IS_enable = isEnableSignal;
	}

	public boolean get_EX_IF_LatchType_IS_enable() {
		return EX_IF_LatchType_IS_enable;
	}

}
