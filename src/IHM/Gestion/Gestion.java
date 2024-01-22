package IHM.Gestion;
/**
 * @Author Victor VENULETH
 * @Version 1.0
 * @Date 20/12/2023
 * @brief This class is the main page of the application. It shows every classCard of the teacher
 */
import IHM.Window.Window;
import datatypes.classMap.ClassMap;
import database.DatabaseManager;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Gestion {
    private Window template;
    private GridPane grid;
    private ArrayList<ClassMap> classMaps;
    private ScrollPane scrollPane;
    private ArrayList<ClassMapCard> classCardList;
    private Rectangle2D screenBounds;
    private double width;
    private double height;
    private int id;
    private Stage stage;
    private DatabaseManager dbManager;
    public Gestion(ArrayList<ClassMap> classMaps, Rectangle2D screenBounds, Stage stage, DatabaseManager dbManager, int id){
        this.stage = stage;
        this.id = id;
        this.screenBounds = screenBounds;
        this.width = screenBounds.getWidth();
        this.height = screenBounds.getHeight();
        this.dbManager = dbManager;
        this.template = new Window(stage,true,id,dbManager);
        this.grid = new GridPane();
        this.grid.setHgap(50);
        this.grid.setVgap(50);
        this.scrollPane = new ScrollPane();
        this.scrollPane.fitToWidthProperty().set(true);
        this.scrollPane.fitToHeightProperty().set(true);
        this.scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        this.classMaps=classMaps;
        this.classCardList = new ArrayList<>();
        createClassGrid(stage);
        this.scrollPane.setStyle("-fx-background-color: #2b2d30;");
        this.grid.setStyle("-fx-background-color: #2b2d30;");
        this.grid.setPadding(new Insets(25,25,25,25));
        this.scrollPane.setContent(this.grid);
        this.template.getMiddlePanel().getChildren().add(this.scrollPane);
        this.template.setTitle("Gestion");
    }
    /**
     * @brief This method is used to refresh the window and information (THIS IS NOT OPTIMIZED AND RELOAD EVERYTHING)
     * @author Victor VENULETH
     * @// TODO: 12/01/2024 Optimize this method to reduce the number of request to the DB
     */
    public void reload(){
        for(ClassMap c : this.classMaps){
            c.getAllLocal();
        }
        for (ClassMapCard c : this.classCardList){
            c.reloadCurrentClassMap();
        }
        this.grid.getChildren().clear();
        createClassGrid(this.stage);
    }
    public DatabaseManager getDataBaseManager(){
        return this.dbManager;
    }

    /**
     * @brief This method is used to create the grid of classCard and add them to the gridPane
     * @author Victor VENULETH
     * @param stage
     */
    public void createClassGrid(Stage stage){
        int index = 0;
        int line = 0;
        //System.out.println(classMaps.size());
        while(index < this.classMaps.size()){
            for(int i = 0;i<3 && index<this.classMaps.size();i++){
                ClassMapCard c = new ClassMapCard(this.classMaps.get(index),stage,this);
                c.getCard().setMinHeight(this.height/2);
                c.getCard().setPrefWidth(this.width/3);
                this.grid.add(c.getCard(),i,line);
                index++;
            }
            line++;
        }
    }

    public Scene getScene(){
        return this.template.getScene();
    }
}
