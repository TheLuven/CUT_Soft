package IHM.Window;

import IHM.Gestion.Gestion;
import IHM.Login.Login;
import IHM.Profile.Profile;
import database.DatabaseManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Window {
    private Scene scene;
    public boolean isConnected = false;
    private StackPane mainWindow;
    private Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    private Stage primaryStage;
    private DatabaseManager dbManager;
    private int id;
    private ImageView logo;
    private Button disconnectButton;
    private Button profileButton;
    private VBox screen;
    private HBox topPanel;
    private HBox botPanel;
    private HBox middlePanel;
    private Region topPanelBlank;
    public Window() {
        init();
        refreshWindow();
        scene = new Scene(mainWindow);
    }

    public Window(Stage primaryStage,boolean isConnected, int id, DatabaseManager databaseManager) {
        this();
        this.isConnected = isConnected;
        this.primaryStage = primaryStage;
        this.dbManager = databaseManager;
        this.id = id;
    }

    public void init(){
        primaryStage = primaryStage;                   // Récupération de la fenêtre


        mainWindow = new StackPane();
        mainWindow.setStyle("-fx-background-color: #2b2d30;"); //Change backgound color

        //Create the logo
        logo = new ImageView(new Image("CUT.png"));
        logo.setFitHeight(50); // Set the width as needed //TODO : Make the logo clickable to go back to an other stage
        logo.setPreserveRatio(true);

        //Create the disconect Button
        disconnectButton = new Button();
        String imageDisconnectPath = "file:" + System.getProperty("user.dir") + "/dataset/logo/disconnect_icon.jpg";
        Image disconnectImage = new Image(imageDisconnectPath);
        ImageView disconnectIcon = new ImageView(disconnectImage);
        disconnectIcon.setFitHeight(50);
        disconnectIcon.setPreserveRatio(true);
        disconnectButton.setGraphic(disconnectIcon);
        disconnectButton.setOnAction(event -> disconnect(primaryStage));

        //Create the profile Button
        profileButton = new Button();
        profileButton.setPrefHeight(60);
        profileButton.setPrefWidth(60);
        String imageProfilePath = "file:" + System.getProperty("user.dir") + "/dataset/logo/profile_icon_default.jpg";
        ImageView profileIcon = new ImageView(new Image(imageProfilePath));
        profileIcon.setFitHeight(50);
        profileIcon.setPreserveRatio(true);
        profileButton.setGraphic(profileIcon);
        profileButton.setOnAction(event -> openProfile());

        /**
         TUTO de TOTOR

         +----------------------------------+
         | Window                           |
         | +------------------------------+ |
         | |  VBOX (Vertical Box)         | |
         | | +--------------------------+ | |
         | | |HBox (Horizontal Box)     | | |
         | | +--------------------------+ | |
         | | +--------------------------+ | |
         | | |HBox (Horizontal Box)     | | |
         | | +--------------------------+ | |
         | | +--------------------------+ | |
         | | |HBox (Horizontal Box)     | | |
         | | +--------------------------+ | |
         | +------------------------------+ |
         +----------------------------------+

         En Gros on met des HBox dans des VBox. Nous on va probablement avoir 3 HBox
         (La topPanel, BotPanel et finalement la Middle avec le contenu de notre application (Page de connection - Tableau - Schéma etc....)
         */

        //Create the Vbox that will arrange everything in Vertical
        screen = new VBox();

        //Create the topPanel (Logo / Profile information / Disconnection button etc...)
        topPanel = new HBox();
        topPanel.setPadding(new Insets(5,5,5,5));   //Add a padding of 10px everywhere
        topPanel.setMaxHeight(60);
        topPanel.setMinHeight(60);
        StackPane.setAlignment(topPanel, Pos.TOP_CENTER);
        topPanel.setStyle("-fx-background-color: #1e1f22;");

        //Create the middlePanel
        middlePanel = new HBox();
        middlePanel.setPadding(new Insets(5,5,5,5));   //Add a padding of 10px everywhere
        StackPane.setAlignment(middlePanel, Pos.TOP_CENTER);
        middlePanel.setStyle("-fx-background-color: #2b2d30;");

        //Create the topPanel (Logo / Profile information / Disconnection button etc...)
        botPanel = new HBox();
        botPanel.setPadding(new Insets(5,5,5,5));   //Add a padding of 10px everywhere
        botPanel.setMaxHeight(40);
        botPanel.setMinHeight(40);
        StackPane.setAlignment(botPanel, Pos.TOP_CENTER);
        botPanel.setStyle("-fx-background-color: #1e1f22;");

        topPanelBlank = new Region();
    }
    /**
     TUTO de TOTOR :
     Priority.ALWAYS: The node should always grow or shrink to fill the available space.
     Priority.SOMETIMES: The node may grow or shrink if there is additional space, but it's not required to do so.
     Priority.NEVER: The node should not grow or shrink.
     +--------------------------------------------------------------------------+
     |             |                                      |                     |
     |    LOGO     |                BLANK                 | PROFILE | DISCONNECT|
     |             |                                      |                     |
     +--------------------------------------------------------------------------+
     */
    public void refreshWindow(){
        topPanel.getChildren().clear();
        middlePanel.getChildren().clear();
        botPanel.getChildren().clear();
        screen.getChildren().clear();
        mainWindow.getChildren().clear();
        if(isConnected){
            topPanel.getChildren().addAll(logo,topPanelBlank,profileButton, disconnectButton);

        }else{
            topPanel.getChildren().addAll(logo,topPanelBlank);
        }
        screen.getChildren().addAll(topPanel,middlePanel,botPanel);
        HBox.setHgrow(disconnectButton, Priority.NEVER);
        HBox.setHgrow(topPanelBlank, Priority.ALWAYS);
        VBox.setVgrow(topPanel,Priority.NEVER);
        VBox.setVgrow(middlePanel,Priority.ALWAYS);
        VBox.setVgrow(botPanel,Priority.NEVER);
        mainWindow.getChildren().addAll(screen);
    }
    public void disconnect(Stage primaryStage){
        isConnected=false;
        Login login = new Login(dbManager,primaryStage);
        primaryStage.setScene(login.getScene());
    }

    public void openProfile(){
        Profile profile = new Profile(dbManager,primaryStage,dbManager.getTeacherByID(id));
        primaryStage.setScene(profile.getScene());
    }
    public Scene getScene() {
        return scene;
    }
    public void setTitle(String title){
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_LEFT);
        this.topPanel.getChildren().clear();
        Text titleText = new Text(title);
        titleText.setFill(javafx.scene.paint.Color.WHITE);
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        container.getChildren().add(titleText);
        if(isConnected){
            this.topPanel.getChildren().addAll(logo,topPanelBlank,container,region,profileButton, disconnectButton);

        }
        else{
            this.topPanel.getChildren().addAll(logo,topPanelBlank,container,region);
        }
    }
    public VBox getScreen(){ return  screen; }

    public HBox getTopPanel(){ return topPanel; }

    public HBox getBotPanel(){ return botPanel; }

    public HBox getMiddlePanel(){ return middlePanel; }

    public Button getDisconnectButton(){ return disconnectButton; }

    public Button getProfileButton(){ return profileButton; }
}
