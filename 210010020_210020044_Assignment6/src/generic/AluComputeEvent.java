package generic;

public class AluComputeEvent extends Event {
    public AluComputeEvent(long eventTime, Element requestingElement, Element processingElement) {
        super(eventTime, EventType.AluCompute, requestingElement, processingElement);
        //this.addressToReadFrom = address;
    }
}