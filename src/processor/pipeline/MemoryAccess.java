package processor.pipeline;

import processor.Clock;
import configuration.Configuration;
import generic.*;
import processor.Processor;
import generic.Instruction.OperationType;

import java.util.HashMap;
import java.util.Map;

public class MemoryAccess implements Element {
    EX_MA_LatchType EX_MA_Latch;
    MA_RW_LatchType MA_RW_Latch;
    Processor containingProcessor;
    IF_EnableLatchType IF_EnableLatch;

    public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
        this.EX_MA_Latch = eX_MA_Latch;
        this.MA_RW_Latch = mA_RW_Latch;
        this.containingProcessor = containingProcessor;
        this.IF_EnableLatch = iF_EnableLatch;
    }

    public void performMA() {
        if (EX_MA_Latch.getIsNOP()) {
            MA_RW_Latch.setIsNOP(true);
            MA_RW_Latch.setInstruction(null);
            EX_MA_Latch.setIsNOP(false);
        } else if (EX_MA_Latch.isMA_enable()) {
            Instruction inst = EX_MA_Latch.getInstruction();
            int alu_result = EX_MA_Latch.getALU_result();
            // System.out.println("MA is enabled: " + inst);
            MA_RW_Latch.setALU_result(alu_result);
            OperationType operand_Type = inst.getOperationType();

            // Create a map to associate each operation type with a lambda expression
            Map<OperationType, Runnable> operationHandlers = new HashMap<>();
            operationHandlers.put(OperationType.store, () -> {
                int val_store = containingProcessor.getRegisterFile()
                        .getValue(inst.getSourceOperand1().getValue());
                containingProcessor.getMainMemory().setWord(alu_result, val_store);

                // Add MemoryWriteEvent
                Simulator.getEventQueue().addEvent(
                        new MemoryWriteEvent(
                                Clock.getCurrentTime() + Configuration.mainMemoryLatency,
                                this,
                                containingProcessor.getMainMemory(),
                                alu_result,
                                val_store)
                );
                if(containingProcessor.getCache_d().checkinCache(alu_result)==false)
                {
                    containingProcessor.getCache_d().addtoCache(alu_result);
                }
                
                if (containingProcessor.getMax_latency() < Configuration.mainMemoryLatency) {
                    containingProcessor.setMax_latency(Configuration.mainMemoryLatency);
                }
            });

            operationHandlers.put(OperationType.load, () -> {
                int load_result = containingProcessor.getMainMemory().getWord(alu_result);
                MA_RW_Latch.setLoad_result(load_result);

                // Add MemoryReadEvent
                if(containingProcessor.getCache_d().checkinCache(alu_result)==true)
                {        
                    Simulator.getEventQueue().addEvent(
                            new MemoryReadEvent(
                                    Clock.getCurrentTime() + containingProcessor.getCache_d().latency,
                                    this,
                                    containingProcessor.getMainMemory(),
                                    alu_result)
                    );
                    // System.out.println("Found at Cache Value = "+ Integer.toString(alu_result));
                    Statistics.setNoOfMemoryAccesses(Statistics.getNoOfMemoryAccesses()+1);
                    Statistics.setNoOfMemoryHits(Statistics.getNoOfMemoryHits()+1);
                }
                else{
                     Simulator.getEventQueue().addEvent(
                            new MemoryReadEvent(
                                    Clock.getCurrentTime() + Configuration.mainMemoryLatency,
                                    this,
                                    containingProcessor.getMainMemory(),
                                    alu_result)
                    );
                    containingProcessor.getCache_d().addtoCache(alu_result);
                    // System.out.println("Did not find at Cache Value = "+ Integer.toString(alu_result));
					Statistics.setNoOfMemoryAccesses(Statistics.getNoOfMemoryAccesses()+1);
                }
                // System.out.println(" From " + alu_result + " value retrieved is " + load_result);

                if (containingProcessor.getMax_latency() < Configuration.mainMemoryLatency) {
                    containingProcessor.setMax_latency(Configuration.mainMemoryLatency);
                }
            });

            // Check if the operation type is in the map and execute the corresponding lambda
            if (operationHandlers.containsKey(operand_Type)) {
                operationHandlers.get(operand_Type).run();
            }

            if (operand_Type.ordinal() == 29) {
                IF_EnableLatch.setIF_enable(false);
            }

            MA_RW_Latch.setInstruction(inst);
            MA_RW_Latch.setRW_enable(true);
        }
    }

    @Override
    public void handleEvent(Event e) {
        // Handle any events here
    }
}
