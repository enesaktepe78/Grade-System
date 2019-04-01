package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Admin {
    @FXML
    private AnchorPane sahne3;

    public void back2(ActionEvent actionEvent) throws IOException {
        Controller c=new Controller();
        c. menu(sahne3,"sample.fxml");
    }

    public void submit(ActionEvent actionEvent) {

    }

    public void addstudent(ActionEvent actionEvent) throws IOException {
        Controller c=new Controller();
        c. menu(sahne3,"Addstudent.fxml");
    }

    public void addinstructor(ActionEvent actionEvent) throws IOException {
        Controller c=new Controller();
        c. menu(sahne3,"Addinstructor.fxml");
    }
}
