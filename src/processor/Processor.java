package processor;
// To use the datatype cache import the class
import processor.memorysystem.Cache;
import processor.pipeline.InstructionFetch;
import processor.pipeline.MA_RW_LatchType;
import processor.pipeline.MemoryAccess;
import processor.pipeline.OF_EX_LatchType;
import processor.memorysystem.MainMemory;
import processor.pipeline.IF_EnableLatchType;
import processor.pipeline.IF_OF_LatchType;
import processor.pipeline.OperandFetch;
import processor.pipeline.RegisterFile;
import processor.pipeline.RegisterWrite;
import processor.pipeline.EX_IF_LatchType;
import processor.pipeline.EX_MA_LatchType;
import processor.pipeline.Execute;

public class Processor {
	
	MainMemory mainMemory;
	RegisterFile registerFile;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	IF_EnableLatchType IF_EnableLatch;
	MA_RW_LatchType MA_RW_Latch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	OperandFetch OFUnit;
	Execute EXUnit;
	RegisterWrite RWUnit;
	InstructionFetch IFUnit;
	MemoryAccess MAUnit;
	int max_latency;
	
// Create cache fields cache_i and cache_d
	Cache cache_i;
	Cache cache_d;


	public Processor()
	{
		registerFile = new RegisterFile();
		mainMemory = new MainMemory();

		OF_EX_Latch = new OF_EX_LatchType();
		EX_MA_Latch = new EX_MA_LatchType();
		EX_IF_Latch = new EX_IF_LatchType();
		MA_RW_Latch = new MA_RW_LatchType();
		IF_EnableLatch = new IF_EnableLatchType();
		IF_OF_Latch = new IF_OF_LatchType();
		
		IFUnit = new InstructionFetch(this, IF_EnableLatch, IF_OF_Latch, EX_IF_Latch);
		OFUnit = new OperandFetch(this, IF_OF_Latch, OF_EX_Latch, EX_MA_Latch, MA_RW_Latch, IF_EnableLatch);
		EXUnit = new Execute(this, OF_EX_Latch, EX_MA_Latch, EX_IF_Latch, IF_OF_Latch, IF_EnableLatch);
		MAUnit = new MemoryAccess(this, EX_MA_Latch, MA_RW_Latch, IF_EnableLatch);
		RWUnit = new RegisterWrite(this, MA_RW_Latch, IF_EnableLatch);
		
		//Set the cache Parametres:
		cache_i=new Cache(this,8,256,4);
		cache_d=new Cache(this,1,256,4);
	}
	
	public void printState(int memoryStartingAddress, int memoryEndingAddress)
	{
		System.out.println(registerFile.getContentsAsString());
		
		System.out.println(mainMemory.getContentsAsString(memoryStartingAddress, memoryEndingAddress));		
	}

	public RegisterFile getRegisterFile() {
		return registerFile;
	}

	public void setRegisterFile(RegisterFile registerFile) {
		this.registerFile = registerFile;
	}

	public MemoryAccess getMAUnit() {
		return MAUnit;
	}

	public RegisterWrite getRWUnit() {
		return RWUnit;
	}

	public MainMemory getMainMemory() {
		return mainMemory;
	}

	public void setMainMemory(MainMemory mainMemory) {
		this.mainMemory = mainMemory;
	}

	public Execute getEXUnit() {
		return EXUnit;
	}

	public InstructionFetch getIFUnit() {
		return IFUnit;
	}

	public OperandFetch getOFUnit() {
		return OFUnit;
	}

    public void setMax_latency(int i) {
			this.max_latency = i;
    }

	public int getMax_latency() {
		return max_latency;
	}
	public Cache getCache_i(){
		return cache_i;
	}
	public Cache getCache_d(){
		return cache_d;
	}
}
