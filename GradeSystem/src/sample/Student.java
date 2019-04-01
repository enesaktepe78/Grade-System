package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Student implements Initializable {
    Connection conn;
    @FXML
    private AnchorPane sahne2;
    @FXML
    ComboBox combo;
    @FXML
    PieChart grafik;
    @FXML

    int x, y, z, w, q,p;
    String student;

    public Student() {

    }
    @FXML   private TableView<Person> tableview;
    @FXML   private TableColumn<Person,String> studentid;
    @FXML   private TableColumn<Person,Integer> grade;
    ObservableList<Person> list;

    public void degeral() throws SQLException, ClassNotFoundException {
            conn = DataManage.dbConnect();
            list=FXCollections.observableArrayList();

            String query = "SELECT  * FROM grades WHERE COURSEID='" + combo.getValue()+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
            list.add(new Person(rs.getString(2),rs.getInt(3)));
            }
            studentid.setCellValueFactory(new PropertyValueFactory<Person, String>("studentid"));
            grade.setCellValueFactory(new PropertyValueFactory<Person, Integer>("grade"));
            tableview.setItems(null);
            tableview.setItems(list);
            rs.close();
            stmt.close();
            conn.close();
    }

    public Student(String student) {
        this.student = student;
    }

    public String getStudent() {
        return student;
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Controller c = new Controller();
        c.menu(sahne2, "sample.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            conn = DataManage.dbConnect();
            String query = "SELECT  * FROM course ";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                combo.getItems().add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void register(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String x = genel.student;
        String y = genel.student2;
        conn = DataManage.dbConnect();
        Boolean bool = true;
        String query2 = "SELECT  * FROM studentgrade";
        Statement stmt2 = conn.createStatement();
        ResultSet rs = stmt2.executeQuery(query2);

        while (rs.next()) {
            if ((rs.getString(1) + rs.getString(2)).equals(combo.getValue() + x)) {
                bool = false;
            }
        }
        if (bool == false) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You can not have more than one record");
            alert.showAndWait();
            return;
        }
        if(quota(String.valueOf(combo.getValue())) > count(String.valueOf(combo.getValue()))){

            String query = "INSERT INTO studentgrade (COURSEID, STUDENTID, STUDENTNAME) VALUES ('" + combo.getValue() + "','" + x + "','" + y + "')";
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
            rs.close();
            stmt.close();
            conn.close();

            if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Eklendi");
                alert.showAndWait();
            }

        stmt2.close();
        stmt.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"quota is full");
            alert.showAndWait();
        }
        conn.close();
    }

    public int quota(String courseID) throws SQLException, ClassNotFoundException {

        String updateTableSQL = "SELECT * FROM  course  WHERE COURSEID ='" + courseID+"'";
        Statement s= conn.createStatement();

        ResultSet rs = s.executeQuery(updateTableSQL);
        int sayi=0;
    while (rs.next()) {
     sayi = rs.getInt(3);
    }
        return sayi;
    }

    public int count(String courseID) throws SQLException, ClassNotFoundException {

        String updateTableSQL = "SELECT * FROM  studentgrade  WHERE COURSEID ='" + courseID+"'";
        Statement s= conn.createStatement();
        ResultSet rs = s.executeQuery(updateTableSQL);
        int temp = 0;
        while (rs.next()){
            temp++;
        }
        return temp;
    }

    public void control(ComboBox kombo) throws SQLException, ClassNotFoundException {
        conn = DataManage.dbConnect();
        String query2 = "SELECT  * FROM studentgrade ";
        Statement stmt2 = conn.createStatement();
        ResultSet rs = stmt2.executeQuery(query2);
        if ((rs.getString(1) + rs.getString(2)).equals(kombo.getValue() + genel.student)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Error!");
            alert.setHeaderText(null);
            alert.setContentText("Username or password wrong");
            alert.show();
        }
        stmt2.close();
        conn.close();
    }

    public void combo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        grafik.getData().clear();
        degeral();
        conn = DataManage.dbConnect();
        String query2 = "SELECT  * FROM grades WHERE COURSEID='" + combo.getValue() + "'";
        Statement stmt2 = conn.createStatement();
        ResultSet rs = stmt2.executeQuery(query2);

        while (rs.next()) {
            grafik(rs.getInt(3));
        }
        x = 0;
        y = 0;
        z = 0;
        q = 0;
        w = 0;

        stmt2.close();
        conn.close();
    }

    public void grafik(int a) {
        grafik.getData().clear();
        if (a <= 100 && a >= 80) {
            x++;
        } else if (a <= 80 && a >= 60) {
            y++;
        } else if (a <= 60 && a >= 40) {
            z++;
        } else if (a <= 40 && a >= 20) {
            w++;
        } else if (a <= 20 && a >= 0) {
            q++;
        }
        PieChart.Data slice1 = new PieChart.Data("80-100", x);
        PieChart.Data slice2 = new PieChart.Data("60-80", y);
        PieChart.Data slice3 = new PieChart.Data("40-60", z);
        PieChart.Data slice4 = new PieChart.Data("20-40", w);
        PieChart.Data slice5 = new PieChart.Data("0-20", q);

        grafik.getData().add(slice1);
        grafik.getData().add(slice2);
        grafik.getData().add(slice3);
        grafik.getData().add(slice4);
        grafik.getData().add(slice5);

    }
}
