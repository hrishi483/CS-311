package processor.pipeline;

import generic.Instruction.OperationType;
import generic.Operand.OperandType;
import generic.Instruction;
import generic.Statistics;
import processor.Processor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Execute {
	Processor containingProcessor;

	IF_EnableLatchType IF_EnableLatch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	IF_OF_LatchType IF_OF_Latch;

	public int performBranchOperation(OperationType op_type, int operand_1, int operand_2, int imm, int current_ProgramCounter) {
		int alu_result=0;
		if ((op_type == OperationType.beq && operand_1 == operand_2) ||(op_type == OperationType.bne && operand_1 != operand_2) ||
					(op_type == OperationType.blt && operand_1 < operand_2) ||
					(op_type == OperationType.bgt && operand_1 > operand_2)) {
					alu_result = imm + current_ProgramCounter;
					EX_IF_Latch.setIS_enable(true, alu_result);
				}	
		return alu_result;
	}
	



	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch,
			EX_IF_LatchType eX_IF_Latch, IF_OF_LatchType iF_OF_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	

	public void performEX() {
		if (OF_EX_Latch.getIsNOP()) {
			EX_MA_Latch.setIsNOP(true);
			OF_EX_Latch.setIsNOP(false);
			EX_MA_Latch.setInstruction(null);
		} 
		else if (OF_EX_Latch.isEX_enable()) {
			Instruction instrn = OF_EX_Latch.getInstruction();
			System.out.println("EX is enabled: " + instrn);
			EX_MA_Latch.setInstruction(instrn);
			OperationType op_type = instrn.getOperationType();
			int operand_Code = Arrays.asList(OperationType.values()).indexOf(op_type);
			int current_ProgramCounter = containingProcessor.getRegisterFile().getProgramCounter() - 1;

			if (operand_Code == 24 || operand_Code == 25 || operand_Code == 26 || operand_Code == 27 || operand_Code == 28 || operand_Code == 29) {
				Statistics.setNumberOfBranchTaken(Statistics.getNumberOfBranchTaken() + 2);
				IF_EnableLatch.setIF_enable(false);
				IF_OF_Latch.setOF_enable(false);
				OF_EX_Latch.setEX_enable(false);
			}
			
			int alu_result = 0;

			if (operand_Code % 2 == 0 && operand_Code < 21) {
				int operand_1 = containingProcessor.getRegisterFile().getValue(instrn.getSourceOperand1().getValue());
				int operand_2 = containingProcessor.getRegisterFile().getValue(instrn.getSourceOperand2().getValue());
			
				// Create a map to associate each op_type with its operation
				Map<OperationType, BiFunction<Integer, Integer, Integer>> operationMap = new HashMap<>();
				operationMap.put(OperationType.add, (a, b) -> a + b);
				operationMap.put(OperationType.sub, (a, b) -> a - b);
				operationMap.put(OperationType.mul, (a, b) -> a * b);
				operationMap.put(OperationType.div, (a, b) -> {
					int result = a / b;
					int remainder = a % b;
					containingProcessor.getRegisterFile().setValue(31, remainder);
					return result;
				});
				operationMap.put(OperationType.and, (a, b) -> a & b);
				operationMap.put(OperationType.or, (a, b) -> a | b);
				operationMap.put(OperationType.xor, (a, b) -> a ^ b);
				operationMap.put(OperationType.slt, (a, b) -> a < b ? 1 : 0);
				operationMap.put(OperationType.sll, (a, b) -> a << b);
				operationMap.put(OperationType.srl, (a, b) -> a >>> b);
				operationMap.put(OperationType.sra, (a, b) -> a >> b);
			
				// Use the map to perform the operation based on op_type
				if (operationMap.containsKey(op_type)) {
					BiFunction<Integer, Integer, Integer> operation = operationMap.get(op_type);
					alu_result = operation.apply(operand_1, operand_2);
				}
			}
			
			else if (operand_Code < 23) {
				int i = instrn.getSourceOperand1().getValue();
				int operand_1 = containingProcessor.getRegisterFile().getValue(i);
				int operand_2 = instrn.getSourceOperand2().getValue();

				// Create a map to associate each op_type with its operation
				Map<OperationType, BiFunction<Integer, Integer, Integer>> operationMap = new HashMap<>();
				operationMap.put(OperationType.addi, (a, b) -> a + b);
				operationMap.put(OperationType.subi, (a, b) -> a - b);
				operationMap.put(OperationType.muli, (a, b) -> a * b);
				operationMap.put(OperationType.divi, (a, b) -> {
					int result = a / b;
					int remainder = a % b;
					System.out.println(result + ", " + remainder + "........");
					containingProcessor.getRegisterFile().setValue(31, remainder);
					return result;
				});
				operationMap.put(OperationType.andi, (a, b) -> a & b);
				operationMap.put(OperationType.ori, (a, b) -> a | b);
				operationMap.put(OperationType.xori, (a, b) -> a ^ b);
				operationMap.put(OperationType.slti, (a, b) -> a < b ? 1 : 0);
				operationMap.put(OperationType.slli, (a, b) -> a << b);
				operationMap.put(OperationType.srli, (a, b) -> a >>> b);
				operationMap.put(OperationType.srai, (a, b) -> a >> b);
				operationMap.put(OperationType.load, (a, b) -> a + b);

				// Use the map to perform the operation based on op_type
				if (operationMap.containsKey(op_type)) {
					BiFunction<Integer, Integer, Integer> operation = operationMap.get(op_type);
					alu_result = operation.apply(operand_1, operand_2);
				}
			}
 
			else if (operand_Code == 24) {
				OperandType optype = instrn.getDestinationOperand().getOperandType();
				int imm = 0;
				if (optype == OperandType.Register) {
					imm = containingProcessor.getRegisterFile()
							.getValue(instrn.getDestinationOperand().getValue());
				} else {
					imm = instrn.getDestinationOperand().getValue();
				}
				alu_result = imm + current_ProgramCounter;
				EX_IF_Latch.setIS_enable(true, alu_result);
			} 
			else if (operand_Code == 23) {
				int operand_1 = containingProcessor.getRegisterFile().getValue(instrn.getDestinationOperand().getValue());
				int operand_2 = instrn.getSourceOperand2().getValue();
				alu_result = operand_1 + operand_2;
			}
			
			else if (operand_Code < 29) {
				int operand_1 = containingProcessor.getRegisterFile().getValue(instrn.getSourceOperand1().getValue());
				int operand_2 = containingProcessor.getRegisterFile().getValue(instrn.getSourceOperand2().getValue());
				int imm = instrn.getDestinationOperand().getValue();
				
				alu_result = performBranchOperation(op_type, operand_1, operand_2, imm, current_ProgramCounter);
					
			}
			EX_MA_Latch.setALU_result(alu_result);
			EX_MA_Latch.setMA_enable(true);
		}
	}

}
