package generic;

public class Operand {
	
	public enum OperandType {Register, Immediate, Label};
	
	int val;
	String lVal; 
	OperandType op_Type;	
					
	public String getLabelValue() {
		return lVal;
	}
	public void setLabelValue(String labelValue) {
		this.lVal = labelValue;
	}
	public OperandType getOperandType() {
		return op_Type;
	}
	
	public void setValue(int value) {
		this.val = value;
	}

	public void setOperandType(OperandType operandType) {
		this.op_Type = operandType;
	}
	public int getValue() {
		return val;
	}
	public String toString()
	{
		if(op_Type == OperandType.Register || op_Type == OperandType.Immediate)
		{
			return "[" + op_Type.toString() + ":" + val + "]";
		}
		else
		{
			return "[" + op_Type.toString() + ":" + lVal + "]";
		}
	}
}
