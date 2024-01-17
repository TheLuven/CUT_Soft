package dataTypes.classMap;

import dataTypes.actors.Student;
import dataTypes.actors.Teacher;
import dataTypes.classMap.object.BoardOrientation;
import dataTypes.classMap.object.Class;
import dataTypes.classMap.object.Desk;
import dataTypes.classMap.object.DeskOrientation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
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
        getAllLocal();
    }
    public void getAllLocal(){
        this.drafts.clear();
        //Check in the files if there is a directory with the same subject and class
        File folder = new File("Drafts/"+this.aClass.getClassName()+"/"+this.subject.getSubjectName());
        Path path = Path.of(folder.getPath());
        if(Files.exists(path)){
            for (final File fileEntry : folder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    //Read the file
                    try {
                        ArrayList<Student> students = getaClass().getStudents();
                        Object o = new JSONParser().parse(new FileReader(fileEntry.getPath()));
                        JSONObject jo = (JSONObject) o;
                        String name = (String) jo.get("name");
                        double roomWidth = (double) jo.get("roomWidth");
                        double roomHeight = (double) jo.get("roomHeight");
                        String boardOrientation = (String) jo.get("boardOrientation");
                        BoardOrientation bo = BoardOrientation.north;
                        if (boardOrientation.equals("north")) bo = BoardOrientation.north;
                        else if (boardOrientation.equals("south")) bo = BoardOrientation.south;
                        else if (boardOrientation.equals("east")) bo = BoardOrientation.east;
                        else if (boardOrientation.equals("west")) bo = BoardOrientation.west;
                        JSONArray desks = (JSONArray) jo.get("desks");
                        ClassMapLayer classMapLayer = new ClassMapLayer(name,roomWidth,roomHeight,bo);
                        for (Object desk : desks){
                            JSONObject deskObject = (JSONObject) desk;
                            double x = (double) deskObject.get("x");
                            double y = (double) deskObject.get("y");
                            String orientation = (String) deskObject.get("orientation");
                            DeskOrientation deskOrientation = DeskOrientation.horizontal;
                            if (orientation.equals("horizontal")) deskOrientation = DeskOrientation.horizontal;
                            else if (orientation.equals("vertical")) deskOrientation = DeskOrientation.vertical;
                            Desk desk1 = new Desk(x,y,"mono",deskOrientation);
                            int id ;
                            if (deskObject.get("student_id") == null);
                            else{
                                try {
                                    id = Math.toIntExact((long) deskObject.get("student_id"));
                                }catch (Exception e){
                                    id = -1;
                                }
                                if (id != -1){
                                    Student student = this.getStudentByID(id);
                                    desk1.setStudent(student);
                                }
                            }
                            classMapLayer.addDesk(desk1);
                        }
                        classMapLayer.parseDesks();
                        this.drafts.add(classMapLayer);
                    }catch (Exception e){
                        System.out.println("Error reading file");
                    }
                }
            }
        }
    }
    public Subject getSubject() {
        return subject;
    }
    public Student getStudentByID(int i){
        for (Student student : this.aClass.getStudents()){
            if (student.getId() == i) return student;
        }
        return null;
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
