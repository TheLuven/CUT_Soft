package datatypes.classMap.object;
/*********************
 * @author Victor VENULETH
 * @date 05/01/2024
 ********************/

import datatypes.actors.Student;

import java.util.ArrayList;

/**
 * @brief This class is used to represent a desk in the classroom
 * @details A desk desciption is composed of its type (mono or duo) and its orientation (horizontal or vertical). Is position is defined by its x and y coordinates. It can also contain a student. The width and height of a desk depends on its type and orientation.
 * @author Victor VENULETH
 */
public class Desk {
    private double x;
    private double y;
    private String type;
    private DeskOrientation orientation;
    private final double width = 1;
    private final double height = 0.60;
    private Student student;

    /**
     * @brief Constructor of the class Desk without a student
     * @param x
     * @param y
     * @param type
     * @param orientation
     * @autor Victor VENULETH
     */
    public Desk(double x, double y, String type, DeskOrientation orientation){
        this.type = type;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }
    /**
     * @brief Constructor of the class Desk with a student
     * @param x
     * @param y
     * @param type
     * @param deskOrientation
     * @param studentByID
     * @autor Victor VENULETH
     */
    public Desk(double x, double y, String type, DeskOrientation deskOrientation, Student studentByID) {
        this(x,y,type,deskOrientation);
        this.student = studentByID;
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

    /**
     * @author Victor VENULETH
     * @brief This method returns the width and height of a desk depending on its type and orientation
     * @return double : the width of the desk
     */
    public double getWidth(){
        if (orientation == DeskOrientation.horizontal ){
            if (this.type.equals("mono")) return this.width;
            else return this.width*2;
        }
        else{
            if (this.type.equals("mono")) return this.height;
            else return this.height;
        }
    }
    /**
     * @author Victor VENULETH
     * @brief This method returns the height of a desk depending on its type and orientation
     * @return double : the height of the desk
     */
    public double getHeight(){
        if (orientation == DeskOrientation.horizontal ){
            if (this.type.equals("mono")) return this.height;
            else return this.height;
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
    /**
     * @autor Victor VENULETH
     * @brief This method returns a list of all the possible desk types
     * @return ArrayList<Desk> : the list of all the possible desk types
     */
    public static ArrayList<Desk> getDeskTypeList() {
        ArrayList<Desk> deskTypeList = new ArrayList<>();
        deskTypeList.add(new Desk(0,0,"mono",DeskOrientation.horizontal));
        deskTypeList.add(new Desk(0,0,"mono",DeskOrientation.vertical));
        deskTypeList.add(new Desk(0,0,"duo",DeskOrientation.horizontal));
        deskTypeList.add(new Desk(0,0,"duo",DeskOrientation.vertical));
        return deskTypeList;
    }

    @Override
    public String toString() {
        //If there is a student in the desk we print his name
        if (student != null) return "Desk{" +
                "type='" + type + '\'' +
                ", orientation=" + orientation +
                ", x=" + x +
                ", y=" + y +
                ", student name = "+student.getName()+" "+student.getSurname()+
                '}';
        else return "Desk{" +
                "type='" + type + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", orientation=" + orientation +
                '}';
    }
}
