import java.util.*;

public class Infiltrator{
    int x;
    int y;

    // init 
    int height;
    int width;
    Infiltrator(int width,int height){    
        System.out.println("");
        this.width = width;
        this.height = height;
    }

    public ArrayList<ArrayList<Integer>> conditions(int position_x,int position_y){
        ArrayList<ArrayList<Integer>> next_step = new ArrayList<ArrayList<Integer>>();
        if (position_x==0 && position_y==0){
            ArrayList<Integer> step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y);
            next_step.add(new ArrayList<>(step_coordinates));
            
           step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(0);
            step_coordinates.add(1);
            next_step.add(new ArrayList<>(step_coordinates)); // Create a new ArrayList
        
            step_coordinates = new ArrayList<Integer>(); // Create a new ArrayList
            step_coordinates.add(1);
            step_coordinates.add(0);
            next_step.add(new ArrayList<>(step_coordinates)); // Create a new ArrayList
        
            step_coordinates = new ArrayList<Integer>(); // Create a new ArrayList
            step_coordinates.add(1);
            step_coordinates.add(1);
            next_step.add(new ArrayList<>(step_coordinates)); // Create a new ArrayList
        

        }
       
        else if((position_x==width-1) && (position_y==0)){
            ArrayList<Integer> step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y);
            next_step.add(new ArrayList<>(step_coordinates));
            
            step_coordinates = new ArrayList<Integer>();            
            step_coordinates.add(width-2);
            step_coordinates.add(0);
            next_step.add(step_coordinates);

            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(width-1);
            step_coordinates.add(1);
            next_step.add(step_coordinates);

            step_coordinates = new ArrayList<Integer>();          
            step_coordinates.clear();
            step_coordinates.add(width-2);
            step_coordinates.add(1);
            next_step.add(step_coordinates);
        }
       
        else if((position_x == 0) && (position_y==height-1)){
            ArrayList<Integer> step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y);
            next_step.add(new ArrayList<>(step_coordinates));
            
           step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y);
            next_step.add(new ArrayList<>(step_coordinates));
            
           step_coordinates = new ArrayList<Integer>();            
            step_coordinates.add(width-1);
            step_coordinates.add(height-2);
            next_step.add(step_coordinates);

            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(width-2);
            step_coordinates.add(height-1);
            next_step.add(step_coordinates);

            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(width-2);
            step_coordinates.add(height-2);
            next_step.add(step_coordinates);

        }
       
        else if((position_x==0) && (0<position_y && position_y<height-1)){           
            ArrayList<Integer> step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y);
            next_step.add(new ArrayList<>(step_coordinates));
            
           step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(0);
            step_coordinates.add(position_y-1);
            next_step.add(step_coordinates);

            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(0);
            step_coordinates.add(position_y+1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(1);
            step_coordinates.add(position_y);
            next_step.add(step_coordinates);

            step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(1);
            step_coordinates.add(position_y-1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(1);
            step_coordinates.add(position_y+1);
            next_step.add(step_coordinates);
        }
       
        else if((position_x==width-1) && (0<position_y && position_y<height-1)){
            ArrayList<Integer> step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y);
            next_step.add(new ArrayList<>(step_coordinates));
            
           step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(width-1);
            step_coordinates.add(position_y-1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(width-1);
            step_coordinates.add(position_y+1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(width-2);
            step_coordinates.add(position_y);
            next_step.add(step_coordinates);

            
            step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(width-2);
            step_coordinates.add(position_y-1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(width-2);
            step_coordinates.add(position_y+1);
            next_step.add(step_coordinates);
        }
        
        else if((position_y==0) && (0<position_x && position_x<width-1)){
            ArrayList<Integer> step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y);
            next_step.add(new ArrayList<>(step_coordinates));
            
           step_coordinates = new ArrayList<Integer>();
           step_coordinates.add(position_x-1);
            step_coordinates.add(0);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x+1);
            step_coordinates.add(0);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x-2);
            step_coordinates.add(1);
            next_step.add(step_coordinates);

            step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x+1);
            step_coordinates.add(1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x-1);
            step_coordinates.add(1);
            next_step.add(step_coordinates);
        }
            
        else if ((position_y==height-1) && (0<position_x && position_x<width-1)){
            ArrayList<Integer> step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y);
            next_step.add(new ArrayList<>(step_coordinates));
            
           step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x-1);
            step_coordinates.add(height-1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x+1);
            step_coordinates.add(height-1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x);
            step_coordinates.add(height-2);
            next_step.add(step_coordinates);

            
            step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x+1);
            step_coordinates.add(height-2);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x-1);
            step_coordinates.add(height-2);
            next_step.add(step_coordinates);
        }
        
        else{
            ArrayList<Integer> step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y);
            next_step.add(new ArrayList<>(step_coordinates));
            
           step_coordinates = new ArrayList<Integer>();
            step_coordinates.add(position_x-1);
            step_coordinates.add(position_y);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y-1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x+1);
            step_coordinates.add(position_y);
            next_step.add(step_coordinates);

            
            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x);
            step_coordinates.add(position_y+1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x-1);
            step_coordinates.add(position_y-1);
            next_step.add(step_coordinates);

            
            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x+1);
            step_coordinates.add(position_y+1);
            next_step.add(step_coordinates);


            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x-1);
            step_coordinates.add(position_y+1);
            next_step.add(step_coordinates);

            
            step_coordinates = new ArrayList<Integer>();
            step_coordinates.clear();
            step_coordinates.add(position_x+1);
            step_coordinates.add(position_y-1);
            next_step.add(step_coordinates);
        }
      
        return next_step;
    }

    // public static void main(String[] args){
       
    // }
}