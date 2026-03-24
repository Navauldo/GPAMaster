package Frontend.controllers;

import Frontend.MainApp;
import Backend.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.util.List;

public class StudentDashboardController {

    @FXML private Label   studentNameLabel;
    @FXML private Label   studentIdLabel;
    @FXML private Label   pageTitle;
    @FXML private TabPane tabPane;
    @FXML private Tab     gradesTab;
    @FXML private Tab     gpaTab;
    @FXML private Button  navGrades;
    @FXML private Button  navGPA;
    @FXML private ComboBox<String> themePicker;

    @FXML private TableView<Enrollment>          gradesTable;
    @FXML private TableColumn<Enrollment,String> colCode;
    @FXML private TableColumn<Enrollment,String> colCName;
    @FXML private TableColumn<Enrollment,String> colGrade;
    @FXML private TableColumn<Enrollment,String> colLetter;

    @FXML private Label gpaValue;
    @FXML private Label courseCount;
    @FXML private Label standingLabel;

    private Student loggedStudent;
    private ObservableList<Enrollment> enrollmentData = FXCollections.observableArrayList();

    public void initData(Student student) {
        this.loggedStudent = student;
        studentNameLabel.setText(student.getName());
        studentIdLabel  .setText("ID: " + student.getId());
        setupTable();
        refreshData();
        setupThemePicker();
        showGradesView();
    }

    private void setupThemePicker() {
        themePicker.getItems().addAll(
                "NordDark", "Dracula", "CupertinoDark", "PrimerDark", "PrimerLight", "CupertinoLight"
        );
        themePicker.setValue("NordDark");
        themePicker.setOnAction(e -> MainApp.setTheme(themePicker.getValue()));
    }

    private void setupTable() {
        colCode  .setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getCourse().getCCode()));
        colCName .setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getCourse().getCName()));
        colGrade .setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getGrade())));
        colLetter.setCellValueFactory(c -> new SimpleStringProperty(
                toLetter(c.getValue().getGrade())));
        gradesTable.setItems(enrollmentData);
    }

    private void refreshData() {
        List<Enrollment> enrollments = loggedStudent.getEnrollments();
        enrollmentData.setAll(enrollments);
        courseCount.setText(String.valueOf(enrollments.size()));
        if (enrollments.isEmpty()) {
            gpaValue.setText("N/A");
            standingLabel.setText("No courses yet");
            return;
        }
        double total = 0;
        for (Enrollment e : enrollments) {
            total += toPoints(e.getGrade());
        }
        double gpa = total / enrollments.size();
        gpaValue.setText(String.format("%.2f", gpa));
        standingLabel.setText(toStanding(gpa));
    }

    @FXML private void showGradesView() {
        tabPane.getSelectionModel().select(gradesTab);
        pageTitle.setText("My Grades");
        setNavActive(navGrades);
        refreshData();
    }

    @FXML private void showGPAView() {
        tabPane.getSelectionModel().select(gpaTab);
        pageTitle.setText("GPA and Status");
        setNavActive(navGPA);
        refreshData();
    }

    private void setNavActive(Button active) {
        navGrades.getStyleClass().remove("nav-btn-active");
        navGPA   .getStyleClass().remove("nav-btn-active");
        active.getStyleClass().add("nav-btn-active");
    }

    private String toLetter(int grade) {
        if (grade >= 90) return "A";
        if (grade >= 80) return "B";
        if (grade >= 70) return "C";
        if (grade >= 60) return "D";
        return "F";
    }

    private double toPoints(int grade) {
        if (grade >= 90) return 4.0;
        if (grade >= 80) return 3.0;
        if (grade >= 70) return 2.0;
        if (grade >= 60) return 1.0;
        return 0.0;
    }

    private String toStanding(double gpa) {
        if (gpa >= 3.5) return "Dean's List";
        if (gpa >= 3.0) return "Good Standing";
        if (gpa >= 2.0) return "Satisfactory";
        if (gpa >= 1.0) return "Academic Warning";
        return "Academic Probation";
    }

    @FXML private void handleLogout() { MainApp.loadScreen("login"); }
}