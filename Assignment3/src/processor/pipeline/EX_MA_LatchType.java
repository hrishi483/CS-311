package processor.pipeline;
import generic.Instruction;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int pc;
	int rd;
	int opcode;
	int result;
	int excess;

	Instruction instruction;
	int aluResult;
	

	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	public int get_Opcode()
	{
		return this.opcode;
	}

	public int get_pc()
	{
		return this.pc;
	}
	public int get_rd()
	{
		return this.rd;
	}
	public int get_aluResult()
	{
		return aluResult;
	}
	public int get_Result()
	{
		return this.result;
	}
	public int get_Excess() 
	{
		return excess;
	}
}
