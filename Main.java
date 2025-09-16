import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

// ===== ABSTRACTION =====
abstract class Person {
    private String id;
    private String name;
    private String email;
    private String password;

    public Person(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // ENCAPSULATION
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public abstract String getRole(); // ABSTRACTION
}

// ===== INHERITANCE =====
class Student extends Person {
    private String course;
    private int yearLevel;
    private String type; // Regular / Irregular / Transferee
    private ArrayList<Subject> subjects = new ArrayList<>();

    public Student(String id, String name, String email, String password,
                   String course, int yearLevel, String type) {
        super(id, name, email, password);
        this.course = course;
        this.yearLevel = yearLevel;
        this.type = type;
    }

    public String getCourse() { return course; }
    public int getYearLevel() { return yearLevel; }
    public String getType() { return type; }
    public ArrayList<Subject> getSubjects() { return subjects; }

    public void addSubject(Subject subject) { subjects.add(subject); }

    @Override
    public String getRole() { return "Student"; } // POLYMORPHISM
}

class Teacher extends Person {
    private ArrayList<Student> students = new ArrayList<>();

    public Teacher(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    public ArrayList<Student> getStudents() { return students; }
    public void addStudent(Student s) { students.add(s); }

    @Override
    public String getRole() { return "Teacher"; } // POLYMORPHISM
}

class Subject {
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

// ===== MAIN SYSTEM =====
public class Main {
    private static ArrayList<Teacher> teachers = new ArrayList<>();
    private static ArrayList<Student> students = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static final String FILE = "system.json";

    public static void main(String[] args) {
        loadData();

        while (true) {
            System.out.println("\n==== MAIN MENU ====");
            System.out.println("1. Teacher");
            System.out.println("2. Student");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) teacherMenu();
            else if (choice == 2) studentMenu();
            else if (choice == 3) {
                saveData();
                System.out.println("System saved. Goodbye!");
                break;
            }
        }
    }

    // ===== TEACHER MENU =====
    private static void teacherMenu() {
        while (true) {
            System.out.println("\n--- Teacher Menu ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) registerTeacher();
            else if (choice == 2) loginTeacher();
            else break;
        }
    }

    private static void registerTeacher() {
        System.out.print("Enter Teacher ID: "); String id = sc.nextLine();
        System.out.print("Enter Name: "); String name = sc.nextLine();
        System.out.print("Enter Email: "); String email = sc.nextLine();
        System.out.print("Enter Password: "); String pass = sc.nextLine();
        teachers.add(new Teacher(id, name, email, pass));
        saveData();
        System.out.println("Teacher registered!");
    }

    private static void loginTeacher() {
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Password: "); String pass = sc.nextLine();
        for (Teacher t : teachers) {
            if (t.getEmail().equals(email) && t.getPassword().equals(pass)) {
                teacherDashboard(t);
                return;
            }
        }
        System.out.println("Invalid login.");
    }

    private static void teacherDashboard(Teacher t) {
        while (true) {
            System.out.println("\n--- Teacher Dashboard ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Student Info");
            System.out.println("3. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) addStudent(t);
            else if (choice == 2) viewStudents();
            else break;
        }
    }

    private static void addStudent(Teacher t) {
        System.out.print("Student ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Password: "); String pass = sc.nextLine();
        System.out.print("Course: "); String course = sc.nextLine();
        System.out.print("Year Level: "); int year = sc.nextInt(); sc.nextLine();
        System.out.print("Type (Regular/Irregular/Transferee): "); String type = sc.nextLine();

        Student s = new Student(id, name, email, pass, course, year, type);
        students.add(s);
        t.addStudent(s);
        saveData();
        System.out.println("Student added!");
    }

    private static void viewStudents() {
        for (Student s : students) {
            System.out.println(s.getId() + " | " + s.getName() + " | " + s.getCourse() + " | " + s.getType());
        }
    }

    // ===== STUDENT MENU =====
    private static void studentMenu() {
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) registerStudent();
            else if (choice == 2) loginStudent();
            else break;
        }
    }

    private static void registerStudent() {
        System.out.print("Student ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Password: "); String pass = sc.nextLine();
        System.out.print("Course: "); String course = sc.nextLine();
        System.out.print("Year Level: "); int year = sc.nextInt(); sc.nextLine();
        System.out.print("Type (Regular/Irregular/Transferee): "); String type = sc.nextLine();

        students.add(new Student(id, name, email, pass, course, year, type));
        saveData();
        System.out.println("Student registered!");
    }

    private static void loginStudent() {
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Password: "); String pass = sc.nextLine();
        for (Student s : students) {
            if (s.getEmail().equals(email) && s.getPassword().equals(pass)) {
                studentDashboard(s);
                return;
            }
        }
        System.out.println("Invalid login.");
    }

    private static void studentDashboard(Student s) {
        while (true) {
            System.out.println("\n--- Student Dashboard ---");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            if (choice == 1) {
                System.out.println(s.getId() + " | " + s.getName() + " | " +
                        s.getCourse() + " | " + s.getYearLevel() + " | " + s.getType());
            } else if (choice == 2) {
                System.out.print("New Name: "); s.setName(sc.nextLine());
                System.out.print("New Email: "); s.setEmail(sc.nextLine());
                System.out.print("New Password: "); s.setPassword(sc.nextLine());
                saveData();
            } else break;
        }
    }

    // ===== JSON SAVE / LOAD =====
    private static void saveData() {
        JSONObject root = new JSONObject();
        JSONArray tArr = new JSONArray();
        for (Teacher t : teachers) {
            JSONObject obj = new JSONObject();
            obj.put("id", t.getId());
            obj.put("name", t.getName());
            obj.put("email", t.getEmail());
            obj.put("password", t.getPassword());
            tArr.put(obj);
        }

        JSONArray sArr = new JSONArray();
        for (Student s : students) {
            JSONObject obj = new JSONObject();
            obj.put("id", s.getId());
            obj.put("name", s.getName());
            obj.put("email", s.getEmail());
            obj.put("password", s.getPassword());
            obj.put("course", s.getCourse());
            obj.put("yearLevel", s.getYearLevel());
            obj.put("type", s.getType());
            sArr.put(obj);
        }

        root.put("teachers", tArr);
        root.put("students", sArr);

        try (FileWriter fw = new FileWriter(FILE)) {
            fw.write(root.toString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadData() {
        try (FileReader fr = new FileReader(FILE)) {
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = fr.read()) != -1) sb.append((char) i);
            JSONObject root = new JSONObject(sb.toString());

            if (root.has("teachers")) {
                JSONArray tArr = root.getJSONArray("teachers");
                for (int j = 0; j < tArr.length(); j++) {
                    JSONObject o = tArr.getJSONObject(j);
                    teachers.add(new Teacher(o.getString("id"),
                            o.getString("name"),
                            o.getString("email"),
                            o.getString("password")));
                }
            }

            if (root.has("students")) {
                JSONArray sArr = root.getJSONArray("students");
                for (int j = 0; j < sArr.length(); j++) {
                    JSONObject o = sArr.getJSONObject(j);
                    students.add(new Student(o.getString("id"),
                            o.getString("name"),
                            o.getString("email"),
                            o.getString("password"),
                            o.getString("course"),
                            o.getInt("yearLevel"),
                            o.getString("type")));
                }
            }
        } catch (Exception e) {
            System.out.println("No saved data yet, starting fresh...");
        }
    }
}
