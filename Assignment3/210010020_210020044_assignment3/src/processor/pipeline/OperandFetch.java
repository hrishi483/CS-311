package processor.pipeline;

import processor.Processor;

import generic.Operand;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;
import generic.Instruction;


public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
		
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}

	
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			OperationType[] type_of_operation = OperationType.values();

			String instruction = Integer.toBinaryString(IF_OF_Latch.getInstruction());

			while(instruction.length()!=32)
			{
				instruction = "0" + instruction;
			}

			String code_of_operand = instruction.substring(0, 5);

			int type_operation = Integer.parseInt(code_of_operand, 2);

			OperationType operation = type_of_operation[type_operation];

			Instruction inst = new Instruction();

			switch(operation)
			{
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
					int rs_number = Integer.parseInt(instruction.substring(5, 10), 2);
					rs1.setValue(rs_number);

					Operand rs2 = new Operand();
					rs2.setOperandType(OperandType.Register);
					rs_number = Integer.parseInt(instruction.substring(10, 15), 2);
					rs2.setValue(rs_number);

					Operand rd = new Operand();
					rd.setOperandType(OperandType.Register);
					rs_number = Integer.parseInt(instruction.substring(15, 20), 2);
					rd.setValue(rs_number);

					inst.setOperationType(type_of_operation[type_operation]);
					inst.setSourceOperand1(rs1);
					inst.setSourceOperand2(rs2);
					inst.setDestinationOperand(rd);
					break;

				case end:
					inst.setOperationType(type_of_operation[type_operation]);
					break;
				case jmp:
					Operand op = new Operand();
					String imm = instruction.substring(10, 32);
					int imm_val = Integer.parseInt(imm, 2);
					if (imm.charAt(0) == '1')
					{
						imm = convert_to_twos_complement(imm);
						imm_val = Integer.parseInt(imm, 2) * -1;
					}
					if (imm_val != 0)
					{
						op.setOperandType(OperandType.Immediate);
						op.setValue(imm_val);
					}
					else
					{
						rs_number = Integer.parseInt(instruction.substring(5, 10), 2);
						op.setOperandType(OperandType.Register);
						op.setValue(rs_number);
					}

					inst.setOperationType(type_of_operation[type_operation]);
					inst.setDestinationOperand(op);
					break;
			
				case beq:
				case bne:
				case blt:
				case bgt:
					rs1 = new Operand();
					rs1.setOperandType(OperandType.Register);
					rs_number = Integer.parseInt(instruction.substring(5, 10), 2);
					rs1.setValue(rs_number);

					// destination register
					rs2 = new Operand();
					rs2.setOperandType(OperandType.Register);
					rs_number = Integer.parseInt(instruction.substring(10, 15), 2);
					rs2.setValue(rs_number);

					// Immediate value
					rd = new Operand();
					rd.setOperandType(OperandType.Immediate);
					imm = instruction.substring(15, 32);
					imm_val = Integer.parseInt(imm, 2);
					if (imm.charAt(0) == '1')
					{
						imm = convert_to_twos_complement(imm);
						imm_val = Integer.parseInt(imm, 2) * -1;
					}
					rd.setValue(imm_val);

					inst.setOperationType(type_of_operation[type_operation]);
					inst.setSourceOperand1(rs1);
					inst.setSourceOperand2(rs2);
					inst.setDestinationOperand(rd);
					break;

				default:
					// Source register 1
					rs1 = new Operand();
					rs1.setOperandType(OperandType.Register);
					rs_number = Integer.parseInt(instruction.substring(5, 10), 2);
					rs1.setValue(rs_number);

					// Destination register
					rd = new Operand();
					rd.setOperandType(OperandType.Register);
					rs_number = Integer.parseInt(instruction.substring(10, 15), 2);
					rd.setValue(rs_number);

					// Immediate values
					rs2 = new Operand();
					rs2.setOperandType(OperandType.Immediate);
					imm = instruction.substring(15, 32);
					imm_val = Integer.parseInt(imm, 2);
					if (imm.charAt(0) == '1')
					{
						imm = convert_to_twos_complement(imm);
						imm_val = Integer.parseInt(imm, 2) * -1;
					}
					rs2.setValue(imm_val);

					inst.setOperationType(type_of_operation[type_operation]);
					inst.setSourceOperand1(rs1);
					inst.setSourceOperand2(rs2);
					inst.setDestinationOperand(rd);
					break;
			}

			OF_EX_Latch.set_OF_EX_LatchType_Instruction(inst);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}


	public static String convert_to_twos_complement(String bin)
	{
		String ones = "";
        String twos = "";
		
        for (int i = 0; i < bin.length(); i++)
        {
            ones = ones + switchChar(bin.charAt(i));
        }

        StringBuilder builder = new StringBuilder(ones);

        boolean value_b = false;

        for (int i = ones.length() - 1; i > 0; i--)
        {
            if (ones.charAt(i) == '1')
            {
                builder.setCharAt(i, '0');
            }
            else
            {
                builder.setCharAt(i, '1');
                value_b = true;
                break;
            }
        }
        if (!value_b)
        {
            builder.append("1", 0, 7);
        }
        twos = builder.toString();

        return twos;
    }

	public static char switchChar(char c)
	{
		if(c == '0'){
          return '1';
		}

        return '0';
    }
	

}
