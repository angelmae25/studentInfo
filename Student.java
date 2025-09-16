import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Student {
    private String name;
    private String course;
    private int yearLevel;
    private String email;
    private String password;

    private ArrayList<Subject> subjects; // subjects with grades + schedule

    public Student(String name, String course, int yearLevel, String email, String password) {
        this.name = name;
        this.course = course;
        this.yearLevel = yearLevel;
        this.email = email;
        this.password = password;
        this.subjects = new ArrayList<>();
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getYearLevel() { return yearLevel; }
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }
    public boolean checkPassword(String input) { return this.password.equals(input); }

    // Subject Management
    public void addSubject(Subject subject) { subjects.add(subject); }
    public ArrayList<Subject> getSubjects() { return subjects; }

    public double getAverage() {
        if (subjects.isEmpty()) return 0.0;
        double sum = 0;
        for (Subject s : subjects) sum += s.getGrade();
        return sum / subjects.size();
    }

    public abstract String getType();

    // Save student info + subjects to JSON
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("course", course);
        obj.put("yearLevel", yearLevel);
        obj.put("type", getType());
        obj.put("email", email);
        obj.put("password", password);

        JSONArray subjArr = new JSONArray();
        for (Subject s : subjects) subjArr.put(s.toJSON());
        obj.put("subjects", subjArr);

        return obj;
    }

    public String getDetails() {
        return String.format("Name: %s | Course: %s | Year: %d | Type: %s | Avg: %.2f | Email: %s",
                name, course, yearLevel, getType(), getAverage(), email);
    }
}
