package processor.pipeline;

public class EX_IF_LatchType {
	
	boolean IF_Enable;
	boolean isBranchtaken;
	int pcBranch;
	
	public EX_IF_LatchType()
	{
		IF_Enable = false;
	}

	public boolean isIF_Enable()
	{
		return IF_Enable;
	}

	public void setIF_enable(boolean iF_enable)
	{
		IF_Enable = iF_enable;
	}

	public boolean getIsBranchTaken(boolean isBranchTaken) 
	{
		return this.isBranchtaken;
	}

	public int getBranchPC() {
		return pcBranch;
	}

}
