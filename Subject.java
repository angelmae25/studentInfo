package models;

public class Subject {
    private String subjectName;
    private double grade;
    private String schedule;

    public Subject(String subjectName, double grade, String schedule) {
        this.subjectName = subjectName;
        this.grade = grade;
        this.schedule = schedule;
    }

    public String getSubjectName() { return subjectName; }
    public double getGrade() { return grade; }
    public String getSchedule() { return schedule; }

    public void setGrade(double grade) { this.grade = grade; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
}
