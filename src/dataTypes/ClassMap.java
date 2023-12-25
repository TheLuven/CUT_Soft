package dataTypes;

import dataTypes.actors.Class;
import dataTypes.actors.Subject;
import dataTypes.actors.Teacher;

public class ClassMap {
    private Teacher teacher;
    private Subject subject;
    private Class aClass;
    public ClassMap(Teacher teacher,Subject subject,Class aClass){
        this.teacher=teacher;
        this.subject=subject;
        this.aClass=aClass;
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
