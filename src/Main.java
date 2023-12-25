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
        DatabaseConnection database = new DatabaseConnection("jdbc:mysql://localhost:8889/base","root","root");
        DatabaseManager databaseManager = new DatabaseManager(database);
        System.out.println(databaseManager.getTeacherByID(10).toString());
        /*System.out.println(databaseManager.getStudentByID(7).toString());
        System.out.println(databaseManager.getStudentClassByID(2).toString());
        databaseManager.getActorByID(1).setSurname("test");
        System.out.println(databaseManager.getActorByID(1).toString());*/
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Window myWindow = new Window();
        primaryStage.setScene(myWindow.getScene());
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);
        primaryStage.setTitle("CUT : Class Ultimate Team");
        primaryStage.show();
    }
}
