import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Créer un libellé (Label) avec du texte
        Label label = new Label("Bonjour, ceci est ma fenêtre JavaFX!");

        // Créer une scène (Scene) et y ajouter le libellé
        Scene scene = new Scene(label, 300, 200);

        // Définir la scène de la fenêtre principale
        primaryStage.setScene(scene);

        // Définir le titre de la fenêtre
        primaryStage.setTitle("Ma Fenêtre JavaFX");

        // Afficher la fenêtre
        primaryStage.show();
    }
}
