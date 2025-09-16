import org.json.JSONObject;

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

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("subjectName", subjectName);
        obj.put("grade", grade);
        obj.put("schedule", schedule);
        return obj;
    }

    @Override
    public String toString() {
        return String.format("%s | Grade: %.2f | Schedule: %s", subjectName, grade, schedule);
    }
}
