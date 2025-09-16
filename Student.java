package models;

import java.util.ArrayList;

public class Student extends Person {
    private String course;
    private int yearLevel;
    private String type; // Regular, Irregular, Transferee
    private ArrayList<Subject> subjects;

    public Student(String id, String name, String email, String password, String course, int yearLevel, String type) {
        super(id, name, email, password);
        this.course = course;
        this.yearLevel = yearLevel;
        this.type = type;
        this.subjects = new ArrayList<>();
    }

    public String getCourse() { return course; }
    public int getYearLevel() { return yearLevel; }
    public String getType() { return type; }
    public ArrayList<Subject> getSubjects() { return subjects; }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    @Override
    public String getRole() { return "Student"; }
}
