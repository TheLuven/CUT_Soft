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
    String url;
    String user;
    String password;
    /**
    * @brief Need url user and password to connect to the database
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println(super.getParameters().getRaw());
        url = super.getParameters().getRaw().get(0);
        user = super.getParameters().getRaw().get(1);
        if (super.getParameters().getRaw().size() < 3) {
            password= "";
        }else {
            password = super.getParameters().getRaw().get(2);
        }

        DatabaseConnection database = new DatabaseConnection(url,user,password);
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
