package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Instructor implements Initializable {
    Connection conn;
    @FXML
    AnchorPane sahne4;
    @FXML
    TextField courseid,coursename,quota,grade;
    @FXML
    ComboBox combo;
    @FXML
    ComboBox courses;
    @FXML   private TableView<Person2> tablo;
    @FXML   private TableColumn<Person2,String> studentidT;
    @FXML   private TableColumn<Person2,Integer> gradeT;
    ObservableList<Person2> list2;

    public Instructor(){

    }

    public void back3(ActionEvent actionEvent) throws IOException {
        Controller c=new Controller();
        c.menu(sahne4,"sample.fxml");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String str=genel.instructor;
        try {
            conn = DataManage.dbConnect();
            String query = "SELECT  * FROM course WHERE ID='" + str+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                courses.getItems().add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void submit(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String str=courseid.getText();
        String str2=coursename.getText();
        String st3=quota.getText();
        String str4=genel.instructor;

        Boolean bool=true;
        conn=DataManage.dbConnect();
        String query2 = "SELECT  * FROM course";
        Statement stmt2 = conn.createStatement();
        ResultSet rs = stmt2.executeQuery(query2);

        while (rs.next()) {
            if(((rs.getString(1)).equals(courseid.getText()))){
                bool=false;
            }
        }
        if (bool==false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You can not have more than one record same courseid");
            alert.showAndWait();
            return;
        }
        if (!courseid.getText().isEmpty() && !coursename.getText().isEmpty()) {
            conn = DataManage.dbConnect();
            String query = "INSERT INTO course (COURSEID, COURSENAME, QUOTA,ID) VALUES ('" + str + "','" + str2 + "','" + st3 + "','"+ str4+ "')";
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Eklendi");
                alert.showAndWait();
            }

            stmt.close();
            conn.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Courseid and coursename not null");
            alert.showAndWait();
        }
    }

    public void delete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String str=courseid.getText();
        if (!courseid.getText().isEmpty()) {
            conn = DataManage.dbConnect();
            String query = "DELETE FROM course  WHERE COURSEID = '" + String.valueOf(str)+"'";
            String query2 = "DELETE FROM grades  WHERE COURSEID = '" + String.valueOf(str)+"'";
            String query3 = "DELETE FROM studentgrade  WHERE COURSEID = '" + String.valueOf(str)+"'";
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            int resul2=stmt.executeUpdate(query2);
            int result3=stmt.executeUpdate(query3);
            if (result == 1||resul2==1||result3==1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Silindi");
                alert.showAndWait();
            }
            stmt.close();
            DataManage.dbDisconnect(conn);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("id not null");
            alert.showAndWait();
        }
    }

    public void update(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Coursename = coursename.getText();
        String Quota = quota.getText();

        if (!coursename.getText().isEmpty()&&!quota.getText().isEmpty()&&!courseid.getText().isEmpty()) {
            conn = DataManage.dbConnect();
            String query = "UPDATE course SET COURSENAME='" + Coursename + "', QUOTA='" + Quota + "' WHERE COURSEID='" +  courseid.getText()+"'";
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Güncellendi");
                alert.showAndWait();
            }
            stmt.close();
            DataManage.dbDisconnect(conn);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in required places");
            alert.showAndWait();
        }
    }
    public void grade(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(grade.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have to enter grade");
            alert.showAndWait();
            return;
        }
        boolean bool=true;
        int x= Integer.parseInt(grade.getText());
        conn = DataManage.dbConnect();
        String query2 = "SELECT  * FROM grades";
        Statement stmt2 = conn.createStatement();
        ResultSet rs = stmt2.executeQuery(query2);
        String not=String.valueOf(courses.getValue())+String.valueOf(combo.getValue());

        while (rs.next()) {
            if((rs.getString(1)+rs.getString(2)).equals(not)){
                bool=false;
            }
        }
        if (bool==false){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You can not have more than one grade");
            alert.showAndWait();
            return;
        }
        String query = "INSERT INTO grades (COURSEID, STUDENTID, GRADE) VALUES ('" +  courses.getValue() + "','" + combo.getValue() + "','" + x +"')";
        Statement stmt = conn.createStatement();
        int result = stmt.executeUpdate(query);
        if (result == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Eklendi");
            alert.showAndWait();
        }

        stmt.close();
        conn.close();
    }
    public void tabloyaekle() throws SQLException, ClassNotFoundException {
        conn = DataManage.dbConnect();
        list2= FXCollections.observableArrayList();

        String query = "SELECT  * FROM grades WHERE COURSEID='" + courses.getValue()+"'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        String query2="SELECT  * FROM course WHERE COURSEID='" + courses.getValue()+"'";
        Statement stmt2 = conn.createStatement();
        ResultSet rs2 = stmt2.executeQuery(query2);

        while (rs.next()) {
            list2.add(new Person2(rs.getString(2),rs.getInt(3)));
        }
        studentidT.setCellValueFactory(new PropertyValueFactory<Person2, String>("studentidT"));
        gradeT.setCellValueFactory(new PropertyValueFactory<Person2, Integer>("gradeT"));
        tablo.setItems(null);
        tablo.setItems(list2);

        rs.close();
        stmt.close();
        conn.close();
    }

    public void courses(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        tabloyaekle();
        combo.getItems().clear();
        try {
            conn = DataManage.dbConnect();
            String query = "SELECT  * FROM studentgrade WHERE COURSEID = '" + courses.getValue()+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                combo.getItems().add(rs.getString(2));
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gradeupdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String updateTableSQL = "UPDATE grades SET GRADE = ? WHERE COURSEID = ? AND STUDENTID = ? ";
        PreparedStatement preparedStatement = DataManage.dbConnect().prepareStatement(updateTableSQL);
        preparedStatement.setString(1, grade.getText());
        preparedStatement.setString(2, String.valueOf(courses.getValue()));
        preparedStatement.setString(3, String.valueOf(combo.getValue()));
        preparedStatement .executeUpdate();
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Güncellendi");
        alert.showAndWait();
    }

}
