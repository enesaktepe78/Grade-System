package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentLogin {
    String Username;
    PreparedStatement pst = null;
    ResultSet resultSet = null;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    AnchorPane studentlogin;
    ArrayList liste;
    public void confirm(ActionEvent actionEvent) {
        liste=new ArrayList();
        Controller c=new Controller();
        Username=username.getText();
        String Password=password.getText();

        try {
            Connection conn = DataManage.dbConnect();
            String query = "SELECT * FROM student WHERE USERNAME=? AND PASSWORD=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, Username);
            pst.setString(2, Password);
            resultSet = pst.executeQuery();

            if (resultSet.next()) {
                 genel.student=resultSet.getString(1);
                 genel.student2=resultSet.getString(4)+" "+resultSet.getString(5);
                 c.menu(studentlogin,"Ogrenci.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Form Error!");
                alert.setHeaderText(null);
                alert.setContentText("Username or password wrong");
                alert.show();
            }
            pst.close();
            resultSet.close();
            DataManage.dbDisconnect(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void back(ActionEvent actionEvent) throws IOException {
        Controller c=new Controller();
        c.menu(studentlogin,"sample.fxml");
    }
}
