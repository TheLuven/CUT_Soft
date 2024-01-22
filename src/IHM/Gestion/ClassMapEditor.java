package IHM.Gestion;
/************************************************************
 * @author Victor VENULETH
 * @modifier Yohan JAFFRE
 * @version 1.0.0
 * @date 2024/01/02
 ***********************************************************/
import dataTypes.actors.Student;
import dataTypes.classMap.ClassMap;
import dataTypes.classMap.ClassMapLayer;
import dataTypes.classMap.object.*;
import database.DatabaseManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import IHM.Window.Window;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Victor VENULETH
 * @brief This class is used to the editor used to modify the class map
 */
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
    private Button sendButton;
    private Button backButton;
    private String draftName;
    private Button deleteButton;
    private DatabaseManager dbManager;
    /**
     * @author Victor VENULETH
     * @brief Constructor of the class ClassMapEditor
     * @param classMap The class map to edit
     * @param classMapLayer The class map layer of the classMap
     * @param screenBounds The screen bounds
     * @param stage The current stage
     * @param gestion Current gestion page
     * @param dbManager The database manager
     */
    public ClassMapEditor(ClassMap classMap, ClassMapLayer classMapLayer, Rectangle2D screenBounds, Stage stage, Gestion gestion, DatabaseManager dbManager){
        this.gestion = gestion;
        this.classMapLayer = classMapLayer;
        this.screenBounds = screenBounds;
        this.splitView = new SplitPane();
        this.draftName = classMapLayer.getName();
        this.stage = stage;
        this.window = new Window();
        //window.getScene().getStylesheets().add("style.css");
        this.classMap = classMap;
        this.studentList = classMap.getaClass().getStudents();
        this.mapEditor = new Pane();
        this.room = new Pane();
        this.dbManager = dbManager;
        this.deleteButton = new Button("X");
        //Reduce font size and change color to white
        this.deleteButton.setStyle("-fx-font-size: 8px;-fx-text-fill: #ffffff;");
        this.deleteButton.setStyle("-fx-background-color: #ff0000;");
        display();
        window.setTitle("Class Map Editor - "+classMap.getaClass().getClassName()+" - "+classMap.getSubject().getSubjectName()+" - "+classMap.getTeacher().getSurname()+" "+classMap.getTeacher().getName());
    }
    /**
     * @author Victor VENULETH
     * @brief Display all the buttons at the bottom of the page
     */
    private void displayBotButtons(){
        this.saveButton = new Button("Save");
        this.sendButton = new Button("Send to the Server");
        this.addSendButtonAction();
        Region region = new Region();
        HBox.setHgrow(region, javafx.scene.layout.Priority.ALWAYS);
        this.backButton = new Button("Back");
        this.backButton.setOnAction(event -> {
            this.stage.setScene(this.gestion.getScene());
            this.gestion.reload();
        });
        this.addSaveButtonAction();
        //DIsable send to server button
        this.sendButton.setDisable(true);
        this.window.getBotPanel().getChildren().addAll(this.saveButton,this.backButton,region,this.sendButton);
    }
    /**
     * @author Victor VENULETH
     * @brief Add the action to the send button
     */
    private void addSendButtonAction(){
        this.sendButton.setOnAction(actionEvent -> {
            double roomWidth = this.classMapLayer.getRoom().getWidth();
            double roomHeight = this.classMapLayer.getRoom().getHeight();
            BoardOrientation boardOrientation = this.classMapLayer.getRoom().getBoardOrientation();
            String roomName = this.draftName;
            int classId = this.classMap.getaClass().getClassId();
            int subjectId = this.classMap.getSubject().getSubjectId();
            dbManager.deleteAllCoordinateOfASubject(subjectId);
            try {
                dbManager.updateAClassSubject(classId,subjectId,roomWidth,roomHeight,roomName,"online",boardOrientation.toString());
            }catch (Exception e){
                dbManager.updateClassStatus(classId,subjectId,"undefined");
            }
            try {
                for (Desk desk : this.classMapLayer.getDesks()){
                    if (desk.getStudent()!=null){
                        dbManager.setACoordinate(desk.getX(),desk.getY(),subjectId,desk.getOrientation().toString(),desk.getStudent().getId());
                    }else{
                        dbManager.setACoordinate(desk.getX(),desk.getY(),subjectId,desk.getOrientation().toString());
                    }
                }
            }catch (Exception e){
                dbManager.updateClassStatus(classId, subjectId,"undefined");
            }
            this.gestion.reload();
            this.stage.setScene(this.gestion.getScene());
        });
    }
    /**
     * @author Victor VENULETH
     * @brief Add the action to the save button
     */
    private void addSaveButtonAction(){
        this.saveButton.setOnAction(event -> {
            JSONObject draft = new JSONObject();
            double roomWidth = this.classMapLayer.getRoom().getWidth();
            double roomHeight = this.classMapLayer.getRoom().getHeight();
            BoardOrientation boardOrientation = this.classMapLayer.getRoom().getBoardOrientation();
            draft.put("name",this.draftName);
            draft.put("roomWidth",roomWidth);
            draft.put("roomHeight",roomHeight);
            draft.put("boardOrientation",boardOrientation.toString());
            //Get desks
            ArrayList<JSONObject> desks = new ArrayList<>();
            for (Desk desk : this.classMapLayer.getDesks()){
                JSONObject deskJSON = new JSONObject();
                deskJSON.put("orientation",desk.getOrientation().toString());
                deskJSON.put("x",desk.getX());
                deskJSON.put("y",desk.getY());
                if (desk.getStudent()!=null){
                    deskJSON.put("student_id",desk.getStudent().getId());
                }else{
                    deskJSON.put("student_id",null);
                }
                desks.add(deskJSON);
            }
            draft.put("desks",desks);
            //Save the JSON object in a local JSON file
            try {
                String classMapName = this.classMap.getaClass().getClassName();
                String subjectName = this.classMap.getSubject().getSubjectName();
                //Create the directory if it doesn't exist
                java.io.File directory = new java.io.File("Drafts/"+classMapName+"/"+subjectName);
                directory.mkdirs();

                //Create the file
                FileWriter JSONFile = new FileWriter("Drafts/"+classMapName+"/"+subjectName+"/"+this.draftName+".json");
                JSONFile.write(draft.toJSONString());
                System.out.println("Successfully Copied JSON Object to File...");
                System.out.println("\nJSON Object: " + draft);
                JSONFile.flush();
                JSONFile.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.gestion.reload();
            this.stage.setScene(this.gestion.getScene());
        });
    }
    /**
     * @author Victor VENULETH
     * @brief Display everything in the editor (The order is important)
     */
    private void display(){
        displayTabPane();
        displayRoom();
        displayDesk();
        displayBoard();
        displayBotButtons();
        setDragDeskEvent();
    }
    /**
     * @author Victor VENULETH
     * @brief Display the room inside the mapEditor
     */
    private void displayRoom(){
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

        this.room.setOnMousePressed(event -> {
            //Disable the mouvement of the room if the mouse is on a desk
            if (event.getTarget().equals(this.room)){
                room.setCursor(javafx.scene.Cursor.CLOSED_HAND);
                room.setMouseTransparent(true);
            }
        });
        this.room.setOnMouseReleased(event -> {
            if (event.getTarget().equals(this.room)) {
                room.setCursor(javafx.scene.Cursor.DEFAULT);
                room.setMouseTransparent(false);
                lastX = 0;
                lastY = 0;
            }
        });
        this.room.setOnMouseDragged(event -> {
            if (event.getTarget().equals(this.room)){
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
            }
        });
        this.mapEditor.setOnMousePressed(event -> {
            if (event.getTarget().equals(this.mapEditor)) {
                room.setCursor(javafx.scene.Cursor.CLOSED_HAND);
                room.setMouseTransparent(true);
            }
        });
        this.mapEditor.setOnMouseReleased(event -> {
            if (event.getTarget().equals(this.mapEditor)) {
                room.setCursor(javafx.scene.Cursor.DEFAULT);
                room.setMouseTransparent(false);
                lastX = 0;
                lastY = 0;
            }
        });
        this.mapEditor.setOnMouseDragged(event -> {
            if (event.getTarget().equals(this.mapEditor)) {
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
            }
        });
        this.mapEditor.getChildren().add(this.room);
    }

    /**
     * @author Victor VENULETH
     * @brief Display the board inside the room
     */
    private void displayBoard(){
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
    /**
     * @author Victor VENULETH
     * @brief Display every desk of the class
     */
    private void displayDesk(){
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
            if (desk.getStudent()!=null){
                this.listView.getItems().remove(desk.getStudent());
            }
        }
    }
    /**
     * @author Victor VENULETH
     * @brief Add a drop event to a deskbox, it will react to a drop of a student
     */
    private void addDropEvent(Pane deskBox,Desk desk){
        //Allow drag and drop from different elements
        Text tempStudentName = new Text();
        deskBox.setOnDragEntered(event -> {
            Student student = null;
            if(event.getGestureSource() == listView){
                student = listView.getSelectionModel().getSelectedItem();
            }else if (event.getGestureSource() != deskView){
                //get the student from the dragboard
                Dragboard db = event.getDragboard();
                String studentIndex = db.getString();
                student = studentList.get(Integer.parseInt(studentIndex));
            }else{
                return;
            }
            if (student != null && desk.getStudent() == null) {
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
            if (event.getGestureSource()!=deskView){
                event.acceptTransferModes(TransferMode.COPY);
                event.consume();
            }
        });

        deskBox.setOnDragDropped(event -> {
            if (event.getGestureSource()==deskView){return;}
            if (desk.getStudent() == null && event.getGestureSource()==listView) {
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
                //event.setDropCompleted(true);
            }
            else if (desk.getStudent() == null){
                Dragboard db = event.getDragboard();
                String studentIndex = db.getString();
                Student student = studentList.get(Integer.parseInt(studentIndex));
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

            }
            if(this.listView.getItems().isEmpty()){
                this.sendButton.setDisable(false);
            }
            event.consume();
        });
    }
    /**
     * @author Victor VENULETH
     * @brief Add a Drag event to a deskbox, it will react to a drag of a student
     */
    private void addDragEvent(Desk desk,Pane deskBox){
        deskBox.setOnDragDetected(event -> {
            if (desk.getStudent() != null) {
                Dragboard db = deskBox.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                Student student = desk.getStudent();
                int studentIndex = studentList.indexOf(student);
                content.putString(String.valueOf(studentIndex));
                db.setContent(content);
            }
            event.consume();
        });

        deskBox.setOnDragDone(event -> {
            if (event.getGestureSource()==deskBox && listView.getItems().contains(desk.getStudent())) {
                deskBox.getChildren().clear();
                desk.setStudent(null);
            }
            if (event.getGestureSource()==deskBox && !event.getGestureTarget().equals(this.room) && !event.getGestureTarget().equals(this.mapEditor)) {
                deskBox.getChildren().clear();
                desk.setStudent(null);
            }
            event.consume();
        });
    }
    /**
     * @author Victor VENULETH
     * @brief Display the left tab pane with 3 tabs (Student, Desk, Template)
     */
    private void displayTabPane(){
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
        ClassTemplate classTemplate = new ClassTemplate();
        this.predifinedClassMap.getItems().addAll(classTemplate.getClassMapLayers());
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
        //Add drop of a student from a deskbox to put the student back on the list
        listView.setOnDragOver(event -> {
            if (event.getGestureSource()!=listView){
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        listView.setOnDragDropped(dragEvent -> {
            if(dragEvent.getGestureSource()!=listView){
                //get the student from the dragboard
                Dragboard db = dragEvent.getDragboard();
                String studentIndex = db.getString();
                Student student = studentList.get(Integer.parseInt(studentIndex));
                listView.getItems().add(student);
                dragEvent.setDropCompleted(true);
                this.sendButton.setDisable(true);
            };
            dragEvent.consume();
        });
        deskView.setOnMouseClicked(event -> {
            Desk desk = deskView.getSelectionModel().getSelectedItem();
            if (desk != null) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    System.out.println("Double click on : " + desk);
                }
            }
        });
        predifinedClassMap.setOnMouseClicked(event -> {
            ClassMapLayer template = predifinedClassMap.getSelectionModel().getSelectedItem();
            if (template != null) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    System.out.println("Double click on : " + template);
                }
                this.classMapLayer = template;
                this.room.getChildren().clear();
                this.mapEditor.getChildren().remove(this.room);
                this.classMapLayer.parseDesks();
                displayRoom();
                displayDesk();
                displayBoard();
                refillListView();
            }
        });
        deskView.setOnDragDetected(event -> {
            Desk desk = deskView.getSelectionModel().getSelectedItem();
            if (desk != null) {
                Dragboard db = deskView.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putString("desk");
                db.setContent(content);
            }
            event.consume();
        });
    }
    /**
     * @author Victor VENULETH
     * @brief Refill the list view with the student list
     */
    private void refillListView(){
        this.listView.getItems().clear();
        this.listView.getItems().addAll(this.studentList);
        this.sendButton.setDisable(true);
    }
    /**
     * @author Victor VENULETH
     * @brief Add a drop event in the room to add a desk
     */
    private void setDragDeskEvent(){
        this.room.setOnDragOver(event -> {
            if (event.getGestureSource()==deskView) {
                event.acceptTransferModes(TransferMode.COPY);
                event.consume();
            }
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
                        Desk deskToAdd = new Desk(x/roomWidthRatio,y/roomHeightRatio,"mono",desk.getOrientation());
                        drawMonoDesk(desk,roomWidthRatio,roomHeightRatio,x,y);
                        this.classMapLayer.addDesk(deskToAdd);
                    }
                    else if(desk.getType().equals("duo")){
                        Desk desk1;
                        Desk desk2;
                        if (desk.getOrientation() == DeskOrientation.vertical){
                            desk1 = new Desk(desk.getX(),desk.getY(),"mono",desk.getOrientation());
                            desk2 = new Desk(desk.getX(),desk.getY()+desk.getHeight()/2,"mono",desk.getOrientation());
                        }else{
                            desk1 = new Desk(desk.getX(),desk.getY(),"mono",desk.getOrientation());
                            desk2 = new Desk(desk.getX()+desk.getWidth()/2,desk.getY(),"mono",desk.getOrientation());
                        }
                        drawMonoDesk(desk1,roomWidthRatio,roomHeightRatio,x,y);
                        drawMonoDesk(desk2,roomWidthRatio,roomHeightRatio,x,y);
                        if (desk.getOrientation() == DeskOrientation.vertical){
                            desk1.setX(x/roomWidthRatio);
                            desk1.setY(y/roomHeightRatio);
                            desk2.setX(x/roomWidthRatio);
                            desk2.setY((y)/roomHeightRatio+desk.getHeight()/2);
                        }else{
                            desk1.setX(x/roomWidthRatio);
                            desk1.setY(y/roomHeightRatio);
                            desk2.setX((x)/roomWidthRatio+desk.getWidth()/2);
                            desk2.setY(y/roomHeightRatio);
                        }
                        this.classMapLayer.addDesk(desk1);
                        this.classMapLayer.addDesk(desk2);
                    }
                    System.out.println(this.classMapLayer.getDesks());
                    System.out.println(this.classMapLayer.getDesks().size());
                }
            }
        });
    }
    /**
     * @author Victor VENULETH
     * @brief Get the current scene of the class map editor
     * @return Scene The current scene
     */
    public Scene getScene(){
        return this.window.getScene();
    }
    /**
     * @author Victor VENULETH
     * @param desk The desk
     * @param deskBox The deskbox
     * @brief Add a mouse event to the deskbox to delete the desk
     */
    private void addMouseOverEvent(Pane deskBox, Desk desk){
        deskBox.setOnMouseEntered(event -> {
                deskBox.getChildren().add(this.deleteButton);
                //Delete button is 1/5 of the width of the deskbox
                this.deleteButton.setMaxWidth(10);
                this.deleteButton.setMaxHeight(10);
                this.deleteButton.setOnAction(event1 -> {
                    this.classMapLayer.removeDesk(desk);
                    this.room.getChildren().remove(deskBox);
                    if (desk.getStudent()!=null){
                        this.listView.getItems().add(desk.getStudent());
                        this.sendButton.setDisable(true);
                    }
                    System.out.println(this.classMapLayer.getDesks());
                    System.out.println(this.classMapLayer.getDesks().size());
                });
        });
        deskBox.setOnMouseExited(event -> {
            if (deskBox.getChildren().contains(this.deleteButton)){
                deskBox.getChildren().remove(this.deleteButton);
            }
        });
    }
    /**
     * @author Victor VENULETH
     * @brief To draw a mono desk
     * @param desk The desk to draw
     * @param roomWidthRatio The ratio of the room width
     * @param roomHeightRatio The ratio of the room height
     * @param x The x position of the desk
     * @param y The y position of the desk
     */
    private void drawMonoDesk(Desk desk,double roomWidthRatio,double roomHeightRatio,double x,double y){
        Pane deskBox = new Pane();
        deskBox.setPrefWidth(desk.getWidth()*roomWidthRatio);
        deskBox.setPrefHeight(desk.getHeight()*roomHeightRatio);
        deskBox.setLayoutX(desk.getX()*roomWidthRatio+x);
        deskBox.setLayoutY(desk.getY()*roomWidthRatio+y);
        deskBox.setStyle("-fx-background-color: #525a69;-fx-border-color: #ffffff;");
        addDropEvent(deskBox,desk);
        addDragEvent(desk,deskBox);
        addMouseOverEvent(deskBox,desk);
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
