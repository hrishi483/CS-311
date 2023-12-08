package processor.pipeline;

import processor.Processor;
import java.util.Arrays;

import generic.Instruction.OperationType;
import generic.Operand;
import generic.Operand.OperandType;
import generic.Instruction;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch,
			EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	public void performEX() {
		if (OF_EX_Latch.isEX_enable()) {
			Instruction instruction = OF_EX_Latch.get_OF_EX_LatchType_Instruction();
			System.out.println(instruction);
			EX_MA_Latch.set_EX_MA_LatchType_Instruction(instruction);
			OperationType op_type = instruction.getOperationType();
			int opcode = Arrays.asList(OperationType.values()).indexOf(op_type);
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter() - 1;

			int alu_result = 0;

			if (opcode % 2 == 0 && opcode < 21) {
				int op1 = containingProcessor.getRegisterFile().getValue(instruction.getSourceOperand1().getValue());
				int op2 = containingProcessor.getRegisterFile().getValue(instruction.getSourceOperand2().getValue());

				if (op_type == OperationType.add) {
					alu_result = op1 + op2;
				} else if (op_type == OperationType.sub) {
					alu_result = op1 - op2;
				} else if (op_type == OperationType.mul) {
					alu_result = op1 * op2;
				} else if (op_type == OperationType.div) {
					if (op2 == 0) {
						alu_result = 0;
					} else {
						alu_result = op1 / op2;
						int remainder = op1 % op2;
						containingProcessor.getRegisterFile().setValue(31, remainder);
					}
				} else if (op_type == OperationType.and) {
					alu_result = op1 & op2;
				} else if (op_type == OperationType.or) {
					alu_result = op1 | op2;
				} else if (op_type == OperationType.xor) {
					alu_result = op1 ^ op2;
				} else if (op_type == OperationType.slt) {
					if (op1 < op2) {
						alu_result = 1;
					} else {
						alu_result = 0;
					}
				} else if (op_type == OperationType.sll) {
					alu_result = op1 << op2;
				} else if (op_type == OperationType.srl) {
					alu_result = op1 >>> op2;
				} else if (op_type == OperationType.sra) {
					alu_result = op1 >> op2;
				}
			} else if (opcode < 23) {
				int i = instruction.getSourceOperand1().getValue();
				int op1 = containingProcessor.getRegisterFile().getValue(i);
				int op2 = instruction.getSourceOperand2().getValue();

				if (op_type == OperationType.addi) {
					alu_result = op1 + op2;
				} else if (op_type == OperationType.subi) {
					alu_result = op1 - op2;
				} else if (op_type == OperationType.muli) {
					alu_result = op1 * op2;
				} else if (op_type == OperationType.divi) {
					if (op2 == 0) {
						alu_result = 0;
					} else {
						alu_result = op1 / op2;
						int remainder = op1 % op2;
						containingProcessor.getRegisterFile().setValue(31, remainder);
					}
				} else if (op_type == OperationType.andi) {
					alu_result = op1 & op2;
				} else if (op_type == OperationType.ori) {
					alu_result = op1 | op2;
				} else if (op_type == OperationType.xori) {
					alu_result = op1 ^ op2;
				} else if (op_type == OperationType.slti) {
					if (op1 < op2) {
						alu_result = 1;
					} else {
						alu_result = 0;
					}
				} else if (op_type == OperationType.slli) {
					alu_result = op1 << op2;
				} else if (op_type == OperationType.srli) {
					alu_result = op1 >>> op2;
				} else if (op_type == OperationType.srai) {
					alu_result = op1 >> op2;
				} else if (op_type == OperationType.load) {
					alu_result = op1 + op2;
				}
			} else if (opcode == 23) {
				int op1 = containingProcessor.getRegisterFile()
						.getValue(instruction.getDestinationOperand().getValue());
				int op2 = instruction.getSourceOperand2().getValue();
				alu_result = op1 + op2;
			} else if (opcode == 24) {
				OperandType optype = instruction.getDestinationOperand().getOperandType();
				int imm = 0;
				if (optype == OperandType.Register) {
					imm = containingProcessor.getRegisterFile()
							.getValue(instruction.getDestinationOperand().getValue());
				} else {
					imm = instruction.getDestinationOperand().getValue();
				}
				alu_result = imm + currentPC;
				EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
			} else if (opcode < 29) {
				int op1 = containingProcessor.getRegisterFile()
						.getValue(instruction.getSourceOperand1().getValue());
				int op2 = containingProcessor.getRegisterFile()
						.getValue(instruction.getSourceOperand2().getValue());
				int imm = instruction.getDestinationOperand().getValue();

				if (op_type == OperationType.beq) {
					if (op1 == op2) {
						alu_result = imm + currentPC;
						EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
					}
				} else if (op_type == OperationType.bne) {
					if (op1 != op2) {
						alu_result = imm + currentPC;
						EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
					}
				} else if (op_type == OperationType.blt) {
					if (op1 < op2) {
						alu_result = imm + currentPC;
						EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
					}
				} else if (op_type == OperationType.bgt) {
					if (op1 > op2) {
						alu_result = imm + currentPC;
						EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
					}
				}
			}
			System.out.println("alu_result" + Integer.toString(alu_result));
			EX_MA_Latch.set_EX_MA_LatchType_ALU_result(alu_result);
		}

		OF_EX_Latch.setEX_enable(false);
		EX_MA_Latch.setMA_enable(true);
	}
}

			






















// package processor.pipeline;

// import processor.Processor;
// import java.util.Arrays;

// import generic.Instruction.OperationType;
// import generic.Operand;
// import generic.Operand.OperandType;
// import generic.Instruction;

// public class Execute {
// 	Processor containingProcessor;
// 	OF_EX_LatchType OF_EX_Latch;
// 	EX_MA_LatchType EX_MA_Latch;
// 	EX_IF_LatchType EX_IF_Latch;
	
// 	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
// 	{
// 		this.containingProcessor = containingProcessor;
// 		this.OF_EX_Latch = oF_EX_Latch;
// 		this.EX_MA_Latch = eX_MA_Latch;
// 		this.EX_IF_Latch = eX_IF_Latch;
// 	}
	
// 	public void performEX()
// 	{
// 		if(OF_EX_Latch.isEX_enable())
// 		{
// 			Instruction instruction = OF_EX_Latch.get_OF_EX_LatchType_Instruction();
// 			System.out.println(instruction);
// 			EX_MA_Latch.set_EX_MA_LatchType_Instruction(instruction);
// 			OperationType op_type = instruction.getOperationType();
// 			int opcode = Arrays.asList(OperationType.values()).indexOf(op_type);
// 			int currentPC = containingProcessor.getRegisterFile().getProgramCounter() - 1;

// 			int alu_result = 0;

// 			if(opcode % 2 == 0 && opcode < 21)
// 			{
// 				int op1 = containingProcessor.getRegisterFile().getValue(
// 					instruction.getSourceOperand1().getValue());
// 				int op2 = containingProcessor.getRegisterFile().getValue(
// 					instruction.getSourceOperand2().getValue());

// 				switch(op_type)
// 				{
// 					case add:
// 						alu_result = op1 + op2;
// 						break;
// 					case sub:
// 						alu_result = op1 - op2;
// 						break;
// 					case mul:
// 						alu_result = op1 * op2;
// 						break;
// 					case div:
// 						alu_result = op1 / op2;
// 						int remainder = op1 % op2;
// 						containingProcessor.getRegisterFile().setValue(31, remainder);
// 						break;
// 					case and:
// 						alu_result = op1 & op2;
// 						break;
// 					case or:
// 						alu_result = op1 | op2;
// 						break;
// 					case xor:
// 						alu_result = op1 ^ op2;
// 						break;
// 					case slt:
// 						if(op1 < op2)
// 							alu_result = 1;
// 						else
// 							alu_result = 0;
// 						break;
// 					case sll:
// 						alu_result = op1 << op2;
// 						break;
// 					case srl:
// 						alu_result = op1 >>> op2;
// 						break;
// 					case sra:
// 						alu_result = op1 >> op2;
// 						break;
// 					default:
// 						break;
// 				}
// 			}
// 			else if(opcode < 23)
// 			{
// 				int i = instruction.getSourceOperand1().getValue();
// 				int op1 = containingProcessor.getRegisterFile().getValue(i);
// 				int op2 = instruction.getSourceOperand2().getValue();

// 				switch(op_type)
// 				{
// 					case addi:
// 						alu_result = op1 + op2;
// 						break;
// 					case subi:
// 						alu_result = op1 - op2;
// 						break;
// 					case muli:
// 						alu_result = op1 * op2;
// 						break;
// 					case divi:
// 						alu_result = op1 / op2;
// 						int remainder = op1 % op2;
// 						containingProcessor.getRegisterFile().setValue(31, remainder);
// 						break;
// 					case andi:
// 						alu_result = op1 & op2;
// 						break;
// 					case ori:
// 						alu_result = op1 | op2;
// 						break;
// 					case xori:
// 						alu_result = op1 ^ op2;
// 						break;
// 					case slti:
// 						if(op1 < op2)
// 							alu_result = 1;
// 						else
// 							alu_result = 0;
// 						break;
// 					case slli:
// 						alu_result = op1 << op2;
// 						break;
// 					case srli:
// 						alu_result = op1 >>> op2;
// 						break;
// 					case srai:
// 						alu_result = op1 >> op2;
// 						break;
// 					case load:
// 						alu_result = op1 + op2;
// 						break;
// 					default:
// 						break;
// 				}
// 			}
// 			else if(opcode == 23)
// 			{
// 				int op1 = containingProcessor.getRegisterFile().getValue(
// 					instruction.getDestinationOperand().getValue());
// 				int op2 = instruction.getSourceOperand2().getValue();
// 				alu_result = op1 + op2;
// 			}
// 			else if(opcode == 24)
// 			{
// 				OperandType optype = instruction.getDestinationOperand().getOperandType();
// 				int imm = 0;
// 				if (optype == OperandType.Register)
// 				{
// 					imm = containingProcessor.getRegisterFile().getValue(
// 						instruction.getDestinationOperand().getValue());
// 				}
// 				else
// 				{
// 					imm = instruction.getDestinationOperand().getValue();
// 				}
// 				alu_result = imm + currentPC;
// 				EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
// 			}
// 			else if(opcode < 29)
// 			{
// 				int op1 = containingProcessor.getRegisterFile().getValue(
// 					instruction.getSourceOperand1().getValue());
// 				int op2 = containingProcessor.getRegisterFile().getValue(
// 					instruction.getSourceOperand2().getValue());
// 				int imm = instruction.getDestinationOperand().getValue();
// 				switch(op_type)
// 				{
// 					case beq:
// 						if(op1 == op2)
// 						{
// 							alu_result = imm + currentPC;
// 							EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
// 						}
// 						break;
// 					case bne:
// 						if(op1 != op2)
// 						{
// 							alu_result = imm + currentPC;
// 							EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
// 						}

// 						break;
// 					case blt:
// 						if(op1 < op2)
// 						{
// 							alu_result = imm + currentPC;
// 							EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
// 						}
// 						break;
// 					case bgt:
// 						if(op1 > op2)
// 						{
// 							alu_result = imm + currentPC;
// 							EX_IF_Latch.set_EX_IF_LatchType_IS_enable(true, alu_result);
// 						}
// 						break;
// 					default:
// 						break;
// 				}
// 			}
// 			System.out.println("alu_result" + Integer.toString(alu_result));
// 			EX_MA_Latch.set_EX_MA_LatchType_ALU_result(alu_result);
// 		}

// 		OF_EX_Latch.setEX_enable(false);
// 		EX_MA_Latch.setMA_enable(true);
// 	}

// }
