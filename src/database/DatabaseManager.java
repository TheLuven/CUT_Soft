package database;

import actors.Actor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Actor getActorByID(int id) {
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT p.name,p.surname,p.email,p.gender,p.role,p.description,c.className FROM person as p,class as c,classPerson as cp WHERE p.id=" + id+ " AND cp.person = "+id+" AND cp.class = c.id";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String email = resultSet.getString("email");
            String gender = resultSet.getString("gender");
            String description = resultSet.getString("description");
            String role = resultSet.getString("role");
            String className = resultSet.getString("className");
            return new Actor(this, id, name, surname, role, email, description, className, gender);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
