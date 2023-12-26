package IHM.Gestion;

import dataTypes.ClassMap;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ClassMapCard {
    public ClassMap classMap;
    public VBox card;
    public ClassMapCard(ClassMap classMap){
        this.classMap = classMap;
        this.card = new VBox();
        cardGenerator();
    }

    public void cardGenerator(){
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
        this.card.getChildren().addAll(className,subjectName,studentNumber,classMapStatus);
        this.card.setStyle("-fx-border-color: #ffffff");
        this.card.setPadding(new Insets(10,10,10,10));
    }

    public VBox getCard() {
        return this.card;
    }
}
