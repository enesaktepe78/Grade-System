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
import java.sql.Statement;

public class AdminLogin {
    PreparedStatement pst = null;
    ResultSet resultSet = null;
    Connection conn;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    AnchorPane adminlogin;

    public void confirm(ActionEvent actionEvent) throws IOException {
        Controller c=new Controller();

        String str=username.getText();
        String str2=password.getText();

        try {
            Connection conn = DataManage.dbConnect();
            String query = "SELECT * FROM instructor WHERE USERNAME=? AND PASSWORD=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, str);
            pst.setString(2, str2);
            resultSet = pst.executeQuery();

            if (resultSet.next()) {
                genel.instructor=resultSet.getString(7);
                genel.instructor2=resultSet.getString(3)+" "+resultSet.getString(4);
                c.menu(adminlogin,"instructor.fxml");
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
        c.menu(adminlogin,"sample.fxml");
    }
}
