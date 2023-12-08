package processor.pipeline;

import generic.Misc;
import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
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
	public void performEX()
	{
		//TODO
		EX_MA_Latch.opcode = OF_EX_Latch.get_Opcode();
		EX_MA_Latch.pc = OF_EX_Latch.get_pc();

		int result = 0;
		int shift=0;
		long sum=0;  //overflow
		EX_IF_Latch.isBranchtaken = false;
		int rs1=0;
		int rs2=0;
		int imm=0;
		String operandType = getOperantionType_from_value(OF_EX_Latch.get_Opcode());

		if (operandType == "add"){
			rs1 = OF_EX_Latch.get_rs1();
			rs2 = OF_EX_Latch.get_rs2();

			result = rs1 + rs2;
			sum = (long) rs1 + (long) rs2;
			sum>>=32;
			
			if (sum!=0){
				EX_MA_Latch.excess = (int)sum;
			}
		}
		if (operandType == "addi"){
			rs1 = OF_EX_Latch.get_rs1();
			imm = OF_EX_Latch.get_imm();

			result = rs1 + imm;
			sum = (long) rs1 + (long) imm;
			sum>>=32;
			
			if (sum!=0){
				EX_MA_Latch.excess = (int)sum;
			}
		}
		if (operandType == "sub"){
			rs1 = OF_EX_Latch.get_rs1();
			rs2 = OF_EX_Latch.get_rs2();

			result = rs1 - rs2;
			sum = (long) rs1 - (long) rs2;
			
			if (sum!=0){
				EX_MA_Latch.excess = (int)sum;
			}
		}
		if (operandType == "subi"){
			rs1 = OF_EX_Latch.get_rs1();
			imm = OF_EX_Latch.get_imm();

			result = rs1 - imm;
			sum = (long) rs1 - (long) imm;
			sum>>=32;
			
			if (sum!=0){
				EX_MA_Latch.excess = (int)sum;
			}
		}
		if (operandType == "mul"){
			rs1 = OF_EX_Latch.get_rs1();
			rs2 = OF_EX_Latch.get_rs2();

			result = rs1 * rs2;
			sum = (long) rs1 * (long) rs2;
			
			if (sum!=0){
				EX_MA_Latch.excess = (int)sum;
			}
		}
		if (operandType == "muli"){
			rs1 = OF_EX_Latch.get_rs1();
			imm = OF_EX_Latch.get_imm();

			result = rs1 * imm;
			sum = (long) rs1 * (long) imm;
			sum>>=32;
			
			if (sum!=0){
				EX_MA_Latch.excess = (int)sum;
			}
		}
		if (operandType == "div"){
			rs1 = OF_EX_Latch.get_rs1();
			rs2 = OF_EX_Latch.get_rs2();

			result = rs1 / rs2;
			if (rs1%rs2!=0){
				EX_MA_Latch.excess = (int)(rs1%rs2);
			}
		}
		if (operandType == "divi"){
			rs1 = OF_EX_Latch.get_rs1();
			imm = OF_EX_Latch.get_imm();

			result = rs1 / imm;
			if (rs1%imm!=0){
				EX_MA_Latch.excess = (int)(rs1%imm);
			}
		}
		if (operandType=="and"){
			result =OF_EX_Latch.get_rs1() & OF_EX_Latch.get_rs2();
		}
		if (operandType == "andi"){
			result =OF_EX_Latch.get_rs1() & OF_EX_Latch.get_imm();
		}
		if (operandType=="or"){
			result =OF_EX_Latch.get_rs1() | OF_EX_Latch.get_rs2();
		}
		if (operandType == "ori"){
			result =OF_EX_Latch.get_rs1() | OF_EX_Latch.get_imm();
		}
		if (operandType=="xor"){
			result =OF_EX_Latch.get_rs1() ^ OF_EX_Latch.get_rs2();
		}
		if (operandType == "xori"){
			result =OF_EX_Latch.get_rs1() ^ OF_EX_Latch.get_imm();
		}
		if(operandType == "slt"){
			if (OF_EX_Latch.get_rs1() < OF_EX_Latch.get_rs2()){
						result = 1;}
					else{
						result = 0;}
		}
		if(operandType == "slti"){
			if (OF_EX_Latch.get_rs1() < OF_EX_Latch.get_imm()){
						result = 1;}
					else{
						result = 0;}
		}
		if (operandType == "sll"){
			rs1 = OF_EX_Latch.get_rs1();
			rs2 = OF_EX_Latch.get_rs2();

			result = rs1 << rs2;
			sum = (long)rs1 << (long)rs2;
			sum>>=32;
			
			EX_MA_Latch.excess = (int)sum;
		}
		if (operandType == "slli"){
			rs1 = OF_EX_Latch.get_rs1();
			imm = OF_EX_Latch.get_imm();

			result = rs1 << imm;
			sum = (long)rs1 << (long)imm;
			sum>>=32;

			EX_MA_Latch.excess = (int)(rs1%imm);
		}
		if (operandType == "srl"){
			rs1 = OF_EX_Latch.get_rs1();
			rs2 = OF_EX_Latch.get_rs2();
			result = rs1 >>> rs2;
			shift = rs1 & ((int)Math.pow((double)2, (double)rs2) - 1);
			EX_MA_Latch.excess = (int) shift;
		}
		if (operandType == "srli"){
			rs1 = OF_EX_Latch.get_rs1();
			imm = OF_EX_Latch.get_imm();
			result = rs1 >>> imm;
			shift = rs1 & ((int)Math.pow((double)2, (double)imm) - 1);
			EX_MA_Latch.excess = (int) shift;
		}
		if (operandType == "sra"){
			rs1 = OF_EX_Latch.get_rs1();
			rs2 = OF_EX_Latch.get_rs2();
			result = rs1 >> rs2;
			shift = rs1 & ((int)Math.pow((double)2, (double)rs2) - 1);
			EX_MA_Latch.excess = (int) shift;
		}
		if (operandType == "srai"){
			rs1 = OF_EX_Latch.get_rs1();
			imm = OF_EX_Latch.get_imm();
			result = rs1 >> imm;
			shift = rs1 & ((int)Math.pow((double)2, (double)imm) - 1);
			EX_MA_Latch.excess = (int) shift;
		}
		if(operandType=="load"){
			result = OF_EX_Latch.get_rs1() + OF_EX_Latch.get_imm();
		}
		if(operandType=="store"){
			result = OF_EX_Latch.get_rd() + OF_EX_Latch.get_imm();
		}
		if (operandType=="jmp") {
			int current_pc=OF_EX_Latch.get_pc();
			imm = OF_EX_Latch.get_imm();
			containingProcessor.getRegisterFile().setProgramCounter(current_pc+imm);
			EX_IF_Latch.isBranchtaken = true;
		}
		if(operandType=="beq"){
			if(OF_EX_Latch.get_rd() == OF_EX_Latch.get_rs1()){
				EX_IF_Latch.isBranchtaken = true;
				int current_pc=OF_EX_Latch.get_pc();
				imm = OF_EX_Latch.get_imm();
				containingProcessor.getRegisterFile().setProgramCounter(current_pc+imm);					
			}
		}
		if(operandType=="bgt"){
			if(OF_EX_Latch.get_rd() > OF_EX_Latch.get_rs1()){
				EX_IF_Latch.isBranchtaken = true;
				int current_pc=OF_EX_Latch.get_pc();
				imm = OF_EX_Latch.get_imm();
				containingProcessor.getRegisterFile().setProgramCounter(current_pc+imm);					
			}
		}
		if(operandType=="blt"){
			if(OF_EX_Latch.get_rd() < OF_EX_Latch.get_rs1()){
				EX_IF_Latch.isBranchtaken = true;
				int current_pc=OF_EX_Latch.get_pc();
				imm = OF_EX_Latch.get_imm();
				containingProcessor.getRegisterFile().setProgramCounter(current_pc+imm);					
			}
		}
		if(operandType == "end"){
			;//don't do anything
		}
		if(operandType == "store"){
				EX_MA_Latch.rd = OF_EX_Latch.get_rs1();
			}
			else
			{	
				EX_MA_Latch.rd = OF_EX_Latch.get_rd();
			}
			
			if (OF_EX_Latch.opcode >= 24 && OF_EX_Latch.opcode <= 28){
				EX_IF_Latch.setIF_enable(true);
				EX_MA_Latch.setMA_enable(true);
			}
			else
			{
				EX_MA_Latch.setMA_enable(true);
			}
			EX_MA_Latch.result = result;
			OF_EX_Latch.setEX_enable(false);
		}


		// opcode
	}


