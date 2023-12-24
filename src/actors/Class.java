package actors;

import java.util.ArrayList;

public class Class {
    ArrayList<Actor> students;
    ArrayList<Subject> subjects;
    String className;
    public Class(ArrayList<Actor> students, String className, ArrayList<Subject> subjects){
        this.students = students;
        this.subjects=subjects;
        this.className=className;
    }
}
