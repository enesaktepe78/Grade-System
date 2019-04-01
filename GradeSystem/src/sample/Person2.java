package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person2 {
    private final SimpleStringProperty studentidT;
    private final SimpleIntegerProperty gradeT;


    public Person2(String studentid, Integer grade) {
        super();
        this.studentidT = new SimpleStringProperty(studentid);
        this.gradeT =new SimpleIntegerProperty(grade);

    }

    public String getStudentidT() {
        return studentidT.get();
    }

    public SimpleStringProperty studentidTProperty() {
        return studentidT;
    }

    public void setStudentidT(String studentidT) {
        this.studentidT.set(studentidT);
    }

    public int getGradeT() {
        return gradeT.get();
    }

    public SimpleIntegerProperty gradeTProperty() {
        return gradeT;
    }

    public void setGradeT(int gradeT) {
        this.gradeT.set(gradeT);
    }
}
