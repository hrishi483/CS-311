package processor.pipeline;

import configuration.Configuration;
import processor.Clock;
import processor.Processor;

import java.util.Arrays;

import generic.*;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;

public class Execute implements Element {
	Configuration configuration;
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_OF_LatchType IF_OF_Latch;
	IF_EnableLatchType IF_EnableLatch;

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch,
			EX_IF_LatchType eX_IF_Latch, IF_OF_LatchType iF_OF_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

	public void performEX() {
		int temp_latency=0;
		if (OF_EX_Latch.getIsNOP())
		{
			//System.out.println("Entered EX Stage, detected NOP at OF_EX");
			EX_MA_Latch.setIsNOP(true);
			OF_EX_Latch.setIsNOP(false);
			EX_MA_Latch.setInstruction(null);
		}

		else if (OF_EX_Latch.isEX_enable()) {
			//System.out.println("EX Stage Entered Normally");
			EX_MA_Latch.setstatus(true);
			Instruction instruction = OF_EX_Latch.getInstruction();
			EX_MA_Latch.setInstruction(instruction);
			EX_MA_Latch.setOperand1(OF_EX_Latch.getOperand1());
			EX_MA_Latch.setOperand2(OF_EX_Latch.getOperand2());
			EX_MA_Latch.setImmediate(OF_EX_Latch.getImmediate());
			EX_MA_Latch.setDestinationOperand(OF_EX_Latch.getDestinationOperand());
			OperationType operation = instruction.getOperationType();
			int opcode = Arrays.asList(OperationType.values()).indexOf(operation);
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter() - 1;

			long alu = 0; int alu_pass=0;
			String long_alu;
			temp_latency=configuration.ALU_latency;
			//System.out.println("The operation to be executed is " + OperationType.values()[opcode]);
			if(opcode % 2 == 0 && opcode < 21) {

				int operand1 = OF_EX_Latch.getOperand1();
				int operand2 = OF_EX_Latch.getOperand2();
				//System.out.println("operand 1 for alu calc " + operand1);
				//System.out.println("operand 2 for calc " + operand2);
				switch(operation) {
					case add:
						alu = (operand1 + operand2);
						break;
					case mul:
						alu = (operand1 * operand2);
						temp_latency=Configuration.multiplier_latency;
						break;
					case sub:
						alu = (operand1 - operand2);
						break;
					case load:
						break;
					case and:
						alu = (operand1 & operand2);
						break;
					case div:
						alu = (operand1 / operand2);
						temp_latency=Configuration.divider_latency;
						int remainder = (operand1 % operand2);
						containingProcessor.getRegisterFile().setValue(31, remainder);
						break;
					case xor:
						alu = (operand1 ^ operand2);
						break;
					case or:
						alu = (operand1 | operand2);
						break;
					case store:
						break;						
					case slt:
						if(operand1 < operand2)
							alu = 1;
						else
							alu = 0;
						break;
					case srli:
						break;
					case srl:
						alu = (operand1 >>> operand2);
						String temp = Integer.toBinaryString(operand1);
						int length_temp= temp.length();
						temp=temp.substring(length_temp - operand2,length_temp);
						int store_value = Integer.parseInt(temp);
						containingProcessor.getRegisterFile().setValue(31, store_value);
						break;
					case sll:
						alu = (operand1 << operand2);
						temp = Integer.toBinaryString(operand1);
						temp=temp.substring(0,operand2);
						store_value = Integer.parseInt(temp);
						containingProcessor.getRegisterFile().setValue(31, store_value);
						break;
					case sra:
						alu = (operand1 >> operand2);
						temp = Integer.toBinaryString(operand1);
						length_temp= temp.length();
						temp=temp.substring(length_temp - operand2,length_temp);
						store_value = Integer.parseInt(temp);
						containingProcessor.getRegisterFile().setValue(31, store_value);

						break;
					case end:
						break;
					default:
						break;
				}
				if((alu>Integer.MAX_VALUE) || (alu<Integer.MIN_VALUE))
				{
					String temp_alu_use = Long.toBinaryString(alu);
					int temp_length_use=0;
					if (temp_alu_use.charAt(0)==0)
					{
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "0" + temp_alu_use;
						}

					}

					else {
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "1" + temp_alu_use;
						}

					}
					String temp_alu_x31_value= temp_alu_use.substring(0,32);
					String temp_alu_pass_value = temp_alu_use.substring(32,64);
					alu_pass = Integer.parseInt(temp_alu_pass_value);
					EX_MA_Latch.setalu(alu_pass);


				}
				else
				{
					EX_MA_Latch.setalu((int)alu);
				}


			}
			else if(opcode < 23) {
				int operand1 = OF_EX_Latch.getOperand1();
				int operand2 = OF_EX_Latch.getImmediate();
				//System.out.println("operand 1 for alu calc " + operand1);
				//System.out.println("operand 2 i:e immediate for calc " + operand2);


				switch(operation) {

					case addi:
						alu = (operand1 + operand2);
						break;
					case muli:
						alu = (operand1 * operand2);
						temp_latency=Configuration.multiplier_latency;
						break;
					case beq:
						break;
					case subi:
						alu = (operand1 - operand2);
						break;
					case andi:
						alu = (operand1 & operand2);
						break;
					case end:
						break;
					case xori:
						alu = (operand1 ^ operand2);
						break;
					case ori:
						alu = (operand1 | operand2);
						break;
					case divi:
						alu = (operand1 / operand2);
						temp_latency=Configuration.divider_latency;

						int remainder = (operand1 % operand2);
						containingProcessor.getRegisterFile().setValue(31, remainder);
						break;
					case jmp:
						break;
					case srli:
						alu = (operand1 >>> operand2);
						String temp = Integer.toBinaryString(operand1);
						int length_temp= temp.length();
						temp=temp.substring(length_temp - operand2,length_temp);
						int store_value = Integer.parseInt(temp);
						containingProcessor.getRegisterFile().setValue(31, store_value);
						break;
					case slti:
						if(operand1 < operand2)
							alu = 1;
						else
							alu = 0;
						break;
					case slli:
						alu = (operand1 << operand2);
						temp = Integer.toBinaryString(operand1);
						temp=temp.substring(0,operand2);
						store_value = Integer.parseInt(temp);
						containingProcessor.getRegisterFile().setValue(31, store_value);
						break;
					case load:
						alu = (operand1 + operand2);
						break;
					case srai:
						alu = (operand1 >> operand2);
						break;
					default:
						break;
				}
				if((alu>Integer.MAX_VALUE) || (alu<Integer.MIN_VALUE))
				{
					String temp_alu_use = Long.toBinaryString(alu);
					int temp_length_use=0;
					if (temp_alu_use.charAt(0)==0)
					{
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "0" + temp_alu_use;
						}

					}

					else {
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "1" + temp_alu_use;
						}

					}
					String temp_alu_x31_value= temp_alu_use.substring(0,32);
					String temp_alu_pass_value = temp_alu_use.substring(32,64);
					alu_pass = Integer.parseInt(temp_alu_pass_value);
					EX_MA_Latch.setalu(alu_pass);


				}
				else
				{
					EX_MA_Latch.setalu((int)alu);
				}

			}
			else if(opcode == 23) {

				int operand1 = OF_EX_Latch.getDestinationOperand();
				int operand2 = OF_EX_Latch.getImmediate();
				alu = operand1 + operand2;
				if((alu>Integer.MAX_VALUE) || (alu<Integer.MIN_VALUE))
				{
					String temp_alu_use = Long.toBinaryString(alu);
					int temp_length_use=0;
					if (temp_alu_use.charAt(0)==0)
					{
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "0" + temp_alu_use;
						}

					}

					else {
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "1" + temp_alu_use;
						}

					}
					String temp_alu_x31_value= temp_alu_use.substring(0,32);
					String temp_alu_pass_value = temp_alu_use.substring(32,64);
					alu_pass = Integer.parseInt(temp_alu_pass_value);
					EX_MA_Latch.setalu(alu_pass);


				}
				else
				{
					EX_MA_Latch.setalu((int)alu);
				}
			}

			else if(opcode == 24) {

				OperandType optype = instruction.getDestinationOperand().getOperandType();
				int immediate = 0;
				if (optype == OperandType.Register) 
					immediate = OF_EX_Latch.getDestinationOperand();
				else 
					immediate = instruction.getDestinationOperand().getValue();

				alu = immediate + currentPC;
				EX_IF_Latch.setIS_status(true, (int)alu-1);
				//IF_EnableLatch.setIF_enable(false);
				IF_OF_Latch.setIsNOP(true);
				Statistics.setNumberOfBranchTaken(Statistics.getNumberOfBranchTaken() + 1);
				//OF_EX_Latch.setEX_enable(false);
				if((alu>Integer.MAX_VALUE) || (alu<Integer.MIN_VALUE))
				{
					String temp_alu_use = Long.toBinaryString(alu);
					int temp_length_use=0;
					if (temp_alu_use.charAt(0)==0)
					{
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "0" + temp_alu_use;
						}

					}

					else {
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "1" + temp_alu_use;
						}

					}
					String temp_alu_x31_value= temp_alu_use.substring(0,32);
					String temp_alu_pass_value = temp_alu_use.substring(32,64);
					alu_pass = Integer.parseInt(temp_alu_pass_value);
					EX_MA_Latch.setalu(alu_pass);


				}
				else
				{
					EX_MA_Latch.setalu((int)alu);

				}

			}

			else if(opcode < 29) {

				int immediate = instruction.getDestinationOperand().getValue();
				int operand1 = OF_EX_Latch.getOperand1();
				int operand2 = OF_EX_Latch.getOperand2();
				switch(operation) {

					case beq:
						if(operand1 == operand2) {

							alu = immediate + currentPC;
							EX_IF_Latch.setIS_status(true, (int)alu-1);
							IF_OF_Latch.setIsNOP(true);
							//IF_EnableLatch.setIF_enable(false);
							//IF_OF_Latch.setOF_enable(false);
							Statistics.setNumberOfBranchTaken(Statistics.getNumberOfBranchTaken() + 1);
							//OF_EX_Latch.setEX_enable(false);
						}
						break;
					case bne:
						if(operand1 != operand2) {

							alu = immediate + currentPC;
							EX_IF_Latch.setIS_status(true, (int)alu-1);
							IF_OF_Latch.setIsNOP(true);
							//IF_EnableLatch.setIF_enable(false);
							//IF_OF_Latch.setOF_enable(false);
							Statistics.setNumberOfBranchTaken(Statistics.getNumberOfBranchTaken() + 1);
							//OF_EX_Latch.setEX_enable(false);
							
						}

						break;
					case blt:
						if(operand1 < operand2) {

							alu = immediate + currentPC;
							EX_IF_Latch.setIS_status(true, (int)alu-1);
							IF_OF_Latch.setIsNOP(true);
							//IF_EnableLatch.setIF_enable(false);
							//IF_OF_Latch.setOF_enable(false);
							Statistics.setNumberOfBranchTaken(Statistics.getNumberOfBranchTaken() + 1);
							//OF_EX_Latch.setEX_enable(false);

						}
						break;
					case bgt:
						if(operand1 > operand2) {

							alu = immediate + currentPC;
							EX_IF_Latch.setIS_status(true, (int)alu-1);
							IF_OF_Latch.setIsNOP(true);
							//IF_EnableLatch.setIF_enable(false);
							//IF_OF_Latch.setOF_enable(false);
							Statistics.setNumberOfBranchTaken(Statistics.getNumberOfBranchTaken() + 1);
							//OF_EX_Latch.setEX_enable(false);
						}
						break;
					default:
						break;
				}
				if((alu>Integer.MAX_VALUE) || (alu<Integer.MIN_VALUE))
				{
					String temp_alu_use = Long.toBinaryString(alu);
					int temp_length_use=0;
					if (temp_alu_use.charAt(0)==0)
					{
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "0" + temp_alu_use;
						}

					}

					else {
						temp_length_use = temp_alu_use.length();
						for( int i=0; i<32-temp_length_use;i++)
						{
							temp_alu_use = "1" + temp_alu_use;
						}

					}
					String temp_alu_x31_value= temp_alu_use.substring(0,32);
					String temp_alu_pass_value = temp_alu_use.substring(32,64);
					alu_pass = Integer.parseInt(temp_alu_pass_value);
					EX_MA_Latch.setalu(alu_pass);


				}
				else
				{
					EX_MA_Latch.setalu((int)alu);
				}



			

			


		}
			//System.out.println("Alu value calculated is " + (int)alu);
			if(containingProcessor.getMax_latency()<temp_latency)
			{
				containingProcessor.setMax_latency(temp_latency);
			}
			Simulator.getEventQueue().addEvent(
					new AluComputeEvent(
							Clock.getCurrentTime() + temp_latency,
							this,
							this

					)
			);
	}

}
@Override
	public void  handleEvent(Event e)
{

}
}
