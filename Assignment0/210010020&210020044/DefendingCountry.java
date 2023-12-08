import java.util.*;

public class DefendingCountry {
    int width;
    int height;
    int start_length;
    ArrayList<ArrayList<Integer>> border = new ArrayList<ArrayList<Integer>>();
    double p;    
    Random random = new Random();

    DefendingCountry(int width, int height,double p){
        this.height=height;
        this.width=width;
        this.p=p;
        this.random = new Random();

    }

    public ArrayList<ArrayList<Integer>> sensor_start(){
        for (int i=0;i<this.width;i++){
            int sensor_state;
            ArrayList<Integer>row = new ArrayList<Integer>();

            for(int j=0;j<this.height;j++){
                sensor_state = random.nextDouble() < p ? 1:0  ;
                row.add(sensor_state);
            }
            border.add(row);
        }
        System.out.println("\nDefending Country");
        for (int i=0;i<this.width;i++){
            System.out.println("");
            for(int j=0;j<this.height;j++){
                System.out.print(this.border.get(i).get(j)+" ");
            }
        }
        return border;
  }
}

