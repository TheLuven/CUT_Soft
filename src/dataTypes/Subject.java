package dataTypes;

import dataTypes.actors.Actor;
import dataTypes.actors.Teacher;

public class Subject {
    String subjectName;
    Teacher teacher;

    public Subject(String subjectName, Teacher teacher){
        this.teacher=teacher;
        this.subjectName=subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectName = '" + subjectName + '\'' +
                ", teacher = " + teacher.getName() +" "+teacher.getSurname()+
                '}';
    }
}
