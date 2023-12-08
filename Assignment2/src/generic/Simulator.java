package generic;

import java.io.*;
import java.nio.ByteBuffer;
import generic.Operand.OperandType;


public class Simulator {

	public static String getBinaryCode(String instruction) {
		switch (instruction) {
			case "add":
				return "00000";
			case "sub":
				return "00010";
			case "mul":
				return "00100";
			case "div":
				return "00110";
			case "and":
				return "01000";
			case "or":
				return "01010";
			case "xor":
				return "01100";
			case "slt":
				return "01110";
			case "sll":
				return "10000";
			case "srl":
				return "10010";
			case "sra":
				return "10100";
			case "addi":
				return "00001";
			case "subi":
				return "00011";
			case "muli":
				return "00101";
			case "divi":
				return "00111";
			case "andi":
				return "01001";
			case "ori":
				return "01011";
			case "xori":
				return "01101";
			case "slti":
				return "01111";
			case "slli":
				return "10001";
			case "srli":
				return "10011";
			case "srai":
				return "10101";
			case "load":
				return "10110";
			case "store":
				return "10111";
			case "beq":
				return "11001";
			case "bne":
				return "11010";
			case "blt":
				return "11011";
			case "bgt":
				return "11100";
			case "jmp":
				return "11000";
			default:
				return "Invalid Instruction";
		}
	}
	
		
	static FileInputStream inputcodeStream = null;
	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}

	public static void assemble(String objectProgramFile)
	{
		
		try{
			//1. open the objectProgramFile in binary mode
			FileOutputStream weno = new FileOutputStream(objectProgramFile);
			BufferedOutputStream mech = new BufferedOutputStream(weno);
			//2. write the firstCodeAddress to the file
			byte[] codeAddress = ByteBuffer.allocate(4).putInt(ParsedProgram.firstCodeAddress).array();
			mech.write(codeAddress);

			//3. write the data to the file
            int icounter=0;
            while(icounter<ParsedProgram.data.size())
            {
                byte[] lettuce = ByteBuffer.allocate(4).putInt(ParsedProgram.data.get(icounter)).array();
				mech.write(lettuce);
                icounter++;
            }
			
			//4. assemble one instruction at a time, and write to the file
            int jcounter =0 ;
            while(jcounter<ParsedProgram.code.size())
            {
                String bisaun = ""; // line to append to file
				String opType = ParsedProgram.code.get(jcounter).getOperationType().toString();
				// System.out.println(opType);
				if (opType.equals("jmp")) {
					// Opcode of jmp
					bisaun += getBinaryCode("jmp");
				
					// No register in jmp
					bisaun += "00000";
				
					int destAddr = 0;
					Operand destinationOperand = ParsedProgram.code.get(jcounter).destinationOperand;
					
					if (destinationOperand.operandType == OperandType.Label) {
						String labelValue = destinationOperand.labelValue;
						destAddr = ParsedProgram.symtab.get(labelValue);
					} else if (destinationOperand.operandType == OperandType.Immediate) {
						destAddr = destinationOperand.value;
					}
					
					int pc = ParsedProgram.code.get(jcounter).programCounter;
					int offset = destAddr - pc;
				
					if (offset >= 0) {
						String binRep = Integer.toBinaryString(offset);
						int binOffset = Math.max(0, 22 - binRep.length());
						String padding = "0".repeat(binOffset);
						bisaun += padding + binRep;
					} else {
						String binRep = Integer.toBinaryString(offset & 0x3FFFFF); // Mask the lower 22 bits
						bisaun += binRep;
					}
				}
				
				if (opType.equals("load") || opType.equals("store")) {
					bisaun += getBinaryCode(opType);
				
					Operand sourceOperand1 = ParsedProgram.code.get(jcounter).sourceOperand1;
					Operand destinationOperand = ParsedProgram.code.get(jcounter).destinationOperand;
					Operand sourceOperand2 = ParsedProgram.code.get(jcounter).sourceOperand2;
				
					if (sourceOperand1.operandType == OperandType.Register) {
						bisaun += String.format("%5s", Integer.toBinaryString(sourceOperand1.value)).replace(' ', '0');
					}
				
					if (destinationOperand.operandType == OperandType.Register) {
						bisaun += String.format("%5s", Integer.toBinaryString(destinationOperand.value)).replace(' ', '0');
					}
				
					if (sourceOperand2.operandType == OperandType.Label) {
						String labelBinary = String.format("%17s", Integer.toBinaryString(ParsedProgram.symtab.get(sourceOperand2.labelValue))).replace(' ', '0');
						bisaun += labelBinary;
					} else if (sourceOperand2.operandType == OperandType.Immediate) {
						String immediateBinary = Integer.toBinaryString(sourceOperand2.value);
						int paddingSize = Math.max(0, 17 - immediateBinary.length());
						String padding = "0".repeat(paddingSize);
						bisaun += padding + immediateBinary;
					}
				}
				
				if (opType.equals("add") || opType.equals("sub") || opType.equals("mul") || opType.equals("div") || 
    opType.equals("and") || opType.equals("or") || opType.equals("xor") || opType.equals("slt") || 
    opType.equals("sll") || opType.equals("srl") || opType.equals("sra")) 
	{
    
    bisaun += getBinaryCode(opType);

    Operand sourceOperand1 = ParsedProgram.code.get(jcounter).sourceOperand1;
    Operand sourceOperand2 = ParsedProgram.code.get(jcounter).sourceOperand2;
    Operand destinationOperand = ParsedProgram.code.get(jcounter).destinationOperand;

    if (sourceOperand1.operandType.toString().equals("Register")) {
        bisaun = bisaun.concat(String.format("%5s", Integer.toBinaryString(sourceOperand1.value)).replace(' ', '0'));
    }
    
    if (sourceOperand2.operandType.toString().equals("Register")) {
        bisaun = bisaun.concat(String.format("%5s", Integer.toBinaryString(sourceOperand2.value)).replace(' ', '0'));
    }

    if (destinationOperand.operandType.toString().equals("Register")) {
        bisaun = bisaun.concat(String.format("%5s", Integer.toBinaryString(destinationOperand.value)).replace(' ', '0'));
    }

    bisaun = bisaun.concat("000000000000");
}

if (opType.matches("addi|subi|muli|divi|andi|ori|xori|slti|slli|srli|srai")) {
    bisaun += getBinaryCode(opType);

    Operand sourceOperand1 = ParsedProgram.code.get(jcounter).sourceOperand1;
    Operand destinationOperand = ParsedProgram.code.get(jcounter).destinationOperand;
    Operand sourceOperand2 = ParsedProgram.code.get(jcounter).sourceOperand2;

    if (sourceOperand1.operandType.toString().equals("Register")) {
        bisaun = bisaun.concat(String.format("%5s", Integer.toBinaryString(sourceOperand1.value)).replace(' ', '0'));
    }
    
    if (destinationOperand.operandType.toString().equals("Register")) {
        bisaun = bisaun.concat(String.format("%5s", Integer.toBinaryString(destinationOperand.value)).replace(' ', '0'));
    }

    if (sourceOperand2.operandType.toString().equals("Immediate")) {
        String immediateBinary = Integer.toBinaryString(sourceOperand2.value);
        int paddingSize = Math.max(0, 17 - immediateBinary.length());
        String padding = "0".repeat(paddingSize);
        bisaun = bisaun.concat(padding + immediateBinary);
    }
}

if (opType.equals("beq") || opType.equals("bgt") || opType.equals("bne") || opType.equals("blt")) {
    bisaun += getBinaryCode(opType);

    Operand sourceOperand1 = ParsedProgram.code.get(jcounter).sourceOperand1;
    Operand sourceOperand2 = ParsedProgram.code.get(jcounter).sourceOperand2;
    Operand destinationOperand = ParsedProgram.code.get(jcounter).destinationOperand;

    if (sourceOperand1.operandType.toString().equals("Register")) {
        bisaun = bisaun.concat(String.format("%5s", Integer.toBinaryString(sourceOperand1.value)).replace(' ', '0'));
    }
    
    if (sourceOperand2.operandType.toString().equals("Register")) {
        bisaun = bisaun.concat(String.format("%5s", Integer.toBinaryString(sourceOperand2.value)).replace(' ', '0'));
    }

    int destAddr = 0;
    if (destinationOperand.operandType == OperandType.Label) {
        destAddr = ParsedProgram.symtab.get(destinationOperand.labelValue);
    } else if (destinationOperand.operandType == OperandType.Immediate) {
        destAddr = destinationOperand.value;
    }

    int pc = ParsedProgram.code.get(jcounter).programCounter;
    int offset = destAddr - pc;

    if (offset >= 0) {
        String binRep = Integer.toBinaryString(offset);
        int binOffset = Math.max(0, 17 - binRep.length());
        String padding = "0".repeat(binOffset);
        bisaun = bisaun.concat(padding + binRep);
    } else {
        String binRep = Integer.toBinaryString(offset);
        String subRep = binRep.substring(15, 32);
        bisaun = bisaun.concat(subRep);
    }
}

				else if(opType.equals("end")){
					bisaun = bisaun.concat("11101000000000000000000000000000");
				}
				System.out.println(bisaun);
				int b_int = (int)Long.parseLong(bisaun, 2);
				byte[] toThefile = ByteBuffer.allocate(4).putInt(b_int).array();
				mech.write(toThefile);
                jcounter++;
            }

			//5. close the file
			mech.close();

		} catch(FileNotFoundException e){
			e.printStackTrace();

		} catch(IOException e){
			e.printStackTrace();

		}
	}

	}