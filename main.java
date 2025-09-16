import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final HashMap<String, Student> accounts = new HashMap<>();
    private static final String FILE_NAME = "students.json";

    private static Student loggedIn = null;

    public static void main(String[] args) {
        loadStudents();

        while (true) {
            if (loggedIn == null) {
                System.out.println("\n=== STUDENT INFORMATION SYSTEM ===");
                System.out.println("1. Register Student");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> login();
                    case 3 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } else {
                System.out.println("\n=== Welcome " + loggedIn.getName() + " ===");
                System.out.println("1. View Profile");
                System.out.println("2. Add Subject with Grade & Schedule");
                System.out.println("3. View Subjects & Grades");
                System.out.println("4. Logout");
                System.out.print("Choose: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> System.out.println(loggedIn.getDetails());
                    case 2 -> addSubjectToStudent();
                    case 3 -> viewSubjects();
                    case 4 -> {
                        System.out.println("Logging out...");
                        loggedIn = null;
                    }
                    default -> System.out.println("Invalid option.");
                }
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter course: ");
        String course = sc.nextLine();
        System.out.print("Enter year level (1-4): ");
        int yearLevel = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter student type (1-Regular / 2-Transferee / 3-Irregular): ");
        int type = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Student student;
        switch (type) {
            case 1 -> student = new RegularStudent(name, course, yearLevel, email, password);
            case 2 -> student = new TransfereeStudent(name, course, yearLevel, email, password);
            default -> student = new IrregularStudent(name, course, yearLevel, email, password);
        }

        students.add(student);
        accounts.put(email, student);
        saveStudents();
        System.out.println("Student registered!");
    }

    private static void login() {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        if (accounts.containsKey(email) && accounts.get(email).checkPassword(pass)) {
            loggedIn = accounts.get(email);
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private static void addSubjectToStudent() {
        System.out.print("Enter subject name: ");
        String subj = sc.nextLine();
        System.out.print("Enter grade (1.0-5.0): ");
        double grade = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter schedule: ");
        String schedule = sc.nextLine();

        loggedIn.addSubject(new Subject(subj, grade, schedule));
        saveStudents();
        System.out.println("Subject added!");
    }

    private static void viewSubjects() {
        ArrayList<Subject> subs = loggedIn.getSubjects();
        if (subs.isEmpty()) {
            System.out.println("No subjects found.");
            return;
        }
        System.out.println("\nSubjects of " + loggedIn.getName() + ":");
        for (Subject s : subs) {
            System.out.println(s);
        }
    }

    private static void saveStudents() {
        JSONArray jsonArray = new JSONArray();
        for (Student student : students) jsonArray.put(student.toJSON());
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadStudents() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                sb.append((char) i);
            }

            JSONArray arr = new JSONArray(sb.toString());
            for (int j = 0; j < arr.length(); j++) {
                JSONObject obj = arr.getJSONObject(j);

                String name = obj.getString("name");
                String course = obj.getString("course");
                int yearLevel = obj.getInt("yearLevel");
                String email = obj.getString("email");
                String password = obj.getString("password");
                String type = obj.getString("type");

                Student student;
                switch (type) {
                    case "Regular" -> student = new RegularStudent(name, course, yearLevel, email, password);
                    case "Transferee" -> student = new TransfereeStudent(name, course, yearLevel, email, password);
                    default -> student = new IrregularStudent(name, course, yearLevel, email, password);
                }

                // load subjects
                if (obj.has("subjects")) {
                    JSONArray subjArr = obj.getJSONArray("subjects");
                    for (int k = 0; k < subjArr.length(); k++) {
                        JSONObject sobj = subjArr.getJSONObject(k);
                        student.addSubject(new Subject(
                                sobj.getString("subjectName"),
                                sobj.getDouble("grade"),
                                sobj.getString("schedule")
                        ));
                    }
                }

                students.add(student);
                accounts.put(email, student);
            }
        } catch (IOException e) {
            System.out.println("No saved students found. A new file will be created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
