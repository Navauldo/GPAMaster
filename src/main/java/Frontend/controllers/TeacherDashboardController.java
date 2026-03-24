package Frontend.controllers;

import Frontend.MainApp;
import Backend.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class TeacherDashboardController {

    @FXML private Label     teacherNameLabel;
    @FXML private Label     pageTitle;
    @FXML private TabPane   tabPane;
    @FXML private Tab       studentsTab;
    @FXML private Tab       gradesTab;
    @FXML private Button    navMyStudents;
    @FXML private Button    navGrades;
    @FXML private ComboBox<String> themePicker;

    @FXML private Label statStudents;
    @FXML private Label statCourses;
    @FXML private Label statGrades;

    @FXML private TableView<Enrollment>          studentTable;
    @FXML private TableColumn<Enrollment,String> colId;
    @FXML private TableColumn<Enrollment,String> colName;
    @FXML private TableColumn<Enrollment,String> colCourse;
    @FXML private TableColumn<Enrollment,String> colGrade;

    @FXML private TextField gradeStudId;
    @FXML private TextField gradeCode;
    @FXML private TextField gradeValue;
    @FXML private Label     gradeMsg;

    private Teacher loggedTeacher;
    private ObservableList<Enrollment> enrollmentData = FXCollections.observableArrayList();

    public void initData(Teacher teacher) {
        this.loggedTeacher = teacher;
        teacherNameLabel.setText(teacher.getName());
        setupTable();
        refreshStudents();
        setupThemePicker();
        showStudentsView();
    }

    private void setupThemePicker() {
        themePicker.getItems().addAll(
                "NordDark", "Dracula", "CupertinoDark", "PrimerDark", "PrimerLight", "CupertinoLight"
        );
        themePicker.setValue("NordDark");
        themePicker.setOnAction(e -> MainApp.setTheme(themePicker.getValue()));
    }

    private void setupTable() {
        colId    .setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getStudent().getId())));
        colName  .setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getStudent().getName()));
        colCourse.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getCourse().getCCode() + " — " + c.getValue().getCourse().getCName()));
        colGrade .setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getGrade())));
        studentTable.setItems(enrollmentData);
    }

    private void refreshStudents() {
        enrollmentData.clear();
        for (Course course : loggedTeacher.getCoursesTaught()) {
            enrollmentData.addAll(course.getEnrollments());
        }
        refreshStats();
    }

    private void refreshStats() {
        statStudents.setText(String.valueOf(
                loggedTeacher.getCoursesTaught().stream()
                        .mapToInt(c -> c.getStudentsEnrolled().size()).sum()));
        statCourses.setText(String.valueOf(loggedTeacher.getCoursesTaught().size()));
        statGrades .setText(String.valueOf(enrollmentData.size()));
    }

    @FXML private void showStudentsView() {
        tabPane.getSelectionModel().select(studentsTab);
        pageTitle.setText("My Students");
        setNavActive(navMyStudents);
        refreshStudents();
    }

    @FXML private void showGradesView() {
        tabPane.getSelectionModel().select(gradesTab);
        pageTitle.setText("Input Grades");
        setNavActive(navGrades);
    }

    private void setNavActive(Button active) {
        navMyStudents.getStyleClass().remove("nav-btn-active");
        navGrades    .getStyleClass().remove("nav-btn-active");
        active.getStyleClass().add("nav-btn-active");
    }

    @FXML private void handleSubmitGrade() {
        gradeMsg.setVisible(false);
        gradeMsg.setManaged(false);
        String studIdStr  = gradeStudId.getText().trim();
        String courseCode = gradeCode.getText().trim();
        String gradeStr   = gradeValue.getText().trim();
        if (studIdStr.isEmpty() || courseCode.isEmpty() || gradeStr.isEmpty()) {
            showMsg("Please fill in all fields.", false); return;
        }
        int studentId, grade;
        try {
            studentId = Integer.parseInt(studIdStr);
            grade     = Integer.parseInt(gradeStr);
        } catch (NumberFormatException e) {
            showMsg("Student ID and grade must be numbers.", false); return;
        }
        if (grade < 0 || grade > 100) {
            showMsg("Grade must be between 0 and 100.", false); return;
        }
        TeacherControl.updateStudentGrade(loggedTeacher.getId(), studentId, courseCode, grade);
        showMsg("Grade updated successfully!", true);
        refreshStudents();
        gradeStudId.clear(); gradeCode.clear(); gradeValue.clear();
    }

    private void showMsg(String msg, boolean success) {
        gradeMsg.setText(msg);
        gradeMsg.setStyle(success
                ? "-fx-text-fill: -color-success-fg;"
                : "-fx-text-fill: -color-danger-fg;");
        gradeMsg.setVisible(true);
        gradeMsg.setManaged(true);
    }

    @FXML private void handleLogout() { MainApp.loadScreen("login"); }
}