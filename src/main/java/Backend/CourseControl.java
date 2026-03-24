package Backend;

import java.util.ArrayList;

public class CourseControl {
    static ArrayList<Course> ecourses = new ArrayList<>();

    // CONSTRUCTOR, SINCE FUNCTIONS ARE STATIC WE DONT NEED CONSTRUCTOR REALLY...
    public CourseControl(){}


    public static Course findCourseByCode(String coursecode)
    {
        Course course = null;
        for(Course c : ecourses)
        {
            if(c.getCCode().equals(coursecode))
            {
                course = c;
            }
        }
        return course;
    }

    public static ArrayList<Course> getAllCourses(){
        return ecourses;
    }

    public static void addCourse(Course course){
        if(isCourseCodeUnique(course.getCCode()))
        {
            ecourses.add(course);
        }
        else
        {
            System.out.println("Course code already exists.");
        }
    }

    public static void editCourse(String coursecode, String newName)
    {
        Course course = findCourseByCode(coursecode);

        if(course == null)
        {
            System.out.println("Course does not exist.");
            return;
        }

        course.setCName(newName);
    }

    public static void deleteCourse(String coursecode)
    {
        Course course = findCourseByCode(coursecode);

        if(course == null)
        {
            System.out.println("Course does not exist.");
            return;
        }

        ecourses.remove(course);
    }

    public static boolean isCourseCodeUnique(String coursecode)
    {
        return findCourseByCode(coursecode) == null;
    }
}