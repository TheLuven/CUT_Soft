import IHM.Gestion.ClassMapEditor;
import IHM.Gestion.Gestion;
import IHM.Login.Login;
import IHM.Window.Window;
import database.DatabaseConnection;
import database.DatabaseManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DatabaseConnection database = new DatabaseConnection("jdbc:mysql://localhost:8889/base","root","root");
        DatabaseManager databaseManager = new DatabaseManager(database);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Login login = new Login(databaseManager,primaryStage);
        primaryStage.setScene(login.getScene());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);
        primaryStage.setTitle("CUT : Class Ultimate Team");
        primaryStage.show();
    }
}
