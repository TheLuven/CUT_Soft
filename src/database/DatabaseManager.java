package database;

import actors.Actor;
import actors.Class;
import actors.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseManager {
    DatabaseConnection database;

    public DatabaseManager(DatabaseConnection database) {
        this.database = database;
    }

    public void simpleUpdateQuery(int id, String value, String tableName, String columnName) {
        try {
            Connection connection = this.database.getConnection();
            String updateQuery = "UPDATE "+tableName+" SET "+columnName+" = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, id);
            System.out.println("[DEBUG] Update Query : "+preparedStatement.toString());
            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();
            // Check the number of rows affected
            System.out.println("[DEBUG] "+rowsAffected + " row(s) updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String simpleGetQuery(int id, String value, String tableName) {
        try {
            Connection connection = this.database.getConnection();
            String updateQuery = "SELECT ? FROM ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, tableName);
            preparedStatement.setInt(3, id);
            System.out.println("[DEBUG] Get Query : "+preparedStatement.toString());
            // Execute the get query
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check the number of rows affected
            return resultSet.getString(value);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public Actor getStudentByID(int id) {
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT p.name,p.surname,p.email,p.gender,p.role,p.description,c.className FROM person as p,class as c,classPerson as cp WHERE p.id=" + id+ " AND cp.person = "+id+" AND cp.class = c.id";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String description = resultSet.getString("description");
                String role = resultSet.getString("role");
                String className = resultSet.getString("className");
                return new Actor(this, id, name, surname, role, email, description, className, gender);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Actor getTeacherByID(int id) {
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT p.name,p.surname,p.email,p.gender,p.role,p.description FROM person as p WHERE p.id=" + id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String description = resultSet.getString("description");
                String role = resultSet.getString("role");
                return new Actor(this, id, name, surname, role, email, description, null, gender);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Subject getSubjectByID(int id) {
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT subject,teacher FROM subject WHERE id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String subject = resultSet.getString("subject");
            int teacher = resultSet.getInt("teacher");
            return new Subject(subject,getTeacherByID(teacher));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Actor> getClassStudentsByID(int id){
        ArrayList<Actor> students = new ArrayList<Actor>();
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT person FROM classPerson WHERE class="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                students.add(getStudentByID(resultSet.getInt("person")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return students;
    }
    public ArrayList<Subject> getClassSubjectsByID(int id){
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT subject  FROM classSubject cs WHERE cs.class = "+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                subjects.add(getSubjectByID(resultSet.getInt("subject")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return subjects;
    }
    public Class getClassByID(int id){
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT className  FROM class WHERE id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String className = resultSet.getString("className");
            ArrayList<Actor> students = getClassStudentsByID(id);
            ArrayList<Subject> subjects = getClassSubjectsByID(id);
            return new Class(students,className,subjects);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
