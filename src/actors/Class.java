package actors;

import java.util.ArrayList;

public class Class {
    ArrayList<Actor> students;
    ArrayList<Subject> subjects;
    String className;
    int studentNumber = 0;
    int subjectNumber = 0;
    public Class(ArrayList<Actor> students, String className, ArrayList<Subject> subjects){
        this.students = students;
        this.studentNumber = students.size();
        this.subjects=subjects;
        this.subjectNumber=subjects.size();
        this.className=className;
    }

    @Override
    public String toString() {
        String string = "Class Name : "+this.className+" \n";
        string+="Student List of "+this.studentNumber+" students: \n";
        for (Actor a: students) {
            string += a.toString()+"\n";
        }string+="Subject List of "+this.subjectNumber+" Subjects: \n";
        for (Subject s: subjects) {
            string += s.toString()+"\n";
        }
        return string;
    }
}
