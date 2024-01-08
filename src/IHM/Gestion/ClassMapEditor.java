package IHM.Gestion;

import dataTypes.actors.Student;
import dataTypes.classMap.ClassMap;
import dataTypes.classMap.ClassMapLayer;
import dataTypes.classMap.object.Desk;
import dataTypes.classMap.object.BoardOrientation;
import dataTypes.classMap.object.DeskOrientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import IHM.Window.Window;

import java.util.ArrayList;

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
    private ListView<Desk> deskView;
    private ListView<ClassMapLayer> predifinedClassMap;
    private Gestion gestion;
    private Button saveButton;
    private Button cancelButton;
    private Button backButton;
    public ClassMapEditor(ClassMap ClassMap,ClassMapLayer classMapLayer, Rectangle2D screenBounds, Stage stage,Gestion gestion){
        this.gestion = gestion;
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
    public void displayBotButtons(){
        this.saveButton = new Button("Save");
        this.cancelButton = new Button("Cancel");
        this.backButton = new Button("Back");
        this.backButton.setOnAction(event -> {
            this.stage.setScene(this.gestion.getScene());
        });
        this.window.getBotPanel().getChildren().addAll(this.saveButton,this.cancelButton,this.backButton);
    }
    public void display(){
        diplayTabPane();
        displayRoom();
        displayDesk();
        displayBoard();
        displayBotButtons();
        setDragDeskEvent();
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
                Pane deskBox = new Pane();
                deskBox.setPrefWidth(desk.getWidth()*roomWidthRatio);
                deskBox.setPrefHeight(desk.getHeight()*roomHeightRatio);
                deskBox.setLayoutX(desk.getX()*roomWidthRatio);
                deskBox.setLayoutY(desk.getY()*roomHeightRatio);
                deskBox.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                addDropEvent(deskBox,desk);
                this.room.getChildren().add(deskBox);
            }
            else if(desk.getType().equals("duo")){
                if(desk.getOrientation() == DeskOrientation.horizontal){
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
                    addDropEvent(deskBox1,desk);
                    addDropEvent(deskBox2,desk);
                    this.room.getChildren().addAll(deskBox1,deskBox2);
                }else{
                    Pane deskBox1 = new Pane();
                    deskBox1.setPrefWidth(desk.getWidth()*roomWidthRatio);
                    deskBox1.setPrefHeight(desk.getHeight()*roomHeightRatio/2);
                    deskBox1.setLayoutX(desk.getX()*roomWidthRatio);
                    deskBox1.setLayoutY(desk.getY()*roomHeightRatio);
                    deskBox1.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                    Pane deskBox2 = new Pane();
                    deskBox2.setPrefWidth(desk.getWidth()*roomWidthRatio);
                    deskBox2.setPrefHeight(desk.getHeight()*roomHeightRatio/2);
                    deskBox2.setLayoutX(desk.getX()*roomWidthRatio);
                    deskBox2.setLayoutY(desk.getY()*roomHeightRatio+desk.getHeight()*roomHeightRatio/2);
                    deskBox2.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                    addDropEvent(deskBox1,desk);
                    addDropEvent(deskBox2,desk);
                    this.room.getChildren().addAll(deskBox1,deskBox2);
                }
            }
        }
    }
    public void addDropEvent(Pane deskBox,Desk desk){
        //autorize drag and drop from different elements
        Text tempStudentName = new Text();
        deskBox.setOnDragEntered(event -> {
            Student student = listView.getSelectionModel().getSelectedItem();
            if (student != null) {
                tempStudentName.setText(student.getName());
                tempStudentName.setLayoutX(deskBox.getPrefWidth()/2-tempStudentName.getLayoutBounds().getWidth()/2);
                tempStudentName.setLayoutY(deskBox.getPrefHeight()/2-tempStudentName.getLayoutBounds().getHeight()/2);
                tempStudentName.setStyle("-fx-fill: rgba(255,255,255,0.46);");
                deskBox.getChildren().add(tempStudentName);
            }
            event.consume();
        });
        deskBox.setOnDragExited(event -> {
            deskBox.getChildren().remove(tempStudentName);
            event.consume();
        });
        deskBox.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.COPY);
            event.consume();
        });
        deskBox.setOnDragDropped(event -> {
            Student student = listView.getSelectionModel().getSelectedItem();
            //delete the student from the list
            listView.getItems().remove(student);
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
            desk.setStudent(student);
            event.setDropCompleted(true);
            event.consume();
        });
    }
    public void diplayTabPane(){
        HBox container = this.window.getMiddlePanel();
        TabPane tabPane = new TabPane();
        Tab studentPane = new Tab("Student");
        tabPane.getTabs().add(studentPane);
        Tab deskPane = new Tab("Desk");
        tabPane.getTabs().add(deskPane);
        Tab templatePane = new Tab("Template");
        tabPane.getTabs().add(templatePane);
        //Add the list view in the tab
        this.listView=new ListView<>();
        this.splitView.getItems().addAll(tabPane,this.mapEditor);
        listView.getItems().addAll(this.studentList);
        this.deskView=new ListView<>();
        this.deskView.getItems().addAll(dataTypes.classMap.object.Desk.getDeskTypeList());
        this.predifinedClassMap=new ListView<>();
        deskPane.setContent(deskView);
        studentPane.setContent(listView);
        templatePane.setContent(predifinedClassMap);
        container.getChildren().add(splitView);
        tabPane.setMaxWidth(this.screenBounds.getWidth()/5);
        //Cant close the tab
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
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
                Dragboard db = listView.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                db.setContent(content);
            }
            event.consume();
        });
        deskView.setOnMouseClicked(event -> {
            Desk desk = deskView.getSelectionModel().getSelectedItem();
            if (desk != null) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    System.out.println("Double click on : " + desk);
                }
            }
        });
        deskView.setOnDragDetected(event -> {
            Desk desk = deskView.getSelectionModel().getSelectedItem();
            if (desk != null) {
                Dragboard db = deskView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString("desk");
                db.setContent(content);
            }
            event.consume();
        });

    }
    private void setDragDeskEvent(){
        this.room.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });
        this.room.setOnDragDropped(dragEvent -> {
            Desk desk = deskView.getSelectionModel().getSelectedItem();
            if (desk != null) {
                if (dragEvent.getDragboard().getString().equals("desk")) {
                    double roomWidth = this.room.getPrefWidth();
                    double roomHeight = this.room.getPrefHeight();
                    double roomWidthRatio = roomWidth / this.classMapLayer.getRoom().getWidth();
                    double roomHeightRatio = roomHeight / this.classMapLayer.getRoom().getHeight();
                    double x = dragEvent.getX();
                    double y = dragEvent.getY();
                    if(desk.getType().equals("mono")){
                        Pane deskBox = new Pane();
                        deskBox.setPrefWidth(desk.getWidth()*roomWidthRatio);
                        deskBox.setPrefHeight(desk.getHeight()*roomHeightRatio);
                        deskBox.setLayoutX(x);
                        deskBox.setLayoutY(y);
                        deskBox.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                        addDropEvent(deskBox,desk);
                        this.room.getChildren().add(deskBox);
                    }
                    else if(desk.getType().equals("duo")){
                        if(desk.getOrientation() == DeskOrientation.horizontal){
                            Pane deskBox1 = new Pane();
                            deskBox1.setPrefWidth(desk.getWidth()*roomWidthRatio/2);
                            deskBox1.setPrefHeight(desk.getHeight()*roomHeightRatio);
                            deskBox1.setLayoutX(x);
                            deskBox1.setLayoutY(y);
                            deskBox1.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                            Pane deskBox2 = new Pane();
                            deskBox2.setPrefWidth(desk.getWidth()*roomWidthRatio/2);
                            deskBox2.setPrefHeight(desk.getHeight()*roomHeightRatio);
                            deskBox2.setLayoutX(x+desk.getWidth()*roomWidthRatio/2);
                            deskBox2.setLayoutY(y);
                            deskBox2.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                            addDropEvent(deskBox1,desk);
                            addDropEvent(deskBox2,desk);
                            this.room.getChildren().addAll(deskBox1,deskBox2);
                        }else{
                            Pane deskBox1 = new Pane();
                            deskBox1.setPrefWidth(desk.getWidth()*roomWidthRatio);
                            deskBox1.setPrefHeight(desk.getHeight()*roomHeightRatio/2);
                            deskBox1.setLayoutX(x);
                            deskBox1.setLayoutY(y);
                            deskBox1.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                            Pane deskBox2 = new Pane();
                            deskBox2.setPrefWidth(desk.getWidth()*roomWidthRatio);
                            deskBox2.setPrefHeight(desk.getHeight()*roomHeightRatio/2);
                            deskBox2.setLayoutX(x);
                            deskBox2.setLayoutY(y+desk.getHeight()*roomHeightRatio/2);
                            deskBox2.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
                            addDropEvent(deskBox1,desk);
                            addDropEvent(deskBox2,desk);
                            this.room.getChildren().addAll(deskBox1,deskBox2);
                        }
                    }
                }
            }
        });
    }
    public Scene getScene(){
        return this.window.getScene();
    }
}
