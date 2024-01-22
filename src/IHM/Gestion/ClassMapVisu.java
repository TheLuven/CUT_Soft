package IHM.Gestion;
/**
 * @Author Victor VENULETH
 * @Version 1.0
 * @Date 15/01/2024
 * @brief This class is very close to classMap editor but is focus on the visualization of the classMap
 * @brief most of the mapEditor function are exactly the same
 */
import IHM.Window.Window;
import dataTypes.actors.Student;
import dataTypes.classMap.ClassMap;
import dataTypes.classMap.ClassMapLayer;
import dataTypes.classMap.object.BoardOrientation;
import dataTypes.classMap.object.Desk;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClassMapVisu {
    private ClassMapLayer classMapLayer;
    private Rectangle2D screenBounds;
    private Stage stage;
    private Window window;
    private ClassMap classMap;
    private ArrayList<Student> studentList;
    private Pane mapVizu;
    private Pane room;
    private double lastX = 0;
    private double lastY = 0;
    private Gestion gestion;
    private Button backButton;
    public ClassMapVisu(ClassMap classMap, ClassMapLayer classMapLayer, Rectangle2D screenBounds, Stage stage, Gestion gestion){
        this.classMapLayer = classMapLayer;
        this.screenBounds = screenBounds;
        this.stage = stage;
        this.classMap = classMap;
        this.studentList = classMap.getaClass().getStudents();
        this.gestion = gestion;
        this.window = new Window();
        this.mapVizu =new Pane();
        this.room = new Pane();
        this.window.getMiddlePanel().getChildren().add(this.mapVizu);
        display();
        window.setTitle("Visualize the Current map of : - "+classMap.getaClass().getClassName()+" - "+classMap.getSubject().getSubjectName()+" - "+classMap.getTeacher().getSurname()+" "+classMap.getTeacher().getName());
    }

    private void displayBotButtons(){
        this.backButton = new Button("Back");
        this.backButton.setOnAction(e->{
            this.stage.setScene(this.gestion.getScene());
        });
        this.window.getBotPanel().getChildren().addAll(this.backButton);
    }
    private void display(){
        displayRoom();
        displayBotButtons();
        displayBoard();
        displayDesk();
    }
    public void displayRoom(){
        this.mapVizu.setPrefWidth(this.screenBounds.getWidth());
        this.mapVizu.setPrefHeight(this.screenBounds.getHeight());
        this.mapVizu.setStyle("-fx-background-color: #2b2d30;");
        this.room.setStyle("-fx-border-color: #ffffff; -fx-background-color: #1c1c1e;");
        //Drw the room inside the mapEditor
        if (this.classMapLayer.getRoom().getHeight() > this.classMapLayer.getRoom().getWidth())
        {
            this.room.setPrefHeight(this.mapVizu.getPrefHeight()/2);
            this.room.setPrefWidth(this.mapVizu.getPrefHeight()/2*this.classMapLayer.getRoom().getWidth()/this.classMapLayer.getRoom().getHeight());
        }
        else if (this.classMapLayer.getRoom().getHeight() < this.classMapLayer.getRoom().getWidth())
        {
            this.room.setPrefWidth(this.mapVizu.getPrefWidth()/2);
            this.room.setPrefHeight(this.mapVizu.getPrefWidth()/2*this.classMapLayer.getRoom().getHeight()/this.classMapLayer.getRoom().getWidth());
        }
        else
        {
            this.room.setPrefWidth(this.mapVizu.getPrefWidth()/2);
            this.room.setPrefHeight(this.mapVizu.getPrefWidth()/2);
        }
        //zoom in/out
        this.room.setOnScroll(event -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            this.room.setScaleX(this.room.getScaleX() * zoomFactor);
            this.room.setScaleY(this.room.getScaleY() * zoomFactor);
        });
        this.mapVizu.setOnScroll(event -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            this.room.setScaleX(this.room.getScaleX() * zoomFactor);
            this.room.setScaleY(this.room.getScaleY() * zoomFactor);
        });

        this.room.setOnMousePressed(event -> {
                room.setCursor(Cursor.CLOSED_HAND);
                room.setMouseTransparent(true);
        });
        this.room.setOnMouseReleased(event -> {
                room.setCursor(Cursor.DEFAULT);
                room.setMouseTransparent(false);
                lastX = 0;
                lastY = 0;
        });
        this.room.setOnMouseDragged(event -> {
                if (lastX == 0 && lastY == 0) {
                    lastX = event.getSceneX();
                    lastY = event.getSceneY();
                }else{
                    double deltaX = event.getSceneX() - lastX;
                    double deltaY = event.getSceneY() - lastY;
                    this.room.setLayoutX(this.room.getLayoutX() + deltaX);
                    this.room.setLayoutY(this.room.getLayoutY() + deltaY);
                    lastX = event.getSceneX();
                    lastY = event.getSceneY();
                }
        });
        this.mapVizu.setOnMousePressed(event -> {
                room.setCursor(Cursor.CLOSED_HAND);
                room.setMouseTransparent(true);
        });
        this.mapVizu.setOnMouseReleased(event -> {
                room.setCursor(Cursor.DEFAULT);
                room.setMouseTransparent(false);
                lastX = 0;
                lastY = 0;

        });
        this.mapVizu.setOnMouseDragged(event -> {
                if (lastX == 0 && lastY == 0) {
                    lastX = event.getSceneX();
                    lastY = event.getSceneY();
                } else {
                    double deltaX = event.getSceneX() - lastX;
                    double deltaY = event.getSceneY() - lastY;
                    this.room.setLayoutX(this.room.getLayoutX() + deltaX);
                    this.room.setLayoutY(this.room.getLayoutY() + deltaY);
                    lastX = event.getSceneX();
                    lastY = event.getSceneY();
                }

        });
        this.mapVizu.getChildren().add(this.room);

    }
    public Scene getScene(){
        return this.window.getScene();
    }
    public void displayBoard(){
        Pane board = new BorderPane();
        board.setStyle("-fx-background-color: #000000;-fx-border-color: #ffffff;");
        if(this.classMapLayer.getRoom().getBoardOrientation()== BoardOrientation.north){
            board.setPrefHeight(15);
            board.setPrefWidth(this.room.getPrefWidth()/2);
            board.setLayoutX(this.room.getPrefWidth()/4);
            board.setLayoutY(0);
        }
        else if(this.classMapLayer.getRoom().getBoardOrientation()== BoardOrientation.south){
            board.setPrefHeight(15);
            board.setPrefWidth(this.room.getPrefWidth()/2);
            board.setLayoutX(this.room.getPrefWidth()/4);
            board.setLayoutY(this.room.getPrefHeight()-15);
        }
        else if(this.classMapLayer.getRoom().getBoardOrientation()== BoardOrientation.east){
            board.setPrefHeight(this.room.getPrefHeight()/2);
            board.setPrefWidth(15);
            board.setLayoutX(this.room.getPrefWidth()-15);
            board.setLayoutY(this.room.getPrefHeight()/4);
        }
        else if(this.classMapLayer.getRoom().getBoardOrientation()== BoardOrientation.west){
            board.setPrefHeight(this.room.getPrefHeight()/2);
            board.setPrefWidth(15);
            board.setLayoutX(0);
            board.setLayoutY(this.room.getPrefHeight()/4);
        }
        this.room.getChildren().add(board);
    }
    public void displayDesk(){
        double roomWidth = this.room.getPrefWidth();
        double roomHeight = this.room.getPrefHeight();
        double roomWidthRatio = roomWidth/this.classMapLayer.getRoom().getWidth();
        double roomHeightRatio = roomHeight/this.classMapLayer.getRoom().getHeight();
        //Draw the desks inside the mapEditor
        ArrayList<Desk> desks = this.classMapLayer.getDesks();
        for(Desk desk : desks){
            if(desk.getType().equals("mono")){
                drawMonoDesk(desk,roomWidthRatio,roomHeightRatio,0,0);
            }
        }
    }
    public void drawMonoDesk(Desk desk,double roomWidthRatio,double roomHeightRatio,double x,double y){
        Pane deskBox = new Pane();
        deskBox.setPrefWidth(desk.getWidth()*roomWidthRatio);
        deskBox.setPrefHeight(desk.getHeight()*roomHeightRatio);
        deskBox.setLayoutX(desk.getX()*roomWidthRatio+x);
        deskBox.setLayoutY(desk.getY()*roomWidthRatio+y);
        deskBox.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
        if (desk.getStudent()!=null){
            Student student = desk.getStudent();
            //add the student to the desk
            Text studentName = new Text(student.getName());
            Text studentSurname = new Text(student.getSurname());
            //center student name and change the color to white
            studentSurname.setLayoutX(deskBox.getPrefWidth()/2-studentSurname.getLayoutBounds().getWidth()/2);
            studentSurname.setLayoutY(deskBox.getPrefHeight()/2-studentSurname.getLayoutBounds().getHeight()/2+studentName.getLayoutBounds().getHeight());
            studentSurname.setStyle("-fx-fill: #ffffff;");
            studentName.setLayoutX(deskBox.getPrefWidth()/2-studentName.getLayoutBounds().getWidth()/2);
            studentName.setLayoutY(deskBox.getPrefHeight()/2-studentName.getLayoutBounds().getHeight()/2);
            studentName.setStyle("-fx-fill: #ffffff;");
            deskBox.getChildren().addAll(studentName,studentSurname);
        }
        this.room.getChildren().add(deskBox);
    }
}
