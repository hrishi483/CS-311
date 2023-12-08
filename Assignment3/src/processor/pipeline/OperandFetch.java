package processor.pipeline;

import generic.Misc;
import processor.Processor;

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
	
	public String getOperantionType_from_value(int value){
		int instruction = value /* Replace with your instruction value */;
		String opType = "";
		
		if (instruction == 1) {
			opType = "add";
		} else if (instruction == 2) {
			opType = "sub";
		} else if (instruction == 3) {
			opType = "subi";
		} else if (instruction == 4) {
			opType = "mul";
		} else if (instruction == 5) {
			opType = "muli";
		} else if (instruction == 6) {
			opType = "div";
		} else if (instruction == 7) {
			opType = "divi";
		} else if (instruction == 8) {
			opType = "and";
		} else if (instruction == 9) {
			opType = "andi";
		} else if (instruction == 10) {
			opType = "or";
		} else if (instruction == 11) {
			opType = "ori";
		} else if (instruction == 12) {
			opType = "xor";
		} else if (instruction == 13) {
			opType = "xori";
		} else if (instruction == 14) {
			opType = "slt";
		} else if (instruction == 15) {
			opType = "slti";
		} else if (instruction == 16) {
			opType = "sll";
		} else if (instruction == 17) {
			opType = "slli";
		} else if (instruction == 18) {
			opType = "srl";
		} else if (instruction == 19) {
			opType = "srli";
		} else if (instruction == 20) {
			opType = "sra";
		} else if (instruction == 21) {
			opType = "srai";
		} else if (instruction == 22 ) {
			opType = "load";
		} else if (instruction == 23) {
			opType = "store";
		} else if (instruction == 24) {
			opType = "jmp";
		} else if (instruction == 25) {
			opType = "beq";
		} else if (instruction == 26) {
			opType = "bne";
		} else if (instruction == 27) {
			opType = "blt";
		} else if (instruction == 28) {
			opType = "bgt";
		} else if (instruction == 29) {
			opType = "end";
		}else {
			Misc.printErrorAndExit("Unkown Operand with opcode = "+value+"found"); // Handle unknown instructions here
		}
		return opType;
		
	}

	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//TODO

			OF_EX_Latch.pc=containingProcessor.getRegisterFile().getProgramCounter() - 1;

			//1. Get the instruction from IF_OF_Latch
			int instruction = IF_OF_Latch.getInstruction();
			int opcode = instruction>>>27;
			String opType = getOperantionType_from_value(opcode);
			
			OF_EX_Latch.opcode = opcode;
			OF_EX_Latch.rs1 = containingProcessor.getRegisterFile().getValue((instruction << 5) >>> 27);

			if (opType=="add" || opType == "sub" || opType == "mul" || opType == "div" || opType == "and" || opType == "or" 
			|| opType == "xor" || opType == "slt" || opType == "sll" || opType == "srl" || opType == "sra"){
				
				OF_EX_Latch.rs2 = containingProcessor.getRegisterFile().getValue((instruction << 10) >>> 27);
				
				OF_EX_Latch.rd = containingProcessor.getRegisterFile().getValue((instruction << 15) >>> 27);
			}
			
			//2.Calculate immx by taking 18 bits instruction, using odifiers (17,18) to make it 32 bits
			else if (opType=="addi" || opType == "subi" || opType == "muli" || opType == "divi" || opType == "andi" || opType == "ori" 
			|| opType == "xori" || opType == "slti" || opType == "slli" || opType == "srli" || opType == "srai"){
				OF_EX_Latch.rd = (instruction<<10)>>>27;
				OF_EX_Latch.imm = (instruction<<15)>>>27;
			}

			else if(opType == "jmp" || opType == "end" ){
				OF_EX_Latch.imm = (instruction << 10) >> 10;
				OF_EX_Latch.rd = containingProcessor.getRegisterFile().getValue((instruction << 5) >>> 27);				
			}

			else{			
				OF_EX_Latch.rd = containingProcessor.getRegisterFile().getValue((instruction << 5) >>> 27);				
				OF_EX_Latch.imm = (instruction <<15)>> 15;
			}
					

			
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
