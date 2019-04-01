package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    AnchorPane sahne;
    public void menu(AnchorPane scene,String fxml) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource(fxml));
        scene.getChildren().setAll(pane);
    }
    public void admin(javafx.event.ActionEvent actionEvent) throws IOException {
        menu(sahne,"admin.fxml");
    }

    public void student(javafx.event.ActionEvent actionEvent) throws IOException {
        menu(sahne,"StudentLogin.fxml");
    }

    public void instructor(javafx.event.ActionEvent actionEvent) throws IOException {
        menu(sahne,"adminlogin.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
