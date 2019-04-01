package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {

    private final SimpleStringProperty studentid;
    private final SimpleIntegerProperty grade;

    public Person( String studentid, Integer grade) {
        super();
        this.studentid = new SimpleStringProperty(studentid);
        this.grade = new SimpleIntegerProperty(grade);
    }

    public String getStudentid() {
        return studentid.get();
    }

    public SimpleStringProperty studentidProperty() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid.set(studentid);
    }

    public int getGrade() {
        return grade.get();
    }

    public SimpleIntegerProperty gradeProperty() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade.set(grade);
    }
}
