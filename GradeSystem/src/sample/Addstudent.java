package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Addstudent implements Initializable {
    Connection conn;
    @FXML
    private AnchorPane addstudent;
    @FXML
    private TextField studentid,name,surname,email,telephone,username,password;
    @FXML
    ComboBox students;
    public void back3(ActionEvent actionEvent) throws IOException {
        Controller c=new Controller();
        c. menu(addstudent,"admin.fxml");
    }

    public void submit(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Studentid=studentid.getText();
        String Username = username.getText();
        String Password = password.getText();
        String Firstname = name.getText();
        String Lastname = surname.getText();
        String Email = email.getText();
        String Telephone = telephone.getText();

        Boolean bool=true;
        conn=DataManage.dbConnect();
        String query2 = "SELECT  * FROM student";
        Statement stmt2 = conn.createStatement();
        ResultSet rs = stmt2.executeQuery(query2);

        while (rs.next()) {
            if(((rs.getString(1)).equals(studentid.getText()))||((rs.getString(2)).equals(username.getText()))){
                bool=false;
            }
        }
        if (bool==false){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You can not have more than one record same id or username");
            alert.showAndWait();
            return;
        }

        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            conn = DataManage.dbConnect();
            String query = "INSERT INTO student (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL,TELEPHONE) VALUES ('" + Studentid + "','" + Username + "','" + Password + "','" + Firstname + "','" + Lastname + "','" + Email + "','"+Telephone+"')";
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
            alert.setContentText("Username and password not null");
            alert.showAndWait();

        }

        }

    public void delete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!studentid.getText().isEmpty()) {
            conn = DataManage.dbConnect();
            String query = "DELETE FROM student  WHERE ID='" + studentid.getText()+"'";
            String query2 = "DELETE FROM studentgrade  WHERE STUDENTID='" + studentid.getText()+"'";
            String query3="DELETE FROM grades  WHERE STUDENTID='" + studentid.getText()+"'";
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            int result2=stmt.executeUpdate(query2);
            int result3=stmt.executeUpdate(query3);
            if (result == 1||result2==0||result3==0) {
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
        String Email = email.getText();
        String Telephone = telephone.getText();

        if (!studentid.getText().isEmpty()&&!email.getText().isEmpty()&&!telephone.getText().isEmpty()) {
            conn = DataManage.dbConnect();
            String query = "UPDATE student SET EMAIL='" + Email + "', TELEPHONE='" + Telephone + "' WHERE ID=" + studentid.getText();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conn = DataManage.dbConnect();
            String query = "SELECT  * FROM student";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                students.getItems().add(rs.getString(1)+"\\"+rs.getString(2)+"\\"+rs.getString(3)+"\\"+rs.getString(4)
                        +"\\"+rs.getString(5)+"\\"+rs.getString(6)+"\\"+rs.getString(7) );
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
