package processor.memorysystem;
// import generic.*;
import processor.*;
// import configuration.Configuration;
// import javax.swing.text.Element;

public class Cache {
    public int latency;
    Processor processor; //containingprocessor
    int cache_size;
    boolean isPresent;
    CacheLine[] cacheLines;
    int block_size;

    public Cache(Processor processor, int latency, int cache_size,int block_size){
        this.latency=latency;
        this.cache_size=cache_size;
        this.processor=processor;
        this.cacheLines=new CacheLine[cache_size];

        for(int i=0;i<cache_size;i++){
            this.cacheLines[i]=new CacheLine();
        }
        this.block_size=block_size;
    }
    public int getSize(){
        return cache_size;
    }

    public int calculateSetNumber(int value) {
        int setNumber = value;
        setNumber = setNumber - (setNumber % 8);
        setNumber = setNumber / 8;
        setNumber = setNumber % (cache_size / 2);  //cache size is the global var in this file
        return setNumber;
    }
    


    public void addtoCache(int value){
        int setNumber = calculateSetNumber(value);
        int done=0;

        for (int i = setNumber*2; i <= (setNumber*2+1); i++) {
            cacheLines[i].timestamp += 1;
            if((cacheLines[i].valid==0) && (done==0)){
                cacheLines[i].tag = value-(value%block_size);
                cacheLines[i].timestamp = 0;
                cacheLines[i].valid = 1;
                done+=1;
            }
        }
        if (done==0) {
            int max_timestamp=0;
            int max_index=0;
            for (int i = setNumber*2; i <= (setNumber*2+1); i++) {
                if (max_timestamp<cacheLines[i].timestamp) {
                    max_timestamp=cacheLines[i].timestamp;
                    max_index=i;
                }
            }
            cacheLines[max_index].tag=value-(value%block_size);
            cacheLines[max_index].timestamp=0;
            cacheLines[max_index].valid=1;

        }
    }

    public boolean checkinCache(int value) // Cache Lookup function
    {
        int set_number = calculateSetNumber(value);
        for (int i = 0; i < cache_size; i++)
        {
            if(cacheLines[i].valid==1)
            {
                if(cacheLines[i].tag==value-(value%block_size))
                {   
                    // set_number=set_number%8;  ***
                    return true;
                }
            }           
        }
        return false;
    }

}
