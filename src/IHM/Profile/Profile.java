package IHM.Profile;
/**
 * @Author Yohan JAFFRE
 * @Version 1.0
 * @Date 17/01/2024
 * @brief This class is the profile page of the application
 */

import IHM.Gestion.Gestion;
import IHM.Window.Window;
import datatypes.actors.Teacher;
import database.DatabaseManager;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Profile {
    private Stage primaryStage;
    private Window window;
    private DatabaseManager dbManager;
    private Teacher teacher;
    private Scene scene;
    private Rectangle2D screenBounds;
    private Button backButton;
    private Button disconnectButton;
    private VBox card;
    private ImageView profilePicture;
    private Image picture;
    private Text username;
    private Text SurnameAndName;
    private Text email;
    private String imageProfilePath;

    public Profile(DatabaseManager databaseManager, Stage primaryStage,Teacher teacher){
        this.primaryStage = primaryStage;                   // Récupération de la fenêtre
        this.dbManager = databaseManager;                   // Récupération du databaseManager
        this.window = new Window();                         // Création de la fenêtre
        this.teacher = teacher;                             // Récupération du teacher
        this.scene = getScene();                            // Création de la scène
        this.screenBounds = Screen.getPrimary().getVisualBounds();  // Récupération de la taille de l'écran
        this.window.setTitle("Profile");                    // Titre de la fenêtre

        //Create the back Button
        this.backButton = new Button();
        this.backButton.setPrefHeight(60);
        this.backButton.setPrefWidth(60);
        String imageBackPath = "file:" + System.getProperty("user.dir") + "/dataset/logo/back_icon.jpg";
        ImageView backIcon = new ImageView(new Image(imageBackPath));
        backIcon.setFitHeight(50);
        backIcon.setPreserveRatio(true);
        backButton.setGraphic(backIcon);
        this.backButton.setOnAction(event -> moveBack());

        //Create the disconect Button
        disconnectButton = new Button();
        String imageDisconnectPath = "file:" + System.getProperty("user.dir") + "/dataset/logo/disconnect_icon.jpg";
        Image disconnectImage = new Image(imageDisconnectPath);
        ImageView disconnectIcon = new ImageView(disconnectImage);
        disconnectIcon.setFitHeight(50);
        disconnectIcon.setPreserveRatio(true);
        disconnectButton.setGraphic(disconnectIcon);
        disconnectButton.setOnAction(event -> this.window.disconnect(primaryStage));

        String name = this.teacher.getName();
        String surname = this.teacher.getSurname();
        String username = this.teacher.getUsername();
        String email = this.teacher.getEmail();

        this.card = new VBox();

        if(teacher.getGender().equals("male")){
            this.imageProfilePath = "file:" + System.getProperty("user.dir") + "/dataset/logo/profile_icon_male.jpg";
        } else if (teacher.getGender().equals("female")) {
            this.imageProfilePath = "file:" + System.getProperty("user.dir") + "/dataset/logo/profile_icon_female.jpg";
        } else {
            this.imageProfilePath = "file:" + System.getProperty("user.dir") + "/dataset/logo/profile_icon_default.jpg";
        }
        this.picture = new Image(imageProfilePath);                     // Création de l'image de profil
        this.profilePicture = new ImageView(picture);                   // Création du conteneur de l'image de profil
        this.username = new Text("ID: "+username);             // Création du texte username
        this.SurnameAndName = new Text(surname+" "+name);            // Création du texte nom et prénom
        this.email = new Text(email);                                   // Création du texte email

        this.profilePicture.setPreserveRatio(true); // Permet de garder les proportions de l'image
        this.profilePicture.setFitHeight(300);      // Hauteur de l'image
        this.username.setStyle("-fx-fill: #ffffff; -fx-font-size: 25px;");          // Style du texte username
        this.SurnameAndName.setStyle("-fx-fill: #ffffff; -fx-font-size: 25px;");    // Style du texte nom et prénom
        this.email.setStyle("-fx-fill: #ffffff; -fx-font-size: 25px;");             // Style du texte email
        this.email.setTextAlignment(TextAlignment.CENTER);                          // Alignement du texte email
        this.card.setSpacing(15);                                                   // Espace entre les éléments de la carte
        this.card.setAlignment(Pos.CENTER);                                         // Alignement du contenu de la carte
        this.window.getMiddlePanel().setAlignment(Pos.CENTER);                      // Alignement du contenu de la fenêtre

        this.window.getTopPanel().getChildren().addAll(this.backButton, this.disconnectButton);    // Ajout du bouton home au panel du haut
        this.card.getChildren().add(this.username);                                 // Ajout du texte username à la carte
        this.card.getChildren().add(this.profilePicture);                  // Ajout du conteneur de l'image de profil à la carte
        this.card.getChildren().add(this.SurnameAndName);                           // Ajout du conteneur du nom et du prénom à la carte
        this.card.getChildren().add(this.email);                                    // Ajout du texte email à la carte
        this.window.getMiddlePanel().getChildren().add(this.card);                  // Ajout de la carte au milieu de la fenêtre
    }
    public void moveBack(){
        Gestion gestion = new Gestion(dbManager.getAllTeacherClassMapByTeacher(dbManager.getTeacherByID(teacher.getId())),screenBounds,primaryStage,dbManager, teacher.getId()); // Création de la page de gestion
        primaryStage.setScene(gestion.getScene());
    }
    public Scene getScene() {
        return window.getScene(); // Renvoie la scène
    }
}