package Backend;

import java.util.ArrayList;

public class TeacherControl {
    static ArrayList<Teacher> eteachers= new ArrayList<>();


    public TeacherControl(){}

    static public void addTeacher(Teacher teacher){eteachers.add(teacher);}
    //edit and delete may want to have newid for editing field as well
    public static void editTeacher(int teacherId, String newName, String newPassword)
    {
        Teacher teacher = findTeacherById(teacherId);

        if (teacher == null)
        {
            System.out.println("Teacher does not exist.");
            return;
        }

        teacher.setName(newName);
        teacher.setPassword(newPassword);
    }
    public static void deleteTeacher(int teacherId)
    {
        Teacher teacher = findTeacherById(teacherId);

        if (teacher == null)
        {
            System.out.println("Teacher does not exist.");
            return;
        }

        eteachers.remove(teacher);
    }



    static public Teacher findTeacherById(int teacherId)
    {
        Teacher returnable=null;
        for(Teacher teacher: eteachers)
        {
            if(teacher.getId()==teacherId){returnable=teacher;}
        }
        return returnable;
    }

    public static ArrayList<Teacher> getAllTeachers(){return eteachers;}

    public static ArrayList<Student> getTeacherStudents(int teacherId)
    {
        ArrayList<Student> StudentsTaught = new ArrayList<>();
        Teacher teacher = TeacherControl.findTeacherById(teacherId);
        if(teacher==null)
        {
            System.out.println("teacher does not exist");
            return StudentsTaught;
        }

        //second if not needed just couldn't bother to take it out....
        if(teacher!=null)
        {
            ArrayList<Course> coursesfind = teacher.getCoursesTaught();
            ArrayList<ArrayList<Student>> studentsTaughtInEachCourse = new ArrayList<>();

            for (Course c : coursesfind) {
                studentsTaughtInEachCourse.add(c.getStudentsEnrolled());
            }
            for (ArrayList<Student> s : studentsTaughtInEachCourse) {
                StudentsTaught.addAll(s);
            }
        }
        return StudentsTaught;
    }



    public static void updateStudentGrade(int teacherId, int studentId, String courseCode, int newGrade)
    {
        Teacher teacher = findTeacherById(teacherId);
        if (teacher == null) {
            System.out.println("Teacher does not exist.");
            return;
        }

        Student student = StudentControl.getStudent(studentId);
        if (student == null) {
            System.out.println("Student does not exist.");
            return;
        }

        Course course = null;
        for (Course c : teacher.getCoursesTaught()) {
            if (c.getCCode().equals(courseCode)) {
                course = c;
                break;
            }
        }

        if (course == null) {
            System.out.println("Course does not exist for this teacher.");
            return;
        }

        teacher.updateStudentGrade(student, course, newGrade);
    }

    public static Student viewStudentAcademicInfo(int teacherId, int studentId)
    {
        Teacher teacher = findTeacherById(teacherId);
        if (teacher == null) {
            System.out.println("Teacher does not exist.");
            return null;
        }

        Student student = StudentControl.getStudent(studentId);
        if (student == null) {
            System.out.println("Student does not exist.");
            return null;
        }

        boolean taughtByTeacher = false;

        for (Course c : teacher.getCoursesTaught()) {
            for (Student s : c.getStudentsEnrolled()) {
                if (s.getId() == studentId) {
                    taughtByTeacher = true;
                    break;
                }
            }
            if (taughtByTeacher) {
                break;
            }
        }

        if (!taughtByTeacher) {
            System.out.println("This teacher does not teach this student.");
            return null;
        }

        return student;
    }





//+updateStudentGrade(teacherId: int, studentId: int, courseCode: String, newGrade: int): void
//+viewStudentAcademicInfo(teacherId: int, studentId: int): Student

}
