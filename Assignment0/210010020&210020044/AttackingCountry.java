import java.util.ArrayList;
import java.util.Random;

public class AttackingCountry {
    int width;
    int height;
    int start_length;
    int [][] border;
    int [][] start_position;
    Infiltrator infiltrator_person;    
    Random random = new Random();

    AttackingCountry(int width, int height){
        this.width = width;
        this.height = height;
        this.border = new int[width][height];
        this.start_length = random.nextInt(this.height);
        System.out.println("Infiltrator Start: ["+0+","+this.start_length+"]");      

        // Make the borders with sensors
        for (int i=0;i<this.width;i++){
            for(int j=0;j<this.height;j++){
                 this.border[i][j] = 0;
        }

        this.border[0][start_length] = 9;
        for (i=0;i<this.width;i++){
            System.out.println(" ");
            for(int j=0;j<this.height;j++){
                 System.out.print(border[i][j]+" ");
            }
        }

        
    }

        this.infiltrator_person = new Infiltrator(width,height);
    }

    


    public ArrayList<Integer> infiltratorMove(int[] position){
        ArrayList<ArrayList<Integer>> list_of_steps = this.infiltrator_person.conditions(position[0], position[1]);
        int next_step = random.nextInt(list_of_steps.size());
        ArrayList<Integer> next_step_taken = list_of_steps.get(next_step);
        System.out.println("Steps Available: " + list_of_steps);
        System.out.println("Chosen Step: " + next_step_taken);

        this.border[next_step_taken.get(0)][next_step_taken.get(1)]=9;
        for (int i=0;i<this.width;i++){
            System.out.println("");
            for(int j=0;j<this.height;j++){
                 System.out.print(this.border[i][j]+" ");
            }
        }
        return next_step_taken;
    }
   
}
