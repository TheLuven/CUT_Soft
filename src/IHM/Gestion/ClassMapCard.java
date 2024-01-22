package IHM.Gestion;

import dataTypes.classMap.ClassMap;
import dataTypes.classMap.ClassMapLayer;
import dataTypes.classMap.object.BoardOrientation;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
/**
 * @Author Victor VENULETH
 * @Version 1.0
 * @Date 02/01/2024
 * @brief This class is used to display a card for each classMap
 */
public class ClassMapCard {
    private ClassMap classMap;
    private VBox card;
    private ArrayList<ClassMapLayer> drafts;
    private ClassMapLayer currentClassMap;
    private Stage mainStage;
    private Gestion gestion;
    public ClassMapCard(ClassMap classMap, Stage stage,Gestion gestion){
        this.gestion = gestion;
        this.classMap = classMap;
        this.card = new VBox();
        this.drafts = classMap.drafts;
        reloadCurrentClassMap();
        this.mainStage=stage;
        cardGenerator();
    }
    public void setCurrentClassMap(){
        int classId = this.classMap.getaClass().getClassId();
        int subjectId = this.classMap.getSubject().getSubjectId();
        if (this.gestion.getDataBaseManager().getClassMapStatus(classId,subjectId).equals("online")) {
            this.classMap.setStatus("online");
            this.classMap.setCurrentClassMap(this.gestion.getDataBaseManager().getCurrentClassMap(classId,subjectId));
        }else{
            this.classMap.setStatus("undefined");
        }
    }
    public void reloadCurrentClassMap(){
        setCurrentClassMap();
        this.currentClassMap = classMap.currentClassMap;
    }

    /**
     * @brief This method is used to generate the card of the classMap, with every necessary information in it
     * @author Victor VENULETH
     */
    public void cardGenerator(){
        //Create text Node
        Text className = new Text("Class Name : "+classMap.getaClass().getClassName());
        Text subjectName = new Text("Subject Name : "+classMap.getSubject().getSubjectName());
        Text studentNumber = new Text("Student number : "+classMap.getaClass().getStudents().size());
        Text classMapStatus = new Text("Class map Status :  "+classMap.getStatus());
        className.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        subjectName.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        studentNumber.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        classMapStatus.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        className.setFill(Color.WHITE);
        subjectName.setFill(Color.WHITE);
        studentNumber.setFill(Color.WHITE);
        classMapStatus.setFill(Color.WHITE);

        //Create combobox node
        ComboBox comboBox = new ComboBox<>();
        for(ClassMapLayer s : drafts){
            comboBox.getItems().add(s.getName());
        }
        //Select the first item by default
        comboBox.getSelectionModel().selectFirst();
        Button editDraft = new Button("Edit Draft");
        Button createNewDraft = new Button("Create a new Draft");
        Button checkCurrentMap = new Button("Visualize current Class Map");
        this.card.getChildren().addAll(className,subjectName,studentNumber,classMapStatus,comboBox,editDraft,createNewDraft,checkCurrentMap);
        this.card.setStyle("-fx-border-color: #ffffff; -fx-background-color: #1c1c1e;");
        this.card.setPadding(new Insets(10,10,10,10));

        //deactivate the editDraft button if the comboBox isn't set to a value
        editDraft.disableProperty().bind(comboBox.valueProperty().isNull());

        //onclick on the createNewDraft button : open a popup where you can enter the name of a new draft and confirm the creation with a button
        createNewDraft.setOnAction(event -> {
            VBox content = new VBox();
            HBox topBar = new HBox();
            VBox vBox = new VBox();
            vBox.setStyle("-fx-border-color: #ffffff; -fx-background-color: #1c1c1e;");
            content.setStyle("-fx-border-color: #ffffff;");
            topBar.setStyle("-fx-border-color: #ffffff;");
            content.setPadding(new Insets(10,10,10,10));
            content.setSpacing(5);
            Popup popup = new Popup();
            Popup blur = new Popup();
            Pane pane = new Pane();
            //Set blur size to screen size
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            pane.setPrefWidth(screenBounds.getWidth());
            pane.setPrefHeight(screenBounds.getHeight());
            //Change the opacity of blur to 50%
            pane.setOpacity(0.7);
            pane.setStyle("-fx-background-color: #000000;");
            blur.getContent().add(pane);
            Slider classWidth = new Slider(5,50,6);
            Slider classHeigth = new Slider(5,50,10);
            classWidth.setShowTickLabels(true);
            classHeigth.setShowTickLabels(true);
            ChoiceBox classOrientation = new ChoiceBox();
            classOrientation.getItems().addAll("NORTH","EAST","SOUTH","WEST");
            classOrientation.getSelectionModel().selectFirst();
            TextArea textArea = new TextArea("Draft name");
            Button confirmButton = new Button("Confirm");
            //The text area cant exceed 20 characters and delete \n
            textArea.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 20) {
                    textArea.setText(oldValue);
                }
                if(newValue.contains("\n")){
                    textArea.setText(newValue.replace("\n",""));
                }
            });
            textArea.setPrefHeight(12);
            textArea.setPrefWidth(200);
            //There can only be one line on the text area
            textArea.setWrapText(true);
            textArea.setPrefColumnCount(1);
            textArea.setPrefRowCount(1);
            textArea.disableProperty().set(false);
            confirmButton.disableProperty().set(true);
            textArea.setStyle("-fx-border-color: red;");
            confirmButton.setOnAction(event1 -> {
                if(!textArea.getText().equals("Draft name") && !textArea.getText().equals("") && !classOrientation.getSelectionModel().isEmpty()){
                    BoardOrientation orientation;
                    if (classOrientation.getSelectionModel().getSelectedItem().equals("NORTH")){
                        orientation =  BoardOrientation.north;
                    }else if (classOrientation.getSelectionModel().getSelectedItem().equals("EAST")){
                        orientation =  BoardOrientation.east;
                    }else if (classOrientation.getSelectionModel().getSelectedItem().equals("SOUTH")){
                        orientation =  BoardOrientation.south;
                    }else{
                        orientation =  BoardOrientation.west;
                    }
                    ClassMapLayer draft = new ClassMapLayer(textArea.getText(),classWidth.getValue(),classHeigth.getValue(),orientation);
                    ClassMapEditor classMapEditor = new ClassMapEditor(this.classMap,draft, Screen.getPrimary().getVisualBounds(),this.mainStage,this.gestion,this.gestion.getDataBaseManager());
                    this.mainStage.setScene(classMapEditor.getScene());
                    popup.hide();
                    blur.hide();
                }
            });
            //Delete the default text when the user click on the text area
            textArea.setOnMouseClicked(event1 -> {
                if(textArea.getText().equals("Draft name")){
                    textArea.setText("");
                }
            });
            //Warn the user if the given name exist or is empty and desactivate the confirm button
            textArea.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.equals("") || newValue.equals("Draft name")){
                    textArea.setStyle("-fx-border-color: red;");
                    confirmButton.disableProperty().set(true);
                }else if(!classOrientation.getSelectionModel().isEmpty()){
                    textArea.setStyle("-fx-border-color: #ffffff;");
                    confirmButton.disableProperty().set(false);
                }else{
                    textArea.setStyle("-fx-border-color: #ffffff;");
                }
                for(ClassMapLayer s : drafts){
                    if(s.getName().equals(newValue)){
                        textArea.setStyle("-fx-border-color: red;");
                        confirmButton.disableProperty().set(true);
                    }
                }
            });
            //Enable button if the user choose an orientation
            classOrientation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if(!textArea.getText().equals("Draft name") && !textArea.getText().equals("")){
                    textArea.setStyle("-fx-border-color: #ffffff;");
                    confirmButton.disableProperty().set(false);
                }
            });
            Button closeButton = new Button("X");
            closeButton.setOnAction(event1 -> {
                popup.hide();
                blur.hide();
            });
            //If the user press escape close both popup and blur
            blur.setOnHiding(event1 -> {
                popup.hide();
            });
            Text title = new Text("Create a new draft");
            title.setFill(Color.WHITE);
            Text class_width = new Text("Choose the class width : "+classWidth.getValue()+"m");
            class_width.setFill(Color.WHITE);
            Text class_height = new Text("Choose the class height : "+classHeigth.getValue()+"m");
            class_height.setFill(Color.WHITE);
            Text class_orientation = new Text("Choose the class board placement :");
            class_orientation.setFill(Color.WHITE);
            classWidth.valueProperty().addListener((observable, oldValue, newValue) -> {
                class_width.setText("Choose the class width : "+newValue.intValue()+"m");
            });
            classHeigth.valueProperty().addListener((observable, oldValue, newValue) -> {
                class_height.setText("Choose the class height : "+newValue.intValue()+"m");
            });
            topBar.getChildren().addAll(closeButton,title);
            vBox.getChildren().addAll(topBar,content);
            content.getChildren().addAll(textArea,class_width,classWidth,class_height,classHeigth,class_orientation,classOrientation,confirmButton);
            popup.getContent().addAll(vBox);
            popup.setX(screenBounds.getWidth()/2-popup.getWidth()/2);
            popup.setY(screenBounds.getHeight()/2-100);
            blur.show(this.mainStage);
            popup.show(this.mainStage);
        });

        //onclick on the editDraft button : open the draft selected in the comboBox
        editDraft.setOnAction(event -> {
            //get selected draft and open it
            ClassMapLayer draft = drafts.get(comboBox.getSelectionModel().getSelectedIndex());
            ClassMapEditor classMapEditor = new ClassMapEditor(this.classMap,draft, Screen.getPrimary().getVisualBounds(),this.mainStage,this.gestion,this.gestion.getDataBaseManager());
            this.mainStage.setScene(classMapEditor.getScene());
        });
        //If the class map is undefined we cant click on the checkCurrentMap button
        if (this.classMap.getStatus().equals("undefined")){
            checkCurrentMap.disableProperty().set(true);
        }
        //onclick on the checkCurrentMap button : open a popup where you can see the current class map
        checkCurrentMap.setOnAction(event -> {
            ClassMapVisu classMapVisu = new ClassMapVisu(this.classMap,this.classMap.currentClassMap, Screen.getPrimary().getVisualBounds(),this.mainStage,this.gestion);
            this.mainStage.setScene(classMapVisu.getScene());
        });
    }

    public VBox getCard() {
        return this.card;
    }
}
