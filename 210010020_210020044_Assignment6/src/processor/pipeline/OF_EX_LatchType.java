package processor.pipeline;

import generic.Instruction;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	Instruction instruction;
	int operand1;
	int operand2;
	int immediate;
	int destination_operand;
	boolean NOP;

	
	public OF_EX_LatchType()
	{
		EX_enable = false;
		NOP=false;
	}
	public OF_EX_LatchType(boolean status) {

		EX_enable = status;
		NOP=false;
	}
	public OF_EX_LatchType(boolean status, Instruction instruction) {

		EX_enable = status;
		this.instruction = instruction;
		NOP=false;

	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}
	public void setInstruction(Instruction instruction)
	{
		this.instruction=instruction;
	}
	public Instruction getInstruction()
	{
		return instruction;
	}
	public void setOperand1(int operand1)
	{
		this.operand1=operand1;
	}
	public int getOperand1()
	{
		return operand1;
	}
	public void setOperand2(int operand2)
	{
		this.operand2=operand2;
	}
	public int getOperand2()
	{
		return operand2;
	}
	public void setImmediate(int immediate)
	{
		this.immediate = immediate;
	}
	public int getImmediate()
	{
		return immediate;
	}
	public void setDestinationOperand(int destination_operand)
	{
		this.destination_operand=destination_operand;
	}
	public int getDestinationOperand()
	{
		return destination_operand;
	}
	public boolean getIsNOP()
	{
		return NOP;
	}
	public void setIsNOP(boolean status)
	{
		NOP=status;
	}

	
	



}
