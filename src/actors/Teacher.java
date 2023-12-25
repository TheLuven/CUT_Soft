package actors;

import database.DatabaseManager;

public class Teacher extends Actor{
    public Teacher(DatabaseManager source, int id, String name, String surname, String email, String description, String gender) {
        super(source, id, name, surname, email, description, gender);
    }

    @Override
    public String toString() {
        return "[TEACHER] - "+"ID: " + this.id + ", Name: " + this.name+", Surname: "+this.surname+", Email: "+this.email+", Gender: "+gender+"\n Description >> "+this.description;
    }
}
