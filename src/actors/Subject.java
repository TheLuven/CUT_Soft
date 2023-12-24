package actors;

import java.util.ArrayList;

public class Subject {
    String subjectName;
    Actor teacher;
    ArrayList<Class> classes;

    public Subject(String subjectName, Actor teacher, ArrayList<Class> classes){
        this.classes=classes;
        this.teacher=teacher;
        this.subjectName=subjectName;
    }
}
