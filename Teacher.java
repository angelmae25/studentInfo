package models;

import java.util.ArrayList;

public class Teacher extends Person {
    private ArrayList<Student> students;

    public Teacher(String id, String name, String email, String password) {
        super(id, name, email, password);
        this.students = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() { return students; }
    public void addStudent(Student s) { students.add(s); }

    @Override
    public String getRole() { return "Teacher"; }
}
