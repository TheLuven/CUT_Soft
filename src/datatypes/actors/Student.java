package datatypes.actors;
/**
 * @Author Victor VENULETH
 * @Version 1.0
 * @Date 22/12/2023
 * @brief Class used to describe a student
 */

import database.DatabaseManager;

@SuppressWarnings({"checkstyle:MissingJavadocType"})
public class Student extends Actor {
    private String className;

    public Student(DatabaseManager source, int id, String name, String surname, String username, String email, String description, String className, String gender) {
        super(source, id, name, surname, username, email, description, gender);
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
        source.simpleUpdateQuery(this.id, className, "person", "className");
    }

    @Override
    public String toString() {
        return "[STUDENT] - " + "ID: " + this.id + ", Name: " + this.name + ", Surname: " + this.surname + ", ClassName: " + this.className + ", Email: " + this.email + ", Gender: " + gender + "\n Description >> " + this.description;
    }
}
