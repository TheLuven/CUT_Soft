package dataTypes.actors;

import database.DatabaseManager;

public abstract class Actor {
    DatabaseManager source;
    public int id;
    protected String name;
    protected String surname;
    protected String email;
    protected String description;
    protected String username;
    protected String role;
    protected String gender;

    public Actor(DatabaseManager source,int id, String name, String surname,String username, String email, String description,String gender){
        this.source=source;
        this.refreshInformation(id, name, surname,username, email, description, gender);
    }

    public void refreshInformation(int id, String name, String surname, String username, String email, String description,String gender){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.description = description;
        this.gender = gender;
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
        source.simpleUpdateQuery(this.id,username,"person","username");
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        source.simpleUpdateQuery(this.id,gender,"person","gender");
    }
}
