package dataTypes.classMap.object;

import dataTypes.actors.Student;

import java.util.Objects;

public class Desk {
    private double x;
    private double y;
    private String type;
    private Room room;
    private final double width = 1;
    private final double height = 0.60;
    private Student student;
    public Desk(double x, double y, String type, Room room){
        this.type = type;
        this.x = x;
        this.y = y;
        this.room = room;
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
        if (this.room.getBoardOrientation() == boardOrientation.north || this.room.getBoardOrientation() == boardOrientation.south){
            if (this.type.equals("mono")) return this.width;
            else return this.width*2;
        }
        else{
            if (this.type.equals("mono")) return this.height;
            else return this.height*2;
        }
    }
    public double getHeight(){
        if (this.room.getBoardOrientation() == boardOrientation.north || this.room.getBoardOrientation() == boardOrientation.south){
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
    public Room getRoom(){
        return this.room;
    }
    public void setStudent(Student student){
        this.student = student;
    }
    public Student getStudent(){
        return this.student;
    }
}
