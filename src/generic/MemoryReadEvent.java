package generic;

import processor.memorysystem.MainMemory;

public class MemoryReadEvent extends Event {

	int addressToReadFrom;
	
	public MemoryReadEvent(long eventTime, Element requestingElement, Element mainMemory, int address) {
		super(eventTime, EventType.MemoryRead, requestingElement, mainMemory);
		this.addressToReadFrom = address;
	}

	public int getAddressToReadFrom() {
		return addressToReadFrom;
	}

	public void setAddressToReadFrom(int addressToReadFrom) {
		this.addressToReadFrom = addressToReadFrom;
	}
}
