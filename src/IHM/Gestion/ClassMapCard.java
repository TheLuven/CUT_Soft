package IHM.Gestion;

import dataTypes.classMap.ClassMap;
import dataTypes.classMap.ClassMapLayer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClassMapCard {
    public ClassMap classMap;
    public VBox card;
    public ArrayList<ClassMapLayer> drafts;
    public ClassMapLayer currentClassMap;
    public Stage mainStage;
    public ClassMapCard(ClassMap classMap, Stage stage){
        this.classMap = classMap;
        this.card = new VBox();
        this.drafts = classMap.drafts;
        this.currentClassMap = classMap.currentClassMap;
        this.mainStage=stage;
        cardGenerator();
    }

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
            HBox content = new HBox();
            HBox topBar = new HBox();
            VBox vBox = new VBox();
            vBox.setStyle("-fx-border-color: #ffffff; -fx-background-color: #1c1c1e;");
            content.setStyle("-fx-border-color: #ffffff;");
            topBar.setStyle("-fx-border-color: #ffffff;");
            content.setPadding(new Insets(10,10,10,10));
            Popup popup = new Popup();
            popup.setX(300);
            popup.setY(200);
            TextArea textArea = new TextArea("Draft name");
            Button confirmButton = new Button("Confirm");
            //The text area cant exceed 20 characters
            textArea.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 20) {
                    textArea.setText(oldValue);
                }
            });
            //The text area is one line of height
            textArea.setPrefHeight(25);
            textArea.setPrefWidth(200);
            confirmButton.disableProperty().set(true);
            confirmButton.setOnAction(event1 -> {
                if(!textArea.getText().equals("Draft name")){
                    this.drafts.add(new ClassMapLayer(textArea.getText()));
                    comboBox.getItems().add(textArea.getText());
                    popup.hide();
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
                }else{
                    textArea.setStyle("-fx-border-color: #ffffff;");
                    confirmButton.disableProperty().set(false);
                }
                for(ClassMapLayer s : drafts){
                    if(s.getName().equals(newValue)){
                        textArea.setStyle("-fx-border-color: red;");
                        confirmButton.disableProperty().set(true);
                    }
                }
            });
            Button closeButton = new Button("X");
            closeButton.setOnAction(event1 -> {
                popup.hide();
            });
            Text title = new Text("Create a new draft");
            title.setFill(Color.WHITE);
            topBar.getChildren().addAll(closeButton,title);
            vBox.getChildren().addAll(topBar,content);
            content.getChildren().addAll(textArea,confirmButton);
            popup.getContent().addAll(vBox);
            popup.show(this.mainStage);
        });

        //onclick on the editDraft button : open the draft selected in the comboBox
        editDraft.setOnAction(event -> {
            //TODO : create the popup
        });

        //onclick on the checkCurrentMap button : open a popup where you can see the current class map
        checkCurrentMap.setOnAction(event -> {
            //TODO : create the popup
        });
    }

    public VBox getCard() {
        return this.card;
    }
}
