package processor.memorysystem;

public class CacheLine {
    int tag;
    int data;
    int timestamp;
    int valid;
    int modified;
    public CacheLine()
    {
        this.tag=-1;
        this.data=-1;
        this.timestamp=0;
        this.modified=0;
        this.valid=0;

    }

}



