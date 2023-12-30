package dataTypes.classMap;

import dataTypes.actors.Teacher;

import java.util.ArrayList;

public class ClassMap {
    private Teacher teacher;
    private Subject subject;
    private Class aClass;
    public ArrayList<ClassMapLayer> drafts;
    public ClassMapLayer currentClassMap;
    private String status;
    public ClassMap(Teacher teacher,Subject subject,Class aClass){
        this.teacher=teacher;
        this.subject=subject;
        this.aClass=aClass;
        this.status = "undefined";
        this.drafts = new ArrayList<>();
        this.drafts.add(new ClassMapLayer("test"));
        this.drafts.add(new ClassMapLayer("test2"));
        this.drafts.add(new ClassMapLayer("test3"));
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
    public void addDraft(ClassMapLayer draft){
        this.drafts.add(draft);
    }
    public void removeDraft(ClassMapLayer draft){
        this.drafts.remove(draft);
    }
    public void setCurrentClassMap(ClassMapLayer currentClassMap){
        this.currentClassMap = currentClassMap;
    }
    public ClassMapLayer getCurrentClassMap(){
        return this.currentClassMap;
    }
    public ArrayList<ClassMapLayer> getDrafts(){
        return this.drafts;
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
