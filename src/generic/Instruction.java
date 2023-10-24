package generic;

public class Instruction {
	
	public enum OperationType {add, addi, sub, subi, mul, muli, div, divi, and, andi, or, ori, xor, xori, slt, slti, sll, slli, srl, srli, sra, srai, load, store, jmp, beq, bne, blt, bgt, end};
	
	int PC;
	Operand so1;
	Operand so2;
	OperationType op_Type;
	Operand destOp;
	

	public Operand getDestinationOperand() {
		return destOp;
	}
	public void setDestinationOperand(Operand destinationOperand) {
		this.destOp = destinationOperand;
	}	
	public void setProgramCounter(int programCounter) {
		this.PC = programCounter;
	}
	public OperationType getOperationType() {
		return op_Type;
	}
	
	public Operand getSourceOperand1() {
		return so1;
	}
	public void setSourceOperand1(Operand sourceOperand1) {
		this.so1 = sourceOperand1;
	}
	public Operand getSourceOperand2() {
		return so2;
	}
	public void setOperationType(OperationType operationType) {
		this.op_Type = operationType;
	}
	public void setSourceOperand2(Operand sourceOperand2) {
		this.so2 = sourceOperand2;
	}
	
	public int getProgramCounter() {
		return PC;
	}
	
	public String toString()
	{
		if(so1 != null)
		{
			if(so2 != null)
			{
				if(destOp != null)
				{
					return "PC="+ PC + "\t" + op_Type + "\t" + so1 + "\t" + so2 + "\t" + destOp + "\n";
				}
				else
				{
					return "PC="+ PC + "\t" + op_Type + "\t" + so1 + "\t" + so2 + "\tnull" + "\n";
				}
			}
			else
			{
				if(destOp != null)
				{
					return "PC="+ PC + "\t" + op_Type + "\t" + so1 + "\tnull" + "\t" + destOp + "\n";
				}
				else
				{
					return "PC="+ PC + "\t" + op_Type + "\t" + so1 + "\tnull" + "\tnull" + "\n";
				}
			}
		}
		else
		{
			if(so2 != null)
			{
				if(destOp != null)
				{
					return "PC="+ PC + "\t" + op_Type + "\tnull" + "\t" + so2 + "\t" + destOp + "\n";
				}
				else
				{
					return "PC="+ PC + "\t" + op_Type + "\tnull" + "\t" + so2 + "\tnull" + "\n";
				}
			}
			else
			{
				if(destOp != null)
				{
					return "PC="+ PC + "\t" + op_Type + "\tnull" + "\tnull" + "\t" + destOp + "\n";
				}
				else
				{
					return "PC="+ PC + "\t" + op_Type + "\tnull" + "\tnull" + "\tnull" + "\n";
				}
			}
		}
	}
}
