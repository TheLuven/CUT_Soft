package IHM.Gestion;

import IHM.Window.Window;
import dataTypes.classMap.ClassMap;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Gestion {
    public Window template;
    public GridPane grid;
    public ArrayList<ClassMap> classMaps;
    public ScrollPane scrollPane;
    public ArrayList<ClassMapCard> classCardList;
    public Rectangle2D screenBounds;
    public double width;
    public double height;
    public Gestion(ArrayList<ClassMap> classMaps, Rectangle2D screenBounds, Stage stage){
        this.screenBounds = screenBounds;
        this.width = screenBounds.getWidth();
        this.height = screenBounds.getHeight();
        this.template = new Window();
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
    }

    public void createClassGrid(Stage stage){
        int index = 0;
        int line = 0;
        //System.out.println(classMaps.size());
        while(index < this.classMaps.size()){
            for(int i = 0;i<3 && index<this.classMaps.size();i++){
                ClassMapCard c = new ClassMapCard(this.classMaps.get(index),stage);
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
