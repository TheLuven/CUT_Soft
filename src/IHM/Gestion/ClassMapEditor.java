package IHM.Gestion;

import com.sun.javafx.scene.control.LabeledText;
import dataTypes.actors.Student;
import dataTypes.classMap.ClassMap;
import dataTypes.classMap.ClassMapLayer;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import IHM.Window.Window;

import java.util.ArrayList;

public class ClassMapEditor {
    public ClassMapLayer classMapLayer;
    public Rectangle2D screenBounds;
    public Stage stage;
    public Window window;
    public ClassMap classMap;
    public ArrayList<Student> studentList;
    public SplitPane splitView;

    public ClassMapEditor(ClassMap ClassMap,ClassMapLayer classMapLayer, Rectangle2D screenBounds, Stage stage){
        this.classMapLayer = classMapLayer;
        this.screenBounds = screenBounds;
        this.splitView = new SplitPane();
        this.stage = stage;
        this.window = new Window();
        this.classMap = ClassMap;
        this.studentList = classMap.getaClass().getStudents();
        diplayStudentList();
        window.setTitle("Class Map Editor - "+classMap.getaClass().getClassName()+" - "+classMap.getSubject().getSubjectName()+" - "+classMap.getTeacher().getSurname()+" "+classMap.getTeacher().getName());
    }


    public void diplayStudentList(){
        HBox container = this.window.getMiddlePanel();
        ListView<Student> listView=new ListView<>();
        HBox mapEditor = new HBox();
        this.splitView.getItems().addAll(listView,mapEditor);
        listView.getItems().addAll(this.studentList);
        container.getChildren().add(splitView);
        listView.setMaxWidth(this.screenBounds.getWidth()/3);
        mapEditor.setPrefWidth(this.screenBounds.getWidth());
        mapEditor.setStyle("-fx-background-color: #2b2d30;");

        //add a mouse event for each student
        listView.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                Student student = listView.getSelectionModel().getSelectedItem();
                if (student != null) {
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                        System.out.println("Double click on : " + student.getSurname());
                    }
                }
            }
        });

    }

    public Scene getScene(){
        return this.window.getScene();
    }
}
