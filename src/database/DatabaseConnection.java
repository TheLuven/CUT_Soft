package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public String connectionStatus;
    String url = "jdbc:mysql://localhost:8889/base";
    String user = "root";
    String password = "root";
    public DatabaseConnection(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
        checkConnection();
    }
    public void checkConnection(){
        connectionStatus = "NotConnected";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            connectionStatus = "Error";
            System.out.println("[ERROR] :"+e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");
            connectionStatus = "Connected";
        } catch (SQLException e) {
            connectionStatus = "Error";
            System.out.println("[ERROR] :"+e.getMessage());
        }
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public String getStatus(){
        return this.connectionStatus;
    }
}