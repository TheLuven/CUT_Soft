package datatypes.classMap.object;

import datatypes.actors.Student;
import datatypes.classMap.Subject;

import java.util.ArrayList;
    /**
     * @Author Victor VENULETH
     * @Version 1.0
     * @Date 22/12/2023
     * @brief Class used to describe the class of the classMap
     */
public class Class {
    private int classId;
    private ArrayList<Student> students;
    private ArrayList<Subject> subjects;
    private String className;
    private int studentNumber = 0;
    private int subjectNumber = 0;
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

    public Student getStudentByID(int i){
        for (Student student : this.students){
            if (student.getId() == i) return student;
        }
        return null;
    }
}
