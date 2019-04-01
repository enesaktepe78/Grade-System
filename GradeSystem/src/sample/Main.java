package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Grade System");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataManage.dbConnect();
        launch(args);
    }
}
