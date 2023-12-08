package processor.pipeline;

import javax.lang.model.util.ElementScanner6;

import generic.Instruction;
import generic.Operand;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;
import generic.Simulator;
import generic.Statistics;
import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, MA_RW_LatchType mA_RW_Latch, EX_MA_LatchType eX_MA_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	static String findTwoscomplement(StringBuffer str) {
		int n = str.length();

		int i;
		for (i = n - 1; i >= 0; i--)
			if (str.charAt(i) == '1')
				break;

		if (i == -1)
			return "1" + str;

		for (int k = i - 1; k >= 0; k--) {

			if (str.charAt(k) == '1')
				str.replace(k, k + 1, "0");
			else
				str.replace(k, k + 1, "1");
		}

		return str.toString();
	}
	static int convert_to_int(String binary_num)
	{
		if (binary_num.charAt(0)=='0')
		{
			return Integer.parseInt(binary_num,2);
		}
		else
		{
			StringBuffer updated = new StringBuffer();
			updated.append(binary_num);
			binary_num = findTwoscomplement(updated);
			int a = Integer.parseInt(binary_num,2);
			return -a;




		}


		
	}

	public static boolean checkDataInterlock(Instruction latch_Instruction, int register1, int register2)
	{

		
		int opcode;
		int register_to_read;
		
		if (latch_Instruction !=null && latch_Instruction.getOperationType()!=null)
		{
			opcode = latch_Instruction.getOperationType().ordinal();
		}
		else
		{
			opcode = Integer.MAX_VALUE;
		}
		if(( opcode <=21 && opcode %2 ==0) || (opcode <=21 && opcode % 2!=0) || (opcode==22) || (opcode==23))
		{
			if (latch_Instruction!=null)
			{
				register_to_read = latch_Instruction.getDestinationOperand().getValue();
			}
			else
			{
				register_to_read = -1;
			}
			if (register1 == register_to_read || register2 == register_to_read)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	public void create_Bubble()
	{
		IF_EnableLatch.setIF_enable(false);
		OF_EX_Latch.setIsNOP(true);
		OF_EX_Latch.setInstruction(null);
		Statistics.setNumberOfDataInterlocks(Statistics.getNumberOfDataInterlocks()+1);

	}

	public void performOF() {
		if(IF_OF_Latch.getNOP())
		{
			OF_EX_Latch.setIsNOP(true);
			IF_OF_Latch.setIsNOP(false);
			OF_EX_Latch.setInstruction(null);
			IF_EnableLatch.setIF_enable(true);
		}
		else if (IF_OF_Latch.isOF_enable()) {
			//System.out.println("OF Stage Entered");
			OF_EX_Latch.setEX_enable(true);
			IF_EnableLatch.setIF_enable(true);
			
			int int_instruction = IF_OF_Latch.getInstruction();
			int total_bits = 32;
			String instruction = Integer.toBinaryString(int_instruction);

			while (instruction.length() != 32) {
				instruction = "0" + instruction;
			}

			String opcode = instruction.substring(0, 5);
			int operation_index = Integer.parseInt(opcode, 2);
			OperationType[] operations = OperationType.values();
			OperationType operation = operations[operation_index];

			Instruction OF_EX_Inst = new Instruction();
			int operand1=0,operand2=0,immediate=0,destination_operand=0;
			Instruction OF_EX_Latch_Instruction = OF_EX_Latch.getInstruction();
			Instruction EX_MA_Latch_Instruction = EX_MA_Latch.getInstruction();
			Instruction MA_RW_Latch_Instruction = MA_RW_Latch.getInstruction();
			boolean present_in_other_latch=false;
			switch (operation) {
				case end:
					OF_EX_Inst.setOperationType(operation);
					operand1=0;
					operand2=0;
					//System.out.println("End Instruction Fetched");
					IF_EnableLatch.setIF_enable(false);
					break;
				case jmp:
					Operand opr = new Operand();
					String immString = instruction.substring(10, 32);
					immediate = Integer.parseInt(immString, 2);
					if (immString.charAt(0) == '1') {

						StringBuffer temp = new StringBuffer();
						temp.append(immString);
						immString = findTwoscomplement(temp);
						immediate = Integer.parseInt(immString, 2)* -1;

					}

					if (immediate != 0) {
						opr.setOperandType(OperandType.Immediate);
						opr.setValue(immediate);
					} else {
						int register = Integer.parseInt(instruction.substring(5, 10), 2);
						opr.setOperandType(OperandType.Register);
						opr.setValue(register);

					}
					OF_EX_Inst.setOperationType(operation);
					OF_EX_Inst.setDestinationOperand(opr);
					operand1=0;
					operand2=0;
					//System.out.println("Jmp instruction Fetched");


					break;
				case add:
				case sub:
				case mul:
				case div:
				case and:
				case or:
				case xor:
				case slt:
				case sll:
				case srl:
				case sra:
					Operand rs1 = new Operand();
					rs1.setOperandType(OperandType.Register);
					rs1.setValue(Integer.parseInt(instruction.substring(5, 10), 2));
					int rs1_value= Integer.parseInt(instruction.substring(5, 10), 2);
					
					Operand rs2 = new Operand();
					rs2.setOperandType(OperandType.Register);
					rs2.setValue(Integer.parseInt(instruction.substring(10, 15), 2));
					int rs2_value = Integer.parseInt(instruction.substring(10, 15), 2);

					if (checkDataInterlock(OF_EX_Latch_Instruction, rs1_value, rs2_value))
					present_in_other_latch= true;
					if (checkDataInterlock(EX_MA_Latch_Instruction, rs1_value, rs2_value))
					present_in_other_latch= true;

					if (checkDataInterlock(MA_RW_Latch_Instruction, rs1_value, rs2_value))
					present_in_other_latch= true;
					if (present_in_other_latch)
					{
						this.create_Bubble();
						//System.out.println("Data interlock Found, created bubble");
						break;
					}

					Operand rd = new Operand();
					rd.setOperandType(OperandType.Register);
					rd.setValue(Integer.parseInt(instruction.substring(15, 20), 2));
					OF_EX_Inst.setOperationType(operation);
					OF_EX_Inst.setDestinationOperand(rd);
					OF_EX_Inst.setSourceOperand1(rs1);
					OF_EX_Inst.setSourceOperand2(rs2);
					operand1 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand1().getValue());
					operand2 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand2().getValue());
					destination_operand = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand2().getValue());
					//System.out.println(operation +" " +"rs1 and value "+ rs1 + " "+ operand1 + " rs2 and value  "+ rs2 +" " +operand2 +" "+ " destr and value " + rd + destination_operand);
					break;
				case beq:
				case bne:
				case blt:
				case bgt:
					rs1 = new Operand();
					rs1.setOperandType(OperandType.Register);
					rs1.setValue(Integer.parseInt(instruction.substring(5, 10), 2));
					rs1_value = Integer.parseInt(instruction.substring(5, 10), 2);
					rs2 = new Operand();
					rs2.setOperandType(OperandType.Register);
					rs2.setValue(Integer.parseInt(instruction.substring(10, 15), 2));
					rs2_value = Integer.parseInt(instruction.substring(10, 15), 2);

					if (checkDataInterlock(OF_EX_Latch_Instruction, rs1_value, rs2_value))
					present_in_other_latch= true;
					if (checkDataInterlock(EX_MA_Latch_Instruction, rs1_value, rs2_value))
					present_in_other_latch= true;
					if (checkDataInterlock(MA_RW_Latch_Instruction, rs1_value, rs2_value))
					present_in_other_latch= true;
					if (present_in_other_latch)
					{
						this.create_Bubble();
						//System.out.println("Data interlock Found, created bubble");
						break;
					}

					opr = new Operand();
					opr.setOperandType(OperandType.Immediate);
					immString = instruction.substring(15, 32);
					immediate = Integer.parseInt(immString,2);
					if (immString.charAt(0) == '1') {

						StringBuffer temp = new StringBuffer();
						temp.append(immString);
						immString = findTwoscomplement(temp);
						immediate = Integer.parseInt(immString, 2)* -1;

					}
					opr.setValue(immediate);

					OF_EX_Inst.setOperationType(operation);
					OF_EX_Inst.setDestinationOperand(opr);
					OF_EX_Inst.setSourceOperand1(rs1);
					OF_EX_Inst.setSourceOperand2(rs2);
					operand1 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand1().getValue());
					operand2 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand2().getValue());
					destination_operand = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand2().getValue());
					//System.out.println(operation + " imm_val "+ immediate + " opr1 "+operand1 + " opr2 "+operand2 +" destr " + destination_operand );
					break;
				case addi:
				case subi:
				case muli:
				case divi:
				case andi:
				case ori:
				case xori:
				case slti:
				case slli:
				case srli:
				case srai:
				case load:
				case store:
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				rs1.setValue(Integer.parseInt(instruction.substring(5, 10), 2));
				rs1_value =  Integer.parseInt(instruction.substring(5, 10), 2);



				
				rd = new Operand();
				rd.setOperandType(OperandType.Register);
				rd.setValue(Integer.parseInt(instruction.substring(10, 15), 2));
				int rd_value=Integer.parseInt(instruction.substring(10, 15), 2);
				opr = new Operand();
				opr.setOperandType(OperandType.Immediate);
				immString = instruction.substring(15, 32);
				immediate = Integer.parseInt(immString,2);
					if (checkDataInterlock(OF_EX_Latch_Instruction, rs1_value, rd_value))
						present_in_other_latch= true;
					if (checkDataInterlock(EX_MA_Latch_Instruction, rs1_value, rd_value))
						present_in_other_latch= true;
					if (checkDataInterlock(MA_RW_Latch_Instruction, rs1_value, rd_value))
						present_in_other_latch= true;
					if (present_in_other_latch)
					{
						this.create_Bubble();
						//System.out.println("Data interlock Found, created bubble");
						break;
					}
				if (immString.charAt(0) == '1') {

					StringBuffer temp = new StringBuffer();
					temp.append(immString);
					immString = findTwoscomplement(temp);
					immediate = Integer.parseInt(immString, 2) * -1;

				}
				opr.setValue(immediate);


				OF_EX_Inst.setOperationType(operation);
				OF_EX_Inst.setDestinationOperand(rd);
				OF_EX_Inst.setSourceOperand1(rs1);
				OF_EX_Inst.setSourceOperand2(opr);

				operand1 = containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getSourceOperand1().getValue());

				destination_operand= containingProcessor.getRegisterFile().getValue(OF_EX_Inst.getDestinationOperand().getValue());
						break;

			}
			OF_EX_Latch.setInstruction(OF_EX_Inst);
			OF_EX_Latch.setOperand1(operand1);
			OF_EX_Latch.setOperand2(operand2);
			OF_EX_Latch.setImmediate(immediate);
			OF_EX_Latch.setDestinationOperand(destination_operand);

		}
	}

}
