package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    DatabaseConnection database;
    public DatabaseManager(DatabaseConnection database){
        this.database=database;
    }


    public void getActorByID(){
        try (Connection connection = this.database.getConnection()) {
            String selectQuery = "SELECT * FROM person";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                // Execute the query and get the result set
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Process the result set
                    while (resultSet.next()) {
                        // Retrieve data from the result set
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("surname");
                        // ... add more columns as needed

                        // Process the retrieved data
                        System.out.println("ID: " + id + ", Name: " + name);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){

        }
    }
}
