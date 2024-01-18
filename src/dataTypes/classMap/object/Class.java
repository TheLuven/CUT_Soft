package dataTypes.classMap.object;

import dataTypes.actors.Student;
import dataTypes.classMap.Subject;

import java.util.ArrayList;

public class Class {
    int classId;
    public ArrayList<Student> students;
    public ArrayList<Subject> subjects;
    public String className;
    public int studentNumber = 0;
    public int subjectNumber = 0;
    public Class(ArrayList<Student> students, String className, ArrayList<Subject> subjects,int classId){
        this.students = students;
        this.studentNumber = students.size();
        this.subjects=subjects;
        this.subjectNumber=subjects.size();
        this.className=className;
        this.classId = classId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        String string = "Class Name : "+this.className+" \n";
        string+="Student List of "+this.studentNumber+" students: \n";
        for (Student st: students) {
            string += st.toString()+"\n";
        }string+="Subject List of "+this.subjectNumber+" Subjects: \n";
        for (Subject s: subjects) {
            string += s.toString()+"\n";
        }
        return string;
    }
}
