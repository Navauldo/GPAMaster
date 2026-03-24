package Frontend.controllers;

import Frontend.MainApp;
import Backend.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Optional;
import java.util.stream.Collectors;

public class AdminDashboardController {

    @FXML private Label     adminNameLabel;
    @FXML private Label     pageTitle;
    @FXML private Button    addBtn;
    @FXML private TabPane   tabPane;
    @FXML private ComboBox<String> themePicker;

    @FXML private Tab studentsTab;
    @FXML private Tab teachersTab;
    @FXML private Tab coursesTab;
    @FXML private Tab gradesTab;

    @FXML private Button navStudents;
    @FXML private Button navTeachers;
    @FXML private Button navCourses;
    @FXML private Button navGrades;

    @FXML private Label statStudents;
    @FXML private Label statTeachers;
    @FXML private Label statCourses;
    @FXML private Label statEnrollments;

    @FXML private TableView<Student>          studentTable;
    @FXML private TableColumn<Student,String> colStudId;
    @FXML private TableColumn<Student,String> colStudName;
    @FXML private TableColumn<Student,String> colStudEnr;
    @FXML private TableColumn<Student,String> colStudAct;
    @FXML private TextField                   studentSearch;

    @FXML private TableView<Teacher>          teacherTable;
    @FXML private TableColumn<Teacher,String> colTeachId;
    @FXML private TableColumn<Teacher,String> colTeachName;
    @FXML private TableColumn<Teacher,String> colTeachCourses;
    @FXML private TableColumn<Teacher,String> colTeachAct;
    @FXML private TextField                   teacherSearch;

    @FXML private TableView<Course>          courseTable;
    @FXML private TableColumn<Course,String> colCourseCode;
    @FXML private TableColumn<Course,String> colCourseName;
    @FXML private TableColumn<Course,String> colCourseEnr;
    @FXML private TableColumn<Course,String> colCourseAct;
    @FXML private TextField                  courseSearch;

    @FXML private TextField gradeStudId;
    @FXML private TextField gradeCode;
    @FXML private TextField gradeValue;
    @FXML private Label     adminGradeMsg;

    private Admin loggedAdmin;
    private ObservableList<Student> studentData = FXCollections.observableArrayList();
    private ObservableList<Teacher> teacherData = FXCollections.observableArrayList();
    private ObservableList<Course>  courseData  = FXCollections.observableArrayList();

    public void initData(Admin admin) {
        this.loggedAdmin = admin;
        adminNameLabel.setText(admin.getName());
        setupTables();
        refreshAll();
        setupThemePicker();
        showStudentsTab();
    }

    private void setupThemePicker() {
        themePicker.getItems().addAll(
                "NordDark", "Dracula", "CupertinoDark", "PrimerDark", "PrimerLight", "CupertinoLight"
        );
        themePicker.setValue("NordDark");
        themePicker.setOnAction(e -> MainApp.setTheme(themePicker.getValue()));
    }

    private void setupTables() {
        colStudId  .setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getId())));
        colStudName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colStudEnr .setCellValueFactory(c -> {
            String courses = c.getValue().getEnrollments().stream()
                    .map(e -> e.getCourse().getCCode())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(courses.isEmpty() ? "—" : courses);
        });
        colStudAct.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn   = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            {
                editBtn  .setStyle("-fx-font-size:11px;");
                deleteBtn.setStyle("-fx-font-size:11px;");
                editBtn  .setOnAction(e -> editStudent(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> deleteStudent(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setGraphic(null); return; }
                setGraphic(new HBox(6, editBtn, deleteBtn));
            }
        });
        studentTable.setItems(studentData);

        colTeachId     .setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getId())));
        colTeachName   .setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colTeachCourses.setCellValueFactory(c -> {
            String courses = c.getValue().getCoursesTaught().stream()
                    .map(Course::getCCode).collect(Collectors.joining(", "));
            return new SimpleStringProperty(courses.isEmpty() ? "—" : courses);
        });
        colTeachAct.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn   = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            {
                editBtn  .setStyle("-fx-font-size:11px;");
                deleteBtn.setStyle("-fx-font-size:11px;");
                editBtn  .setOnAction(e -> editTeacher(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> deleteTeacher(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setGraphic(null); return; }
                setGraphic(new HBox(6, editBtn, deleteBtn));
            }
        });
        teacherTable.setItems(teacherData);

        colCourseCode.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCCode()));
        colCourseName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCName()));
        colCourseEnr .setCellValueFactory(c -> new SimpleStringProperty(
                String.valueOf(c.getValue().getStudentsEnrolled().size())));
        colCourseAct.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn   = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            {
                editBtn  .setStyle("-fx-font-size:11px;");
                deleteBtn.setStyle("-fx-font-size:11px;");
                editBtn  .setOnAction(e -> editCourse(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> deleteCourse(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) { setGraphic(null); return; }
                setGraphic(new HBox(6, editBtn, deleteBtn));
            }
        });
        courseTable.setItems(courseData);
    }

    @FXML private void showStudentsTab() {
        tabPane.getSelectionModel().select(studentsTab);
        pageTitle.setText("Students");
        addBtn.setText("+ Add Student");
        addBtn.setVisible(true);
        setNavActive(navStudents);
        refreshStudents();
    }

    @FXML private void showTeachersTab() {
        tabPane.getSelectionModel().select(teachersTab);
        pageTitle.setText("Teachers");
        addBtn.setText("+ Add Teacher");
        addBtn.setVisible(true);
        setNavActive(navTeachers);
        refreshTeachers();
    }

    @FXML private void showCoursesTab() {
        tabPane.getSelectionModel().select(coursesTab);
        pageTitle.setText("Courses");
        addBtn.setText("+ Add Course");
        addBtn.setVisible(true);
        setNavActive(navCourses);
        refreshCourses();
    }

    @FXML private void showGradesTab() {
        tabPane.getSelectionModel().select(gradesTab);
        pageTitle.setText("Input Grades");
        addBtn.setVisible(false);
        setNavActive(navGrades);
    }

    private void setNavActive(Button active) {
        navStudents.getStyleClass().remove("nav-btn-active");
        navTeachers.getStyleClass().remove("nav-btn-active");
        navCourses .getStyleClass().remove("nav-btn-active");
        navGrades  .getStyleClass().remove("nav-btn-active");
        active.getStyleClass().add("nav-btn-active");
    }

    @FXML private void handleAdd() {
        Tab selected = tabPane.getSelectionModel().getSelectedItem();
        if      (selected == studentsTab) addStudent();
        else if (selected == teachersTab) addTeacher();
        else                              addCourse();
    }

    @FXML private void filterStudents() {
        String q = studentSearch.getText().toLowerCase();
        studentData.setAll(StudentControl.getEStudents().stream()
                .filter(s -> s.getName().toLowerCase().contains(q)
                        || String.valueOf(s.getId()).contains(q))
                .collect(Collectors.toList()));
    }

    @FXML private void filterTeachers() {
        String q = teacherSearch.getText().toLowerCase();
        teacherData.setAll(TeacherControl.getAllTeachers().stream()
                .filter(t -> t.getName().toLowerCase().contains(q)
                        || String.valueOf(t.getId()).contains(q))
                .collect(Collectors.toList()));
    }

    @FXML private void filterCourses() {
        String q = courseSearch.getText().toLowerCase();
        courseData.setAll(CourseControl.getAllCourses().stream()
                .filter(c -> c.getCName().toLowerCase().contains(q)
                        || c.getCCode().toLowerCase().contains(q))
                .collect(Collectors.toList()));
    }

    private void refreshAll() {
        refreshStudents();
        refreshTeachers();
        refreshCourses();
        refreshStats();
    }

    private void refreshStudents() { studentData.setAll(StudentControl.getEStudents()); }
    private void refreshTeachers() { teacherData.setAll(TeacherControl.getAllTeachers()); }
    private void refreshCourses()  { courseData.setAll(CourseControl.getAllCourses()); }

    private void refreshStats() {
        statStudents.setText(String.valueOf(StudentControl.getEStudents().size()));
        statTeachers.setText(String.valueOf(TeacherControl.getAllTeachers().size()));
        statCourses .setText(String.valueOf(CourseControl.getAllCourses().size()));
        int enrollments = StudentControl.getEStudents().stream()
                .mapToInt(s -> s.getEnrollments().size()).sum();
        statEnrollments.setText(String.valueOf(enrollments));
    }

    private void addStudent() {
        buildStudentDialog("Add Student", null).showAndWait().ifPresent(s -> {
            loggedAdmin.addStudent(s);
            refreshAll();
        });
    }

    private void editStudent(Student s) {
        buildStudentDialog("Edit Student", s).showAndWait().ifPresent(updated -> {
            loggedAdmin.editStudent(s.getId(), updated.getId(), updated.getName(), updated.getPassword());
            refreshAll();
        });
    }

    private void deleteStudent(Student s) {
        if (confirmDelete("student", s.getName())) {
            loggedAdmin.deleteStudent(s.getId());
            refreshAll();
        }
    }

    private Dialog<Student> buildStudentDialog(String title, Student existing) {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField idField   = new TextField(existing != null ? String.valueOf(existing.getId()) : "");
        TextField nameField = new TextField(existing != null ? existing.getName() : "");
        PasswordField passField = new PasswordField();
        if (existing != null) passField.setText(existing.getPassword());
        grid.add(new Label("ID:"),       0, 0); grid.add(idField,   1, 0);
        grid.add(new Label("Name:"),     0, 1); grid.add(nameField, 1, 1);
        grid.add(new Label("Password:"), 0, 2); grid.add(passField, 1, 2);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return new Student(
                            Integer.parseInt(idField.getText().trim()),
                            nameField.getText().trim(),
                            passField.getText().trim()
                    );
                } catch (NumberFormatException ex) {
                    showAlert("Invalid ID", "ID must be a number.");
                }
            }
            return null;
        });
        return dialog;
    }

    private void addTeacher() {
        buildTeacherDialog("Add Teacher", null).showAndWait().ifPresent(t -> {
            loggedAdmin.addTeacher(t);
            refreshAll();
        });
    }

    private void editTeacher(Teacher t) {
        buildTeacherDialog("Edit Teacher", t).showAndWait().ifPresent(updated -> {
            loggedAdmin.editTeacher(t.getId(), updated.getName(), updated.getPassword());
            refreshAll();
        });
    }

    private void deleteTeacher(Teacher t) {
        if (confirmDelete("teacher", t.getName())) {
            loggedAdmin.deleteTeacher(t.getId());
            refreshAll();
        }
    }

    private Dialog<Teacher> buildTeacherDialog(String title, Teacher existing) {
        Dialog<Teacher> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField idField   = new TextField(existing != null ? String.valueOf(existing.getId()) : "");
        TextField nameField = new TextField(existing != null ? existing.getName() : "");
        PasswordField passField = new PasswordField();
        if (existing != null) passField.setText(existing.getPassword());
        idField.setDisable(existing != null);
        grid.add(new Label("ID:"),       0, 0); grid.add(idField,   1, 0);
        grid.add(new Label("Name:"),     0, 1); grid.add(nameField, 1, 1);
        grid.add(new Label("Password:"), 0, 2); grid.add(passField, 1, 2);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    int id = existing != null ? existing.getId()
                            : Integer.parseInt(idField.getText().trim());
                    return new Teacher(id, nameField.getText().trim(), passField.getText().trim());
                } catch (NumberFormatException ex) {
                    showAlert("Invalid ID", "ID must be a number.");
                }
            }
            return null;
        });
        return dialog;
    }

    private void addCourse() {
        buildCourseDialog("Add Course", null).showAndWait().ifPresent(c -> {
            loggedAdmin.addCourse(c);
            refreshAll();
        });
    }

    private void editCourse(Course c) {
        buildCourseDialog("Edit Course", c).showAndWait().ifPresent(updated -> {
            loggedAdmin.editCourse(c.getCCode(), updated.getCName());
            refreshAll();
        });
    }

    private void deleteCourse(Course c) {
        if (confirmDelete("course", c.getCName())) {
            loggedAdmin.deleteCourse(c.getCCode());
            refreshAll();
        }
    }

    private Dialog<Course> buildCourseDialog(String title, Course existing) {
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20));
        TextField codeField = new TextField(existing != null ? existing.getCCode() : "");
        TextField nameField = new TextField(existing != null ? existing.getCName() : "");
        codeField.setDisable(existing != null);
        grid.add(new Label("Course Code:"), 0, 0); grid.add(codeField, 1, 0);
        grid.add(new Label("Course Name:"), 0, 1); grid.add(nameField, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> btn == ButtonType.OK
                ? new Course(codeField.getText().trim(), nameField.getText().trim())
                : null);
        return dialog;
    }

    @FXML private void handleAdminGrade() {
        adminGradeMsg.setVisible(false);
        adminGradeMsg.setManaged(false);
        String studIdStr  = gradeStudId.getText().trim();
        String courseCode = gradeCode.getText().trim();
        String gradeStr   = gradeValue.getText().trim();
        if (studIdStr.isEmpty() || courseCode.isEmpty() || gradeStr.isEmpty()) {
            showAdminGradeMsg("Please fill in all fields.", false); return;
        }
        int studentId, grade;
        try {
            studentId = Integer.parseInt(studIdStr);
            grade     = Integer.parseInt(gradeStr);
        } catch (NumberFormatException e) {
            showAdminGradeMsg("Student ID and grade must be numbers.", false); return;
        }
        if (grade < 0 || grade > 100) {
            showAdminGradeMsg("Grade must be between 0 and 100.", false); return;
        }
        AdminControl.inputStudentGrade(studentId, courseCode, grade);
        showAdminGradeMsg("Grade updated successfully!", true);
        gradeStudId.clear(); gradeCode.clear(); gradeValue.clear();
        refreshStats();
    }

    private void showAdminGradeMsg(String msg, boolean success) {
        adminGradeMsg.setText(msg);
        adminGradeMsg.setStyle(success
                ? "-fx-text-fill: -color-success-fg;"
                : "-fx-text-fill: -color-danger-fg;");
        adminGradeMsg.setVisible(true);
        adminGradeMsg.setManaged(true);
    }

    private boolean confirmDelete(String type, String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete " + type + "?");
        alert.setContentText("Are you sure you want to delete \"" + name + "\"?");
        return alert.showAndWait().filter(b -> b == ButtonType.OK).isPresent();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    @FXML private void handleLogout() { MainApp.loadScreen("login"); }
}