package processor.pipeline;

import generic.Operand;
import generic.Statistics;
import processor.Processor;
import generic.Operand.OperandType;
import generic.Instruction;
import generic.Instruction.OperationType;

public class OperandFetch {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	MA_RW_LatchType MA_RW_Latch;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
	}
	
	public void changeBubblePCWhenIssue () {
		// System.out.println("Conflict Observed");
		IF_EnableLatch.setIF_enable(false);
		OF_EX_Latch.setIsNOP(true);
	}
	
	private boolean shouldBubble(Instruction instExStage, Instruction instMaStage, Instruction instRwStage, int reg1, int reg2) {
		return (checkIssue(instExStage, reg1, reg2) ||
				checkIssue(instMaStage, reg1, reg2) ||
				checkIssue(instRwStage, reg1, reg2) ||
				checkIssueForDiv(reg1, reg2));
	}
 	
	public void performOF() {
		if (IF_OF_Latch.isOF_enable()) {
			Statistics.setNumberOfOFInstructions(Statistics.getNumberOfOFInstructions() + 1);
			OperationType[] op_Type = OperationType.values();
			String instrn = Integer.toBinaryString(IF_OF_Latch.getInstruction());
			// System.out.println("OF is enabled with instruction: " + instrn + "..");
			while (instrn.length() != 32) {
				instrn = "0" + instrn;
			}
			String opcode = instrn.substring(0, 5);
			int type_operation = Integer.parseInt(opcode, 2);
			OperationType operation = op_Type[type_operation];
			
			if (operation.ordinal() == 24 || operation.ordinal() == 25 || operation.ordinal() == 26 || operation.ordinal() == 27 || operation.ordinal() == 28 ) {
				IF_EnableLatch.setIF_enable(false);
			}
			

			Instruction inst_ex_stage = OF_EX_Latch.getInstruction();
			Instruction inst_ma_stage = EX_MA_Latch.getInstruction();
			Instruction inst_rw_stage = MA_RW_Latch.getInstruction();
			Instruction inst = new Instruction();
			boolean issue_inst = false;

			switch (operation) {
			case add:
			case sub:
			case mul:
			case div:
				Operand rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				int registerNo = Integer.parseInt(instrn.substring(5, 10), 2);
				rs1.setValue(registerNo);

				Operand rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				int registerNo2 = Integer.parseInt(instrn.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				if (checkIssue(inst_ex_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_ma_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_rw_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssueForDiv(registerNo, registerNo2)) {
					issue_inst = true;
				}
				if (issue_inst) {
					this.changeBubblePCWhenIssue();
					break;
				}

				Operand rd = new Operand();
				rd.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instrn.substring(15, 20), 2);
				rd.setValue(registerNo);

				inst.setOperationType(op_Type[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;
			case and:
			case or:
			case xor:
				 rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				 registerNo = Integer.parseInt(instrn.substring(5, 10), 2);
				rs1.setValue(registerNo);

				 rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				 registerNo2 = Integer.parseInt(instrn.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				if (checkIssue(inst_ex_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_ma_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_rw_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssueForDiv(registerNo, registerNo2)) {
					issue_inst = true;
				}
				if (issue_inst) {
					this.changeBubblePCWhenIssue();
					break;
				}

				 rd = new Operand();
				rd.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instrn.substring(15, 20), 2);
				rd.setValue(registerNo);

				inst.setOperationType(op_Type[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;
			case slt:
			case sll:
			case srl:
			case sra:
				 rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				 registerNo = Integer.parseInt(instrn.substring(5, 10), 2);
				rs1.setValue(registerNo);

				 rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				 registerNo2 = Integer.parseInt(instrn.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				if (checkIssue(inst_ex_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_ma_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_rw_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssueForDiv(registerNo, registerNo2)) {
					issue_inst = true;
				}
				if (issue_inst) {
					this.changeBubblePCWhenIssue();
					break;
				}

				 rd = new Operand();
				rd.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instrn.substring(15, 20), 2);
				rd.setValue(registerNo);

				inst.setOperationType(op_Type[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;

			case end:
				inst.setOperationType(op_Type[type_operation]);
				IF_EnableLatch.setIF_enable(false);
				break;
			case jmp:
				Operand op = new Operand();
				String imm = instrn.substring(10, 32);
				int imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = convert_to_twos_complement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				if (imm_val != 0) {
					op.setOperandType(OperandType.Immediate);
					op.setValue(imm_val);
				} else {
					registerNo = Integer.parseInt(instrn.substring(5, 10), 2);
					op.setOperandType(OperandType.Register);
					op.setValue(registerNo);
				}

				inst.setOperationType(op_Type[type_operation]);
				inst.setDestinationOperand(op);
				break;

			case beq:
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instrn.substring(5, 10), 2);
				rs1.setValue(registerNo);
				
				
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				registerNo2 = Integer.parseInt(instrn.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				
				if (checkIssue(inst_ex_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_ma_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_rw_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssueForDiv(registerNo, registerNo2)) {
					issue_inst = true;
				}
				if (issue_inst) {
					this.changeBubblePCWhenIssue();
					break;
				}

				rd = new Operand();
				rd.setOperandType(OperandType.Immediate);
				imm = instrn.substring(15, 32);
				imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = convert_to_twos_complement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				rd.setValue(imm_val);
				
				inst.setOperationType(op_Type[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;
			case bne:
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instrn.substring(5, 10), 2);
				rs1.setValue(registerNo);
				
				
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				registerNo2 = Integer.parseInt(instrn.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				
				if (checkIssue(inst_ex_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_ma_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_rw_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssueForDiv(registerNo, registerNo2)) {
					issue_inst = true;
				}
				if (issue_inst) {
					this.changeBubblePCWhenIssue();
					break;
				}

				rd = new Operand();
				rd.setOperandType(OperandType.Immediate);
				imm = instrn.substring(15, 32);
				imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = convert_to_twos_complement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				rd.setValue(imm_val);
				
				inst.setOperationType(op_Type[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;
			case blt:
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instrn.substring(5, 10), 2);
				rs1.setValue(registerNo);
				
				
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				registerNo2 = Integer.parseInt(instrn.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				
				if (checkIssue(inst_ex_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_ma_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_rw_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssueForDiv(registerNo, registerNo2)) {
					issue_inst = true;
				}
				if (issue_inst) {
					this.changeBubblePCWhenIssue();
					break;
				}

				rd = new Operand();
				rd.setOperandType(OperandType.Immediate);
				imm = instrn.substring(15, 32);
				imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = convert_to_twos_complement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				rd.setValue(imm_val);
				
				inst.setOperationType(op_Type[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;
			case bgt:
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instrn.substring(5, 10), 2);
				rs1.setValue(registerNo);
				
				
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				registerNo2 = Integer.parseInt(instrn.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				
				if (checkIssue(inst_ex_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_ma_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssue(inst_rw_stage, registerNo, registerNo2))
					issue_inst = true;
				if (checkIssueForDiv(registerNo, registerNo2)) {
					issue_inst = true;
				}
				if (issue_inst) {
					this.changeBubblePCWhenIssue();
					break;
				}

				rd = new Operand();
				rd.setOperandType(OperandType.Immediate);
				imm = instrn.substring(15, 32);
				imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = convert_to_twos_complement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				rd.setValue(imm_val);
				
				inst.setOperationType(op_Type[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;

			default:
				
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instrn.substring(5, 10), 2);
				rs1.setValue(registerNo);

				if (checkIssue(inst_ex_stage, registerNo, registerNo)) {
					issue_inst = true;
				}	
				if (checkIssue(inst_ma_stage, registerNo, registerNo)) {
					issue_inst = true;
				}
				if (checkIssue(inst_rw_stage, registerNo, registerNo)) {
					issue_inst = true;
				}
				if (checkIssueForDiv(registerNo, registerNo)) {
					issue_inst = true;
				}
					
				if (issue_inst) {
					this.changeBubblePCWhenIssue();
					break;
				}

				rd = new Operand();
				rd.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instrn.substring(10, 15), 2);
				rd.setValue(registerNo);

				rs2 = new Operand();
				rs2.setOperandType(OperandType.Immediate);
				imm = instrn.substring(15, 32);
				imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = convert_to_twos_complement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				rs2.setValue(imm_val);
				inst.setOperationType(op_Type[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;
			}
			OF_EX_Latch.setInstruction(inst);
			OF_EX_Latch.setEX_enable(true);
		}
	}


// removed the switch char from here


// updated the check issue function here 
public static boolean checkIssue(Instruction inst, int rg1, int rg2) {
    int instruction_sum = (inst != null && inst.getOperationType() != null) ? inst.getOperationType().ordinal() : 1000;

    if (instruction_sum <= 21 || instruction_sum == 22 || instruction_sum == 23) {
        int dest_reg = (inst != null) ? inst.getDestinationOperand().getValue() : -1;
        return (dest_reg == rg1 || dest_reg == rg2);
    }

    return false;
}



// Modified code to calculate 2's complement	
	public static String convert_to_twos_complement(String bin) {
		int len = bin.length();
		boolean foundOne = false;
		StringBuilder twos = new StringBuilder(len);
	
		// Step 1: Find the first '1' from right to left
		for (int i = len - 1; i >= 0; i--) {
			char bit = bin.charAt(i);
			if (!foundOne) {
				if (bit == '1') {
					foundOne = true;
				}
				twos.insert(0, bit); // Keep copying bits until the first '1'
			} else {
				// Step 2: Invert the remaining bits
				twos.insert(0, (bit == '0') ? '1' : '0');
			}
		}
	
		return twos.toString();
	}
	
	
	

	public boolean checkIssueForDiv(int rg1, int rg2) {
	Instruction inst_ex_stage = OF_EX_Latch.getInstruction();
	Instruction inst_ma_stage = EX_MA_Latch.getInstruction();
	Instruction inst_rw_stage = MA_RW_Latch.getInstruction();
		
	if (rg1 == 31 || rg2 == 31) {
	int inst_ex_count = inst_ex_stage != null && inst_ex_stage.getOperationType() != null ? inst_ex_stage.getOperationType().ordinal() : 1000;
	int inst_ma_count = inst_ma_stage != null && inst_ma_stage.getOperationType() != null ? inst_ma_stage.getOperationType().ordinal() : 1000;
	int inst_rw_count = inst_rw_stage != null && inst_rw_stage.getOperationType() != null ? inst_rw_stage.getOperationType().ordinal() : 1000;
    if (inst_ex_count == 6 || inst_ex_count == 7 || inst_ma_count == 6 || inst_ma_count == 7 || inst_rw_count == 6 || inst_rw_count == 7) {
	// System.out.println("Conflict in division");
	return true;
	} else {
	return false;
	}
	} else {
	return false;
	}
	}

}
