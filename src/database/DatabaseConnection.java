package database;
/*****************************************************************
 * DatabaseConnection
 * @brief This class is used to connect to the database
 * @author Victor VENULETH
 * @param url
 * @param user
 * @param password
 * @return connectionStatus
 * @throws ClassNotFoundException
 * @throws SQLException
 *****************************************************************/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private String connectionStatus;
    private String url;
    private String user;
    private String password;
    public DatabaseConnection(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
        checkConnection();
    }

    /**
     * @brief This method is used to check if the connection to the database is working
     * @author Victor VENULETH
     */
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

    /**
     * @author Victor VENULETH
     * @brief This method is used to get the connection to the database
     * @return connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public String getStatus(){
        return this.connectionStatus;
    }
}