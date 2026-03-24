package Frontend;

import atlantafx.base.theme.PrimerLight;
import atlantafx.base.theme.CupertinoLight;

import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Backend.Admin;
import Backend.AdminControl;
import Backend.Teacher;
import Backend.Student;
import Backend.Course;

public class MainApp extends Application {

    private static Stage primaryStage;
    private static Scene currentScene;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        seedDemoData();
        loadScreen("login");
        primaryStage.setTitle("GPA Master");
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    // ── Theme switching ───────────────────────────────────────────────────────

    public static void setTheme(String theme) {
        switch (theme) {
            case "NordDark"       -> Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
            case "Dracula"        -> Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
            case "CupertinoDark"  -> Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
            case "PrimerDark"     -> Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            case "PrimerLight"    -> Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
            case "CupertinoLight" -> Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
        }
        if (currentScene != null) {
            currentScene.getStylesheets().clear();
            currentScene.getStylesheets().add(
                    MainApp.class.getResource("/Frontend/css/app.css").toExternalForm()
            );
        }
    }

    // ── Screen switching ──────────────────────────────────────────────────────

    public static void loadScreen(String screenName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/Frontend/fxml/" + screenName + ".fxml")
            );
            Parent root = loader.load();
            currentScene = new Scene(root, 1100, 700);
            currentScene.getStylesheets().add(
                    MainApp.class.getResource("/Frontend/css/app.css").toExternalForm()
            );
            primaryStage.setScene(currentScene);
        } catch (Exception e) {
            System.err.println("Failed to load screen: " + screenName);
            e.printStackTrace();
        }
    }

    public static <T> T loadScreenWithController(String screenName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/Frontend/fxml/" + screenName + ".fxml")
            );
            Parent root = loader.load();
            currentScene = new Scene(root, 1100, 700);
            currentScene.getStylesheets().add(
                    MainApp.class.getResource("/Frontend/css/app.css").toExternalForm()
            );
            primaryStage.setScene(currentScene);
            return loader.getController();
        } catch (Exception e) {
            System.err.println("Failed to load screen: " + screenName);
            e.printStackTrace();
            return null;
        }
    }

    public static Stage getStage() { return primaryStage; }

    // ── Seed data ─────────────────────────────────────────────────────────────

    private void seedDemoData() {
        Admin admin = new Admin(1, "Main Admin", "admin123");
        AdminControl.addAdmin(admin);

        Teacher t1 = new Teacher(101, "Mr. Brown", "teach101");
        Teacher t2 = new Teacher(102, "Ms. Green", "teach102");
        Backend.TeacherControl.addTeacher(t1);
        Backend.TeacherControl.addTeacher(t2);

        Course c1 = new Course("COMP1", "Programming 1");
        Course c2 = new Course("MATH1", "Calculus 1");
        Course c3 = new Course("COMP2", "Data Structures");
        Backend.CourseControl.addCourse(c1);
        Backend.CourseControl.addCourse(c2);
        Backend.CourseControl.addCourse(c3);

        t1.addCourse(c1);
        t1.addCourse(c3);
        t2.addCourse(c2);

        Student s1 = new Student(201, "David McLish",  "stud201");
        Student s2 = new Student(202, "Anna Baptiste", "stud202");
        Student s3 = new Student(203, "James Carter",  "stud203");
        Backend.StudentControl.addStudent(s1);
        Backend.StudentControl.addStudent(s2);
        Backend.StudentControl.addStudent(s3);

        Backend.Enrollment e1 = new Backend.Enrollment(s1, c1, 78);
        Backend.Enrollment e2 = new Backend.Enrollment(s1, c2, 85);
        Backend.Enrollment e3 = new Backend.Enrollment(s2, c1, 91);
        Backend.Enrollment e4 = new Backend.Enrollment(s3, c3, 66);
        Backend.Enrollment e5 = new Backend.Enrollment(s2, c2, 73);

        s1.addEnrollment(e1); c1.addEnrollment(e1);
        s1.addEnrollment(e2); c2.addEnrollment(e2);
        s2.addEnrollment(e3); c1.addEnrollment(e3);
        s3.addEnrollment(e4); c3.addEnrollment(e4);
        s2.addEnrollment(e5); c2.addEnrollment(e5);
    }

    public static void main(String[] args) {
        launch(args);
    }
}