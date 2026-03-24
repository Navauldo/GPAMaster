package Backend;

import java.util.ArrayList;

public class StudentControl {
    static private ArrayList<Student> estudents= new ArrayList<>();

    //CONSTRUCTOR
    public StudentControl(){}


    //METHODS BELOW
    public static void addStudent(Student s){estudents.add(s);}


    public static ArrayList<Student> getEStudents(){return StudentControl.estudents;}
    public static Student getStudent(int id)
    {
        Student returnable = null;
        for(Student st: estudents)
        {
            //.equals()???? couldnt cuz of primitive typing, may be a better way to implement then.
            if(st.getId()==id)
            {
                returnable=st;
            }
        }
        return returnable;
    }

    public static void editStudent(int id, int newid, String name, String password)
    {
        Student student=StudentControl.getStudent(id);

        student.setId(newid); student.setName(name); student.setPassword(password);
    }

    public static void deleteStudent(int id)
    {
        Student todeletestudent= StudentControl.getStudent(id);
        StudentControl.getEStudents().remove(todeletestudent);
    }
}
