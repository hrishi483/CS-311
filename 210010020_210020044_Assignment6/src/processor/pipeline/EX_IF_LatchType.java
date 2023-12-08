package processor.pipeline;

public class EX_IF_LatchType {

	boolean IS_status;
	int program_counter;
	
	public EX_IF_LatchType() {
		this.IS_status = false;
	}

	public EX_IF_LatchType(boolean status) {
		this.IS_status = status;
	}

	public EX_IF_LatchType(boolean status, int program_counter) {
		this.IS_status = status;
		this.program_counter = program_counter;
	}

	public boolean getIS_status() {
		return IS_status;
	}

	public void setIS_status(boolean IS_status, int newprogram_counter) {
		this.IS_status = IS_status;
		program_counter = newprogram_counter; 
	}

	public void setIS_status(boolean IS_status) {
		this.IS_status = IS_status;
	}

	public void setprogram_counter(int newprogram_counter) {
		this.program_counter = newprogram_counter;
	}

	public int getprogram_counter() {
		return program_counter;
	}

}
