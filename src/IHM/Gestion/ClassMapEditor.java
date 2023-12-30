package IHM.Gestion;

import dataTypes.actors.Student;
import dataTypes.classMap.ClassMap;
import dataTypes.classMap.ClassMapLayer;
import dataTypes.classMap.object.Desk;
import dataTypes.classMap.object.boardOrientation;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import IHM.Window.Window;

import java.util.ArrayList;
import java.util.Map;

public class ClassMapEditor {
    private ClassMapLayer classMapLayer;
    private Rectangle2D screenBounds;
    private Stage stage;
    private Window window;
    private ClassMap classMap;
    private ArrayList<Student> studentList;
    private SplitPane splitView;

    private Pane mapEditor;
    private Pane room;
    private double lastX = 0;
    private double lastY = 0;
    private ListView<Student> listView;
    public ClassMapEditor(ClassMap ClassMap,ClassMapLayer classMapLayer, Rectangle2D screenBounds, Stage stage){
        this.classMapLayer = classMapLayer;
        this.screenBounds = screenBounds;
        this.splitView = new SplitPane();
        this.stage = stage;
        this.window = new Window();
        this.classMap = ClassMap;
        this.studentList = classMap.getaClass().getStudents();
        this.mapEditor = new Pane();
        this.room = new Pane();
        display();
        window.setTitle("Class Map Editor - "+classMap.getaClass().getClassName()+" - "+classMap.getSubject().getSubjectName()+" - "+classMap.getTeacher().getSurname()+" "+classMap.getTeacher().getName());
    }
    public void display(){
        diplayStudentList();
        displayRoom();
        displayDesk();
        displayBoard();
    }
    public void displayRoom(){
        this.mapEditor.setPrefWidth(this.screenBounds.getWidth());
        this.mapEditor.setPrefHeight(this.screenBounds.getHeight());
        this.mapEditor.setStyle("-fx-background-color: #2b2d30;");
        this.room.setStyle("-fx-border-color: #ffffff; -fx-background-color: #1c1c1e;");
        //Drw the room inside the mapEditor
        if (this.classMapLayer.getRoom().getHeight() > this.classMapLayer.getRoom().getWidth())
        {
            this.room.setPrefHeight(this.mapEditor.getPrefHeight()/2);
            this.room.setPrefWidth(this.mapEditor.getPrefHeight()/2*this.classMapLayer.getRoom().getWidth()/this.classMapLayer.getRoom().getHeight());
        }
        else if (this.classMapLayer.getRoom().getHeight() < this.classMapLayer.getRoom().getWidth())
        {
            this.room.setPrefWidth(this.mapEditor.getPrefWidth()/2);
            this.room.setPrefHeight(this.mapEditor.getPrefWidth()/2*this.classMapLayer.getRoom().getHeight()/this.classMapLayer.getRoom().getWidth());
        }
        else
        {
            this.room.setPrefWidth(this.mapEditor.getPrefWidth()/2);
            this.room.setPrefHeight(this.mapEditor.getPrefWidth()/2);
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
        this.mapEditor.setOnScroll(event -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            this.room.setScaleX(this.room.getScaleX() * zoomFactor);
            this.room.setScaleY(this.room.getScaleY() * zoomFactor);
        });

        //move the map with the mouse
        this.room.setOnMousePressed(event -> {
            room.setCursor(javafx.scene.Cursor.CLOSED_HAND);
            room.setMouseTransparent(true);
        });
        this.room.setOnMouseReleased(event -> {
            room.setCursor(javafx.scene.Cursor.DEFAULT);
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
        this.mapEditor.setOnMousePressed(event -> {
            room.setCursor(javafx.scene.Cursor.CLOSED_HAND);
            room.setMouseTransparent(true);
        });
        this.mapEditor.setOnMouseReleased(event -> {
            room.setCursor(javafx.scene.Cursor.DEFAULT);
            room.setMouseTransparent(false);
            lastX = 0;
            lastY = 0;
        });
        this.mapEditor.setOnMouseDragged(event -> {
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
        this.mapEditor.getChildren().add(this.room);

    }
    public void displayBoard(){
        Pane board = new BorderPane();
        board.setStyle("-fx-background-color: #000000;-fx-border-color: #ffffff;");
        if(this.classMapLayer.getRoom().getBoardOrientation()== boardOrientation.north){
            board.setPrefHeight(15);
            board.setPrefWidth(this.room.getPrefWidth()/2);
            board.setLayoutX(this.room.getPrefWidth()/4);
            board.setLayoutY(0);
        }
        else if(this.classMapLayer.getRoom().getBoardOrientation()== boardOrientation.south){
            board.setPrefHeight(15);
            board.setPrefWidth(this.room.getPrefWidth()/2);
            board.setLayoutX(this.room.getPrefWidth()/4);
            board.setLayoutY(this.room.getPrefHeight()-15);
        }
        else if(this.classMapLayer.getRoom().getBoardOrientation()== boardOrientation.east){
            board.setPrefHeight(this.room.getPrefHeight()/2);
            board.setPrefWidth(15);
            board.setLayoutX(this.room.getPrefWidth()-15);
            board.setLayoutY(this.room.getPrefHeight()/4);
        }
        else if(this.classMapLayer.getRoom().getBoardOrientation()== boardOrientation.west){
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
        System.out.println(roomWidthRatio);
        System.out.println(roomHeightRatio);
        //Draw the desks inside the mapEditor
        ArrayList<Desk> desks = this.classMapLayer.getDesks();
        for(Desk desk : desks){
            if(desk.getType().equals("mono")){
                Pane deskBox = new Pane();
                deskBox.setPrefWidth(desk.getWidth()*roomWidthRatio);
                deskBox.setPrefHeight(desk.getHeight()*roomHeightRatio);
                deskBox.setLayoutX(desk.getX()*roomWidthRatio);
                deskBox.setLayoutY(desk.getY()*roomHeightRatio);
                deskBox.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                addDropEvent(deskBox);
                this.room.getChildren().add(deskBox);
            }
            else if(desk.getType().equals("duo")){
                if(desk.getRoom().getBoardOrientation() == dataTypes.classMap.object.boardOrientation.north || desk.getRoom().getBoardOrientation() == dataTypes.classMap.object.boardOrientation.south){
                    Pane deskBox1 = new Pane();
                    deskBox1.setPrefWidth(desk.getWidth()*roomWidthRatio/2);
                    deskBox1.setPrefHeight(desk.getHeight()*roomHeightRatio);
                    deskBox1.setLayoutX(desk.getX()*roomWidthRatio);
                    deskBox1.setLayoutY(desk.getY()*roomHeightRatio);
                    deskBox1.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                    Pane deskBox2 = new Pane();
                    deskBox2.setPrefWidth(desk.getWidth()*roomWidthRatio/2);
                    deskBox2.setPrefHeight(desk.getHeight()*roomHeightRatio);
                    deskBox2.setLayoutX(desk.getX()*roomWidthRatio+desk.getWidth()*roomWidthRatio/2);
                    deskBox2.setLayoutY(desk.getY()*roomHeightRatio);
                    deskBox2.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                    addDropEvent(deskBox1);
                    addDropEvent(deskBox2);
                    this.room.getChildren().addAll(deskBox1,deskBox2);
                }
            }
        }
    }
    public void addDropEvent(Pane deskBox){
        //autorize drag and drop from different elements
        deskBox.setOnDragEntered(event -> {
            System.out.println("Entered detected on : " + deskBox.getLayoutX());
            System.out.println("Entered detected on : " + deskBox.getLayoutY());
            event.consume();
        });
        deskBox.setOnDragExited(event -> {
            System.out.println("Exited detected on : " + deskBox.getLayoutX());
            System.out.println("Exited detected on : " + deskBox.getLayoutY());
            event.consume();
        });
        deskBox.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });
        deskBox.setOnDragDropped(event -> {
            Student student = listView.getSelectionModel().getSelectedItem();
            //delete the student from the list
            listView.getItems().remove(student);
            //add the student to the desk
            deskBox.getChildren().add(new Button(student.getSurname()));
            event.setDropCompleted(true);
            event.consume();
        });
    }
    public void diplayStudentList(){
        HBox container = this.window.getMiddlePanel();
        this.listView=new ListView<>();
        this.splitView.getItems().addAll(listView,this.mapEditor);
        listView.getItems().addAll(this.studentList);
        container.getChildren().add(splitView);
        listView.setMaxWidth(this.screenBounds.getWidth()/3);
        //add a mouse event for each student
        listView.setOnMouseClicked(event -> {
            Student student = listView.getSelectionModel().getSelectedItem();
            if (student != null) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    System.out.println("Double click on : " + student.getSurname());
                }
            }
        });
        listView.setOnDragDetected(event -> {
            Student student = listView.getSelectionModel().getSelectedItem();
            if (student != null) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = listView.startDragAndDrop(TransferMode.ANY);
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                db.setContent(content);
            }
            event.consume();
        });
    }

    public Scene getScene(){
        return this.window.getScene();
    }
}
