package dataTypes.classMap;
/*************************
 * @author Victor VENULETH
 * @date 10/01/2024
 ************************/

import dataTypes.actors.Actor;
import dataTypes.actors.Teacher;

/**
 * @brief This class is used to represent a subject
 * @author Victor VENULETH
 */
public class Subject {
    private String subjectName;
    private Teacher teacher;
    private int subjectId;
    public Subject(String subjectName, Teacher teacher,int subjectId){
        this.teacher=teacher;
        this.subjectName=subjectName;
        this.subjectId = subjectId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
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
