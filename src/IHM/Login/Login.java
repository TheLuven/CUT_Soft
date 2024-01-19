package IHM.Login;

import IHM.Gestion.Gestion;
import IHM.Window.Window;
import database.DatabaseManager;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class Login{
    private Window window;
    private Stage primaryStage;
    private Scene scene;
    private VBox card;
    private Text username;
    private TextField usernameField;
    private Text password;
    private PasswordField passwordField;
    private Button loginButton;
    private DatabaseManager dbManager;
    private Label errorMessage;
    private Rectangle2D screenBounds;

    public Login(DatabaseManager databaseManager, Stage primaryStage){

        this.primaryStage = primaryStage;                           // Récupération de la fenêtre
        this.window = new Window();                                 // Création de la fenêtre
        this.scene = getScene();                                    // Création de la scène
        this.screenBounds = Screen.getPrimary().getVisualBounds();  // Récupération de la taille de l'écran
        this.card = new VBox();                                     // Création de la carte
        this.username = new Text("Username");                    // Création du texte username
        this.usernameField = new TextField();                       // Création du champ de texte username
        this.password = new Text("Password");                    // Création du texte password
        this.passwordField = new PasswordField();                   // Création du champ de texte password
        passwordField.setOnKeyPressed(event -> {                    // Validation du mot de passe avec la touche entrée
            if (event.getCode() == KeyCode.ENTER) {
                id_check();
            }
        });
        this.loginButton = new Button("Login");                  // Création du bouton login
        this.loginButton.setOnAction(event -> id_check());          // Action du bouton login
        this.errorMessage = new Label();                            // Création du message d'erreur
        this.dbManager = databaseManager;                           // Récupération du databaseManager

        this.card.setAlignment(Pos.CENTER);                                     // Alignement du contenu de la carte
        this.card.setSpacing(15);                                               // Espace entre les éléments de la carte
        this.window.getMiddlePanel().setAlignment(Pos.CENTER);                  // Alignement du contenu de la fenêtre
        this.window.setTitle("Login");                                          // Titre de la fenêtre

        this.username.setStyle("-fx-fill: #ffffff; -fx-font-size: 25px;");      // Style du texte username
        this.usernameField.setStyle("-fx-font-size: 14px;");                    // Style du champ de texte username
        this.password.setStyle("-fx-fill: #ffffff; -fx-font-size: 25px;");      // Style du texte password
        this.passwordField.setStyle("-fx-font-size: 14px;");                    // Style du champ de texte password
        this.loginButton.setStyle("-fx-font-size: 20px;");                      // Style du bouton login
        this.errorMessage.setStyle("-fx-text-fill: red; -fx-font-size: 14px;"); // Style du message d'erreur

        this.card.getChildren().add(this.username);                 // Ajout du texte username à la carte
        this.card.getChildren().add(this.usernameField);            // Ajout du champ de texte username à la carte
        this.card.getChildren().add(this.password);                 // Ajout du texte password à la carte
        this.card.getChildren().add(this.passwordField);            // Ajout du champ de texte password à la carte
        this.card.getChildren().add(this.loginButton);              // Ajout du bouton login à la carte
        this.card.getChildren().add(this.errorMessage);             // Ajout du message d'erreur à la carte
        this.window.getMiddlePanel().getChildren().add(this.card);  // Ajout de la carte au milieu de la fenêtre
    }

    public void id_check() {
        int id_get = dbManager.getIdByUsername(usernameField.getText()); // Récupère l'id via l'username
        if (id_get == -1) {
            errorDetected("Incorrect username. Please try again."); // Reinitialise les champs et affiche le message d'erreur
        } else { // Si l'id est correct
            boolean result = dbManager.CheckPassword(id_get, passwordField.getText()); // Vérifie le mot de passe
            if (result) { // Si le mot de passe est correct
                errorMessage.setText(""); // Efface le message d'erreur
                Gestion gestion = new Gestion(dbManager.getAllTeacherClassMapByTeacher(dbManager.getTeacherByID(id_get)),screenBounds,primaryStage,dbManager,id_get); // Création de la page de gestion
                primaryStage.setScene(gestion.getScene());
            } else {
                errorDetected("Incorrect password. Please try again."); // Reinitialise les champs et affiche le message d'erreur
            }
        }
    }
    private void errorDetected(String message) {
        usernameField.clear();// Efface le champ username
        passwordField.clear();// Efface le champ password
        errorMessage.setText(message); // Affiche le message d'erreur
    }
    public Scene getScene() {
        return window.getScene(); // Renvoie la scène
    }
}