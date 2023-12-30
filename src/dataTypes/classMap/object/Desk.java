package dataTypes.classMap.object;

import dataTypes.actors.Student;

public class Desk {
    private double x;
    private double y;
    private String type;
    private DeskOrientation orientation;
    private final double width = 1;
    private final double height = 0.60;
    private Student student;
    public Desk(double x, double y, String type, DeskOrientation orientation){
        this.type = type;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getWidth(){
        if (orientation == DeskOrientation.horizontal ){
            if (this.type.equals("mono")) return this.width;
            else return this.width*2;
        }
        else{
            if (this.type.equals("mono")) return this.height;
            else return this.height*2;
        }
    }
    public double getHeight(){
        if (orientation == DeskOrientation.horizontal ){
            if (this.type.equals("mono")) return this.height;
            else return this.height*2;
        }
        else{
            if (this.type.equals("mono")) return this.width;
            else return this.width*2;
        }
    }

    public String getType(){
        return this.type;
    }
    public void setStudent(Student student){
        this.student = student;
    }
    public Student getStudent(){
        return this.student;
    }
    public DeskOrientation getOrientation(){
        return this.orientation;
    }
}
