import java.util.*;
public class Main {
    public static void main(String[] args){
        int width=3;
        int height=50;
        double p=0;

        AttackingCountry attacker = new AttackingCountry(width, height);
        DefendingCountry defender = new DefendingCountry(width, height, p);

        ArrayList<Integer> next_step = new ArrayList<>();        
        ArrayList<ArrayList<Integer>> defense;

        int[] start_position = {0, attacker.start_length};
        next_step.add(start_position[0]);        
        next_step.add(start_position[1]);

        
        defense=defender.sensor_start();
        

        if (defense.get(next_step.get(0)).get(next_step.get(1)) == 1) {
                System.out.println("\nInfiltrator Caught");
            }
        else if (next_step.get(0)==width-1){
            System.out.println("\n Infiltrator reached Opposite country");
        
        }
        else{
            int index = 0;

            while (true) {
                System.out.println("width: " + width);
                int[] next_move = {next_step.get(0), next_step.get(1)};

                next_step = attacker.infiltratorMove(next_move);
                defense = defender.sensor_start();

                // System.out.println("\nNext Step: " + next_step);

                if (defense.get(next_step.get(0)).get(next_step.get(1)) == 1) {
                    System.out.println("\nInfiltrator Caught");
                    break;
                }
                if (next_step.get(0)==width-1){
                    System.out.println("\n Infiltrator reached Opposite country");
                    break;
                }

                index++;      
                System.out.println("********************************************************************");
            }

        }





    }
       
}
