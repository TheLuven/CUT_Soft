package datatypes.actors;
/**
 * @Author Victor VENULETH
 * @Version 1.0
 * @Date 22/12/2023
 * @brief Class used to describe a Teacher
 */
import datatypes.classMap.ClassMap;
import database.DatabaseManager;

import java.util.ArrayList;

public class Teacher extends Actor{
    private ArrayList<ClassMap> classMaps;
    public Teacher(DatabaseManager source, int id, String name, String surname,String username, String email, String description, String gender) {
        super(source, id, name, surname, username, email, description, gender);
        this.classMaps = super.source.getAllTeacherClassMapByTeacher(this);
    }

    public ArrayList<ClassMap> getClassMaps() {
        return classMaps;
    }

    public void setClassMaps(ArrayList<ClassMap> classMaps) {
        this.classMaps = classMaps;
    }

    @Override
    public String toString() {
        return "[TEACHER] - " + "ID: " + this.id + ", Name: " + this.name+", Surname: "+this.surname+", " +
                "Email: " + this.email + ", Gender: "+gender+", Number of classes : "+this.classMaps.size()+"\n Description >> "+this.description;
    }

}
