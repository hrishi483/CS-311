// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;
class Shape{
    String color;
}

// # class Child class parent classs
class Triangle extends Shape{
    public void setColor(String color){
        System.out.println("Set Color = "+color);
    }
}

class Pen{
    String color;
    String type; //ballpoint pen

    public void write(String s){
        System.out.println(this.color);
        System.out.println(this.type);
        System.out.println("Writing "+s);    
    }
}

class Student{
    String name;
    int age;

    //Constructor 
    Student(String name, int age){
        this.name = name;
        this.age = age;
        System.out.println("Constructor Called");
    }

    public  void Info(){
        System.out.println("Name: "+this.name);
        System.out.println("Age: "+this.age);
    }

}

public class OOPs{
    public static void main(String args[]){
        Pen p = new Pen();   
        p.color="Blue";
        p.type="Gel";
        // p.write("Hello");
        
        Pen p2 = new Pen();
        p2.color="Blue";
        p2.type="Ball Point";
        // p2.write(" Hellow ");

        Student s1 = new Student("Hrishikesh",20);
        s1.Info();      

        Triangle t1 = new Triangle();
        t1.color="Blue";


    }
}
