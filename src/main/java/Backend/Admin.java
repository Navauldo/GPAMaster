package Backend;

import java.util.ArrayList;

public class Admin extends User {

    public Admin(int id, String name, String password){
        super(id, name, password);
    }

    // ADMIN MANAGEMENT OF OTHER ADMINS
    public void addAdmin(Admin admin){
        AdminControl.addAdmin(admin);
    }

    public void editAdmin(int adminId, String newName, String newPassword){
        AdminControl.editAdmin(adminId, newName, newPassword);
    }

    public void deleteAdmin(int adminId){
        AdminControl.deleteAdmin(adminId);
    }

    // STUDENT MANAGEMENT
    public void addStudent(Student student){
        StudentControl.addStudent(student);
    }

    public void editStudent(int id, int newId, String name, String password){
        StudentControl.editStudent(id, newId, name, password);
    }

    public void deleteStudent(int id){
        StudentControl.deleteStudent(id);
    }

    // TEACHER MANAGEMENT
    public void addTeacher(Teacher teacher){
        TeacherControl.addTeacher(teacher);
    }

    public void editTeacher(int teacherId, String newName, String newPassword){
        TeacherControl.editTeacher(teacherId, newName, newPassword);
    }

    public void deleteTeacher(int teacherId){
        TeacherControl.deleteTeacher(teacherId);
    }

    // COURSE MANAGEMENT
    public void addCourse(Course course){
        CourseControl.addCourse(course);
    }

    public void editCourse(String courseCode, String newName){
        CourseControl.editCourse(courseCode, newName);
    }

    public void deleteCourse(String courseCode){
        CourseControl.deleteCourse(courseCode);
    }

    // VIEW METHODS
    public ArrayList<Student> viewAllStudents(){
        return StudentControl.getEStudents();
    }

    public ArrayList<Teacher> viewAllTeachers(){
        return TeacherControl.getAllTeachers();
    }

    public ArrayList<Course> viewAllCourses(){
        return CourseControl.getAllCourses();
    }

    // PLACEHOLDERS FOR LATER
    public void saveSystem(String filePath){
        System.out.println("Save system not implemented yet.");
    }

    public void loadSystem(String filePath){
        System.out.println("Load system not implemented yet.");
    }
}