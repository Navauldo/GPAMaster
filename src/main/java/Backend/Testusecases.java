package Backend;

public class Testusecases {
    public static void main(String[] args) {

        // CREATE ADMIN
        Admin admin1 = new Admin(1, "Main Admin", "admin123");
        AdminControl.addAdmin(admin1);

        // CREATE TEACHERS
        Teacher teacher1 = new Teacher(101, "Mr Brown", "teach101");
        Teacher teacher2 = new Teacher(102, "Ms Green", "teach102");

        // CREATE STUDENTS
        Student student1 = new Student(201, "David", "stud201");
        Student student2 = new Student(202, "Anna", "stud202");

        // CREATE COURSES
        Course course1 = new Course("COMP1", "Programming 1");
        Course course2 = new Course("MATH1", "Calculus 1");

        // =========================
        // TEST ADD
        // =========================
        System.out.println("=== TEST ADD ===");
        admin1.addTeacher(teacher1);
        admin1.addTeacher(teacher2);

        admin1.addStudent(student1);
        admin1.addStudent(student2);

        admin1.addCourse(course1);
        admin1.addCourse(course2);

        System.out.println("Teachers:");
        System.out.println(admin1.viewAllTeachers());

        System.out.println("Students:");
        System.out.println(admin1.viewAllStudents());

        System.out.println("Courses:");
        System.out.println(admin1.viewAllCourses());

        // =========================
        // TEST EDIT
        // =========================
        System.out.println("\n=== TEST EDIT ===");

        admin1.editTeacher(101, "Mr Smith", "newTeach101");
        admin1.editStudent(201, 211, "David McLish", "newStud201");
        admin1.editCourse("COMP1", "Introduction to Programming");

        System.out.println("Teachers after edit:");
        System.out.println(admin1.viewAllTeachers());

        System.out.println("Students after edit:");
        System.out.println(admin1.viewAllStudents());

        System.out.println("Courses after edit:");
        System.out.println(admin1.viewAllCourses());

        // =========================
        // TEST DELETE
        // =========================
        System.out.println("\n=== TEST DELETE ===");

        admin1.deleteTeacher(102);
        admin1.deleteStudent(202);
        admin1.deleteCourse("MATH1");

        System.out.println("Teachers after delete:");
        System.out.println(admin1.viewAllTeachers());

        System.out.println("Students after delete:");
        System.out.println(admin1.viewAllStudents());

        System.out.println("Courses after delete:");
        System.out.println(admin1.viewAllCourses());
    }
}


///GPA Master 1.0   =>   ABOVE
///- Admin add/edit/delete student
///- Admin add/edit/delete teacher
///- Admin add/edit/delete course
///- Basic control structure working



