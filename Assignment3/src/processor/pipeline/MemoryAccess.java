package processor.pipeline;

import processor.Processor;
import processor.memorysystem.MainMemory;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		//TODO
		if(EX_MA_Latch.isMA_enable()){
			
			int Ma_result = 0;
			MainMemory mainMemory  = containingProcessor.getMainMemory();		
			int opcode = EX_MA_Latch.get_Opcode();
			MA_RW_Latch.opcode = opcode;

			if (opcode == 22){ //load then load from the main mmory address=result
				int result = EX_MA_Latch.get_Result();
				Ma_result = mainMemory.getWord(result);
			}
			else if (opcode == 23){ //store then store into the main memory at address=result
				mainMemory.setWord(EX_MA_Latch.get_Result(),EX_MA_Latch.get_rd());
			}
			MA_RW_Latch.rd = EX_MA_Latch.get_rd();
			MA_RW_Latch.result = Ma_result;
			MA_RW_Latch.excess = EX_MA_Latch.get_Excess();
			//Switch off the EX_MA_Latch
			EX_MA_Latch.setMA_enable(false);
			//Switch ON the MA_RW Latch
			MA_RW_Latch.setRW_enable(true);
		}
	}

}
