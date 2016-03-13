/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.ac.seu;

import bd.ac.seu.model.Course;
import bd.ac.seu.model.ExamSlot;
import bd.ac.seu.model.Faculty;
import bd.ac.seu.model.Room;
import bd.ac.seu.model.Student;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author kmhasan
 */
public class InputReaderSingleton {

    private static final InputReaderSingleton instance = new InputReaderSingleton();
    private static ArrayList<Student> students;
    private static ArrayList<Course> courses;

    private InputReaderSingleton() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        readStudents();
        readCourses();
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<ExamSlot> getExamSlots() {
        return null;
    }

    public ArrayList<Faculty> getFaculties() {
        return null;
    }

    public ArrayList<Room> getRooms() {
        return null;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    private void readCourses() {
        try {
            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_courses.php");
            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
            // RandomAccessFile input = new RandomAccessFile("students.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("courses");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                System.err.println(jsonObject);
                System.err.println(jsonObject.getString("Course Code"));
                System.err.println(jsonObject.getString("Title"));
                System.err.println(jsonObject.getInt("Section"));
                Course course = new Course(jsonObject.getString("Course Code"), jsonObject.getString("Title"), jsonObject.getInt("Section"));
                courses.add(course);
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readStudents() {
        try {
            URL jsonURL = new URL("http://my.seu.ac.bd/~kmhasan/_WebServices_/list_students.php");
            BufferedReader input = new BufferedReader(new InputStreamReader(jsonURL.openStream()));
            // uncomment the following line if reading from a local file
            // RandomAccessFile input = new RandomAccessFile("students.json", "r");
            String json = "";

            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }

                json += line;
            }
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("students");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Student student = new Student(jsonObject.getString("ID"), jsonObject.getString("Name"));
                students.add(student);
            }
        } catch (JSONException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputReaderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
