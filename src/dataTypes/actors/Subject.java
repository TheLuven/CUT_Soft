package dataTypes.actors;

public class Subject {
    String subjectName;
    Actor teacher;

    public Subject(String subjectName, Actor teacher){
        this.teacher=teacher;
        this.subjectName=subjectName;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectName = '" + subjectName + '\'' +
                ", teacher = " + teacher.name +" "+teacher.surname+
                '}';
    }
}
