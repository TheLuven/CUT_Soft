package dataTypes.classMap;

import dataTypes.actors.Teacher;

public class ClassMap {
    private Teacher teacher;
    private Subject subject;
    private Class aClass;

    private String status;
    public ClassMap(Teacher teacher,Subject subject,Class aClass){
        this.teacher=teacher;
        this.subject=subject;
        this.aClass=aClass;
        this.status = "undefined";
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Class getaClass() {
        return aClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "ClassMap{" +
                "teacher=" + teacher.toString() +"\n"+
                ", subject=" + subject.toString() +"\n"+
                ", Class=" + aClass.getClassName() +"\n"+
                '}';
    }
}
