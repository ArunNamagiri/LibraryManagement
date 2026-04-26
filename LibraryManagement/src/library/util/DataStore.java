package library.util;

import library.model.Book;
import library.model.IssuedRecord;
import library.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static final String DATA_DIR    = "data/";
    private static final String BOOKS_FILE  = DATA_DIR + "books.dat";
    private static final String STUDENTS_FILE = DATA_DIR + "students.dat";
    private static final String RECORDS_FILE  = DATA_DIR + "records.dat";

    static {
        new File(DATA_DIR).mkdirs();
    }

    // ─── Generic Save / Load ──────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private static <T> List<T> load(String path) {
        File file = new File(path);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            System.err.println("[DataStore] Failed to load " + path + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static <T> void save(String path, List<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.err.println("[DataStore] Failed to save " + path + ": " + e.getMessage());
        }
    }

    // ─── Books ────────────────────────────────────────────────────────────────

    public static List<Book> loadBooks()          { return load(BOOKS_FILE); }
    public static void       saveBooks(List<Book> b) { save(BOOKS_FILE, b); }

    // ─── Students ─────────────────────────────────────────────────────────────

    public static List<Student> loadStudents()             { return load(STUDENTS_FILE); }
    public static void          saveStudents(List<Student> s) { save(STUDENTS_FILE, s); }

    // ─── Records ──────────────────────────────────────────────────────────────

    public static List<IssuedRecord> loadRecords()                { return load(RECORDS_FILE); }
    public static void               saveRecords(List<IssuedRecord> r) { save(RECORDS_FILE, r); }
}
