package Backend;

import java.util.ArrayList;

public class AdminControl {

    private static ArrayList<Admin> eadmins = new ArrayList<>();

    public AdminControl(){}

    public static void addAdmin(Admin admin){
        eadmins.add(admin);
    }

    public static Admin findAdminById(int adminId){
        Admin returnable = null;
        for(Admin admin : eadmins){
            if(admin.getId() == adminId){
                returnable = admin;
            }
        }
        return returnable;
    }

    public static ArrayList<Admin> getAllAdmins(){
        return eadmins;
    }

    public static void editAdmin(int adminId, String newName, String newPassword){
        Admin admin = findAdminById(adminId);

        if(admin == null){
            System.out.println("Admin does not exist.");
            return;
        }

        admin.setName(newName);
        admin.setPassword(newPassword);
    }

    public static void deleteAdmin(int adminId){
        Admin admin = findAdminById(adminId);

        if(admin == null){
            System.out.println("Admin does not exist.");
            return;
        }

        eadmins.remove(admin);
    }

    public static void inputStudentGrade(int studentId, String courseCode, int grade) {
        Student student = StudentControl.getStudent(studentId);
        if (student == null) {
            System.out.println("Student does not exist.");
            return;
        }

        Course course = CourseControl.findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Course does not exist.");
            return;
        }

        if (grade < 0 || grade > 100) {
            System.out.println("Invalid grade.");
            return;
        }

        Enrollment enrollment = student.getEnrollmentForCourse(course);
        if (enrollment == null) {
            System.out.println("Student not enrolled in this course.");
            return;
        }

        enrollment.setGrade(grade);
    }
}