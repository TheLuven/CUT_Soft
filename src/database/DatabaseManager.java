package database;

import dataTypes.classMap.ClassMap;
import dataTypes.classMap.ClassMapLayer;
import dataTypes.classMap.Subject;
import dataTypes.actors.*;
import dataTypes.classMap.object.BoardOrientation;
import dataTypes.classMap.object.Class;
import dataTypes.classMap.object.Desk;
import dataTypes.classMap.object.DeskOrientation;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class DatabaseManager {
    DatabaseConnection database;

    /**
     * @brief Constructor of the DatabaseManager class. This class provide you some tools to access all data from the database.
     * @author Victor VENULETH
     * @param database The database from DatabaseConnection.java
     */
    public DatabaseManager(DatabaseConnection database) {
        this.database = database;
    }

    /**
     * @brief This function allows you to update simple values from a simple querry.
     * @author Victor VENULETH
     * @param id
     * @param value
     * @param tableName
     * @param columnName
     */
    public void simpleUpdateQuery(int id, String value, String tableName, String columnName) {
        try {
            Connection connection = this.database.getConnection();
            String updateQuery = "UPDATE "+tableName+" SET "+columnName+" = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, id);
            System.out.println("[DEBUG] Update Query : "+preparedStatement.toString());
            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();
            // Check the number of rows affected
            connection.close();
            System.out.println("[DEBUG] "+rowsAffected + " row(s) updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief This function allows you to access simple values from a simple querry.
     * @author Victor VENULETH
     * @param id
     * @param value
     * @param tableName
     * @return It returns a String that may need to be parsed in int for example.
     */
    public String simpleGetQuery(int id, String value, String tableName) {
        try {
            Connection connection = this.database.getConnection();
            String updateQuery = "SELECT ? FROM ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, tableName);
            preparedStatement.setInt(3, id);
            System.out.println("[DEBUG] Get Query : "+preparedStatement.toString());
            // Execute the get query
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check the number of rows affected
            connection.close();
            return resultSet.getString(value);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @brief This function allow you to retrieve the student using its ID.
     * @author Victor VENULETH
     * @param id
     * @return the student in the Student.java object.
     */
    public Student getStudentByID(int id) {
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT p.name,p.surname,p.email,p.gender,p.role,p.description,c.className FROM person as p,class as c,classPerson as cp WHERE p.id=" + id+ " AND cp.person = "+id+" AND cp.class = c.id AND p.role='student'";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String description = resultSet.getString("description");
                String role = resultSet.getString("role");
                String className = resultSet.getString("className");
                connection.close();
                return new Student(this, id, name, surname, email, description, className, gender);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @brief This function allows you to access teacher using its ID.
     * @author Victor VENULETH
     * @param id
     * @return the teacher in the Teacher.java object
     */
    public Teacher getTeacherByID(int id) {
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT p.name,p.surname,p.email,p.gender,p.role,p.description FROM person as p WHERE p.role='teacher' AND p.id=" + id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String description = resultSet.getString("description");
                String role = resultSet.getString("role");
                connection.close();
                return new Teacher(this, id, name, surname, email, description, gender);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @brief This function allows you to access the subject using its ID.
     * @author Victor VENULETH
     * @param id
     * @return the Subject in the Subject.java object.
     */
    public Subject getSubjectByID(int id) {
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT subject,teacher FROM subject WHERE id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String subject = resultSet.getString("subject");
            int teacher = resultSet.getInt("teacher");
            connection.close();
            return new Subject(subject,getTeacherByID(teacher),id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Subject getSubjectByID(int id,Teacher teacher) {
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT subject,teacher FROM subject WHERE id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String subject = resultSet.getString("subject");
            connection.close();
            return new Subject(subject,teacher,id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @brief This function allows you to retrieve all the Students in a class.
     * @author Victor VENULETH
     * @param id
     * @return the List of all student of a defined class.
     */
    public ArrayList<Student> getClassStudentsByID(int id){
        ArrayList<Student> students = new ArrayList<>();
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT person FROM classPerson cp, person p WHERE class="+id+" AND p.role='student' AND cp.person = p.id";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                students.add(getStudentByID(resultSet.getInt("person")));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return students;
    }

    /**
     * @brief This function allows you to retrieve all subjects of a class, using its ID
     * @author Victor VENULETH
     * @param id
     * @return it returns all Subjects in an ArrayList
     */
    public ArrayList<Subject> getClassSubjectsByID(int id){
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT subject  FROM classSubject cs WHERE cs.class = "+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                subjects.add(getSubjectByID(resultSet.getInt("subject")));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return subjects;
    }
    public ArrayList<Subject> getClassSubjectsByID(int id,Teacher teacher){
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT subject  FROM classSubject cs WHERE cs.class = "+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                subjects.add(getSubjectByID(resultSet.getInt("subject"),teacher));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return subjects;
    }

    /**
     * @brief This function allows you retrieve a class using its ID
     * @author Victor VENULETH
     * @param id
     * @return the class in a Class.Java object
     */
    public Class getClassByID(int id){
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT className  FROM class WHERE id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String className = resultSet.getString("className");
            ArrayList<Student> students = getClassStudentsByID(id);
            ArrayList<Subject> subjects = getClassSubjectsByID(id);
            connection.close();
            return new Class(students,className,subjects,id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Class getClassByID(int id,Teacher teacher){
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT className  FROM class WHERE id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String className = resultSet.getString("className");
            ArrayList<Student> students = getClassStudentsByID(id);
            ArrayList<Subject> subjects = getClassSubjectsByID(id,teacher);
            connection.close();
            return new Class(students,className,subjects,id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @brief This function allows you to retrieve the class of a Student.
     * @author Victor VENULETH
     * @param id
     * @return the class in a Class.java object
     */
    public Class getStudentClassByID(int id){
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT class  FROM classPerson cp WHERE person ="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int classID = resultSet.getInt("class");
            connection.close();
            return getClassByID(classID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ClassMap> getAllTeacherClassMapByTeacher(Teacher teacher){
        ArrayList<ClassMap> classMaps = new ArrayList<>();
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT cs.class,cs.subject  FROM classSubject cs, person p,subject s  WHERE p.`role` = 'teacher' AND s.teacher = p.id AND s.id = cs.subject AND p.id ="+teacher.id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int classID = resultSet.getInt("class");
                int subjectID = resultSet.getInt("subject");
                classMaps.add(new ClassMap(teacher,getSubjectByID(subjectID,teacher),getClassByID(classID,teacher)));
            }
            return classMaps;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * @brief This function allows you to retrieve the id of a student.
     * @author Yohan Jaffré
     * @param usernameEntered
     * @return the id of the student
     */
    public int getIdByUsername(String usernameEntered) {
        int userId = -1;
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT p.id FROM person as p WHERE p.identifiant= '"+usernameEntered+"' AND p.role='teacher' ";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userId = Integer.parseInt(resultSet.getString("id"));
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    /**
     * @brief This function allows you to verify the password of a student.
     * @author Yohan Jaffré
     * @param id, passwordEntered
     * @return true if the password is correct, false if not
     */
    public boolean CheckPassword(int id, String passwordEntered) {
        boolean check = false;
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT p.password FROM person as p WHERE p.id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String password = resultSet.getString("password");
                connection.close();
                check = password.equals(passwordEntered);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    /**
     * @brief
     * @author Victor VENULETH
     * @param
     * @return
     */
    public void updateACoordinate(int id,double x, double y,int subjectID,String desk_orientation) {
        try {
            Connection connection = this.database.getConnection();
            String updateQuery = "UPDATE coordinate " +
                                 "SET x = ?, y = ?, subject = ?, desk_orientation = ? " +
                                 "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setDouble(1, x);
            preparedStatement.setDouble(2, y);
            preparedStatement.setInt(3, subjectID);
            preparedStatement.setString(4, desk_orientation);
            preparedStatement.setInt(5, id);
            System.out.println("[DEBUG] Update Query : "+preparedStatement.toString());
            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();
            // Check the number of rows affected
            connection.close();
            System.out.println("[DEBUG] "+rowsAffected + " row(s) updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * @brief
     * @author Victor VENULETH
     * @param
     * @return
     */
    public void setACoordinate(double x, double y,int subjectID,String desk_orientation) {
        try {
            Connection connection = this.database.getConnection();
            String setQuery = "INSERT INTO coordinate (x,y,subject,desk_orientation) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(setQuery);
            preparedStatement.setDouble(1, x);
            preparedStatement.setDouble(2, y);
            preparedStatement.setInt(3, subjectID);
            preparedStatement.setString(4, desk_orientation);
            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            // Check the number of rows affected
            connection.close();
            System.out.println("[DEBUG] "+rowsAffected + " row(s) inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAllCoordinateOfASubject(int subjectID){
        try {
            Connection connection = this.database.getConnection();
            //Delete all row where subject_id = subjectID
            String deleteQuery = "DELETE FROM coordinate WHERE subject = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, subjectID);
            // Execute the delete query
            int rowsAffected = preparedStatement.executeUpdate();
            // Check the number of rows affected
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setACoordinate(double x, double y,int subjectID,String desk_orientation,int studentID) {
        try {
            Connection connection = this.database.getConnection();
            String setQuery = "INSERT INTO coordinate (x,y,subject,desk_orientation,person) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(setQuery);
            preparedStatement.setDouble(1, x);
            preparedStatement.setDouble(2, y);
            preparedStatement.setInt(3, subjectID);
            preparedStatement.setString(4, desk_orientation);
            preparedStatement.setInt(5, studentID);
            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            // Check the number of rows affected
            connection.close();
            System.out.println("[DEBUG] "+rowsAffected + " row(s) inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * @brief
     * @author Victor VENULETH
     * @param
     * @return
     */
    public void updateAClassSubject(int class_id,int subject_id,double width, double height,String room_name,String class_status,String board_orientation) {
        try {
            Connection connection = this.database.getConnection();
            String updateQuery = "UPDATE classsubject SET width = ? ,height = ? ,room_name = ? ,class_status = ? ,board_orientation = ? WHERE class = ? AND subject = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setDouble(1, width);
            preparedStatement.setDouble(2, height);
            preparedStatement.setString(3, room_name);
            preparedStatement.setString(4, class_status);
            preparedStatement.setString(5, board_orientation);
            preparedStatement.setInt(6, class_id);
            preparedStatement.setInt(7, subject_id);
            System.out.println("[DEBUG] Update Query : "+preparedStatement.toString());
            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();
            // Check the number of rows affected
            connection.close();
            System.out.println("[DEBUG] "+rowsAffected + " row(s) updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateClassStatus(int class_id,int subject_id, String status){
        try {
            Connection connection = this.database.getConnection();
            String updateQuery = "UPDATE classsubject SET class_status = ? WHERE class = ? AND subject = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, class_id);
            preparedStatement.setInt(3, subject_id);
            System.out.println("[DEBUG] Update Query : "+preparedStatement.toString());
            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();
            // Check the number of rows affected
            connection.close();
            System.out.println("[DEBUG] "+rowsAffected + " row(s) updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Desk> getAllCordinateOfASubject(int subjectID, Class aClass){
        try {
            ArrayList<Desk> desks = new ArrayList<>();
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT person,desk_orientation,x,y FROM coordinate WHERE subject="+subjectID;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int person = resultSet.getInt("person");
                DeskOrientation deskOrientation;
                String desk_orientation = resultSet.getString("desk_orientation");
                if (desk_orientation.equals("horizontal")) deskOrientation = DeskOrientation.horizontal;
                else deskOrientation = DeskOrientation.vertical;
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                desks.add(new Desk(x,y,"mono",deskOrientation,aClass.getStudentByID(person)));
            }
            System.out.println(desks);
            connection.close();
            return desks;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public ClassMapLayer getCurrentClassMap(int class_id, int subject){
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT width,height,class_status,board_orientation FROM classsubject WHERE class="+class_id+" AND subject="+subject;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            double width = resultSet.getDouble("width");
            double height = resultSet.getDouble("height");
            String class_status = resultSet.getString("class_status");
            String board_orientation = resultSet.getString("board_orientation");
            connection.close();
            BoardOrientation bo = BoardOrientation.north;
            if (board_orientation.equals("north")) bo = BoardOrientation.north;
            else if (board_orientation.equals("south")) bo = BoardOrientation.south;
            else if (board_orientation.equals("east")) bo = BoardOrientation.east;
            else if (board_orientation.equals("west")) bo = BoardOrientation.west;
            ClassMapLayer classMapLayer = new ClassMapLayer("Current ClassMap",width,height,bo);
            for(Desk desk : getAllCordinateOfASubject(subject,getClassByID(class_id))){
                classMapLayer.addDesk(desk);
            }
            return classMapLayer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getClassMapStatus(int class_id,int subject_id){
        try {
            Connection connection = this.database.getConnection();
            String selectQuery = "SELECT class_status FROM classsubject WHERE class="+class_id+" AND subject="+subject_id;
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            // Execute the query and get the result
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String status = resultSet.getString("class_status");
            connection.close();
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
