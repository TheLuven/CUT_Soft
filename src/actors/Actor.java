package actors;

import database.DatabaseManager;

public class Actor {
    DatabaseManager source;
    public int id;
    protected String name;
    protected String surname;
    protected String className;
    protected String email;
    protected String description;
    protected String role;
    protected String gender;

    public Actor(DatabaseManager source,int id, String name, String surname, String role, String email, String description, String className,String gender){
        this.source=source;
        this.refreshInformation(id, name, surname, role, email, description, className, gender);
    }

    public void refreshInformation(int id, String name, String surname, String role, String email, String description, String className,String gender){
        this.id = id;
        this.name=name;
        this.surname=surname;
        this.role=role;
        this.email=email;
        this.description=description;
        this.className=className;
        this.gender=gender;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        source.simpleUpdateQuery(this.id,name,"person","name");
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        source.simpleUpdateQuery(this.id,surname,"person","surname");
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
        source.simpleUpdateQuery(this.id,className,"person","className");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        source.simpleUpdateQuery(this.id,email,"person","email");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        source.simpleUpdateQuery(this.id,description,"person","description");
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
        source.simpleUpdateQuery(this.id,role,"person","role");
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        source.simpleUpdateQuery(this.id,gender,"person","gender");
    }

    @Override
    public String toString() {
        return "["+this.role+"] - "+"ID: " + this.id + ", Name: " + this.name+", Surname: "+this.surname+", ClassName: "+this.className+", Email: "+this.email+", Gender: "+gender+"\n Description >> "+this.description;
    }
}
