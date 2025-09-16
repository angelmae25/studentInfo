package services;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import models.*;

public class DataManager {
    private static final String FILE = "data/system.json";

    public static void save(ArrayList<Teacher> teachers, ArrayList<Student> students) {
        JSONObject root = new JSONObject();

        JSONArray tArr = new JSONArray();
        for (Teacher t : teachers) {
            JSONObject tobj = new JSONObject();
            tobj.put("id", t.getId());
            tobj.put("name", t.getName());
            tobj.put("email", t.getEmail());
            tobj.put("password", t.getPassword());
            tArr.put(tobj);
        }

        JSONArray sArr = new JSONArray();
        for (Student s : students) {
            JSONObject sobj = new JSONObject();
            sobj.put("id", s.getId());
            sobj.put("name", s.getName());
            sobj.put("email", s.getEmail());
            sobj.put("password", s.getPassword());
            sobj.put("course", s.getCourse());
            sobj.put("yearLevel", s.getYearLevel());
            sobj.put("type", s.getType());

            JSONArray subjArr = new JSONArray();
            for (Subject sub : s.getSubjects()) {
                JSONObject subObj = new JSONObject();
                subObj.put("subjectName", sub.getSubjectName());
                subObj.put("grade", sub.getGrade());
                subObj.put("schedule", sub.getSchedule());
                subjArr.put(subObj);
            }
            sobj.put("subjects", subjArr);
            sArr.put(sobj);
        }

        root.put("teachers", tArr);
        root.put("students", sArr);

        try (FileWriter fw = new FileWriter(FILE)) {
            fw.write(root.toString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject load() {
        try (FileReader fr = new FileReader(FILE)) {
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = fr.read()) != -1) sb.append((char) i);
            return new JSONObject(sb.toString());
        } catch (Exception e) {
            return new JSONObject(); // empty
        }
    }
}
