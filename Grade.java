public class Grade {
    private String subject;
    private double grade; // 1.0 - 5.0

    public Grade(String subject, double grade) {
        this.subject = subject;
        setGrade(grade);
    }

    public String getSubject() { return subject; }
    public double getGrade() { return grade; }

    public void setGrade(double grade) {
        if (grade >= 1.0 && grade <= 5.0) {
            this.grade = grade;
        } else {
            throw new IllegalArgumentException("Grade must be between 1.0 (highest) and 5.0 (failed).");
        }
    }

    @Override
    public String toString() {
        return subject + ": " + grade;
    }
}
