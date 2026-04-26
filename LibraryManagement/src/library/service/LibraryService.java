package library.service;

import library.model.Book;
import library.model.IssuedRecord;
import library.model.Student;
import library.util.DataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryService {

    private List<Book>         books;
    private List<Student>      students;
    private List<IssuedRecord> records;
    private int                recordCounter;

    public LibraryService() {
        books         = DataStore.loadBooks();
        students      = DataStore.loadStudents();
        records       = DataStore.loadRecords();
        recordCounter = records.size() + 1;

        if (books.isEmpty())    seedBooks();
        if (students.isEmpty()) seedStudents();
    }

    // ─── BOOK OPERATIONS ──────────────────────────────────────────────────────

    public boolean addBook(Book book) {
        if (findBookById(book.getBookId()).isPresent()) return false;
        books.add(book);
        DataStore.saveBooks(books);
        return true;
    }

    public boolean removeBook(String bookId) {
        Optional<Book> opt = findBookById(bookId);
        if (opt.isEmpty()) return false;
        books.remove(opt.get());
        DataStore.saveBooks(books);
        return true;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<Book> searchBooks(String keyword) {
        String kw = keyword.toLowerCase();
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(kw)
                          || b.getAuthor().toLowerCase().contains(kw)
                          || b.getGenre().toLowerCase().contains(kw)
                          || b.getBookId().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    public Optional<Book> findBookById(String id) {
        return books.stream().filter(b -> b.getBookId().equalsIgnoreCase(id)).findFirst();
    }

    // ─── STUDENT OPERATIONS ───────────────────────────────────────────────────

    public boolean addStudent(Student student) {
        if (findStudentById(student.getStudentId()).isPresent()) return false;
        students.add(student);
        DataStore.saveStudents(students);
        return true;
    }

    public boolean removeStudent(String studentId) {
        Optional<Student> opt = findStudentById(studentId);
        if (opt.isEmpty()) return false;
        students.remove(opt.get());
        DataStore.saveStudents(students);
        return true;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public List<Student> searchStudents(String keyword) {
        String kw = keyword.toLowerCase();
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(kw)
                          || s.getStudentId().toLowerCase().contains(kw)
                          || s.getBranch().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    public Optional<Student> findStudentById(String id) {
        return students.stream().filter(s -> s.getStudentId().equalsIgnoreCase(id)).findFirst();
    }

    // ─── ISSUE / RETURN ───────────────────────────────────────────────────────

    public String issueBook(String studentId, String bookId) {
        Optional<Student> studentOpt = findStudentById(studentId);
        Optional<Book>    bookOpt    = findBookById(bookId);

        if (studentOpt.isEmpty()) return "Student not found.";
        if (bookOpt.isEmpty())    return "Book not found.";

        Student student = studentOpt.get();
        Book    book    = bookOpt.get();

        if (!student.canIssueBook())  return "Student has reached the maximum book limit (3).";
        if (!book.isAvailable())      return "No copies available for this book.";

        // Check duplicate issue
        boolean alreadyIssued = records.stream()
                .anyMatch(r -> r.getStudentId().equalsIgnoreCase(studentId)
                            && r.getBookId().equalsIgnoreCase(bookId)
                            && !r.isReturned());
        if (alreadyIssued) return "Student has already issued this book.";

        String recordId = "REC" + String.format("%04d", recordCounter++);
        IssuedRecord record = new IssuedRecord(recordId, studentId, bookId);

        records.add(record);
        student.incrementBooksIssued();
        book.decrementCopies();

        DataStore.saveRecords(records);
        DataStore.saveStudents(students);
        DataStore.saveBooks(books);

        return "SUCCESS:" + recordId;
    }

    public String returnBook(String recordId) {
        Optional<IssuedRecord> recordOpt = records.stream()
                .filter(r -> r.getRecordId().equalsIgnoreCase(recordId) && !r.isReturned())
                .findFirst();

        if (recordOpt.isEmpty()) return "Active record not found.";

        IssuedRecord record  = recordOpt.get();
        Optional<Student> studentOpt = findStudentById(record.getStudentId());
        Optional<Book>    bookOpt    = findBookById(record.getBookId());

        record.markReturned();
        studentOpt.ifPresent(Student::decrementBooksIssued);
        bookOpt.ifPresent(Book::incrementCopies);

        DataStore.saveRecords(records);
        DataStore.saveStudents(students);
        DataStore.saveBooks(books);

        double fine = record.getFineAmount();
        return fine > 0 ? "FINE:₹" + fine : "SUCCESS";
    }

    // ─── REPORTS ──────────────────────────────────────────────────────────────

    public List<IssuedRecord> getAllActiveRecords() {
        return records.stream().filter(r -> !r.isReturned()).collect(Collectors.toList());
    }

    public List<IssuedRecord> getAllRecords() {
        return new ArrayList<>(records);
    }

    public List<IssuedRecord> getRecordsByStudent(String studentId) {
        return records.stream()
                .filter(r -> r.getStudentId().equalsIgnoreCase(studentId))
                .collect(Collectors.toList());
    }

    public List<IssuedRecord> getOverdueRecords() {
        return records.stream()
                .filter(r -> !r.isReturned() && r.getDaysOverdue() > 0)
                .collect(Collectors.toList());
    }

    // ─── SEED DATA ────────────────────────────────────────────────────────────

    private void seedBooks() {
        books.add(new Book("B001", "Clean Code", "Robert C. Martin", "Programming", 3));
        books.add(new Book("B002", "The Pragmatic Programmer", "David Thomas", "Programming", 2));
        books.add(new Book("B003", "Introduction to Algorithms", "Cormen et al.", "CS Theory", 4));
        books.add(new Book("B004", "Design Patterns", "Gang of Four", "Software Eng", 2));
        books.add(new Book("B005", "Discrete Mathematics", "Kenneth Rosen", "Mathematics", 5));
        books.add(new Book("B006", "Operating System Concepts", "Silberschatz", "OS", 3));
        books.add(new Book("B007", "Computer Networks", "Andrew Tanenbaum", "Networking", 3));
        books.add(new Book("B008", "Database System Concepts", "Silberschatz", "Database", 4));
        DataStore.saveBooks(books);
    }

    private void seedStudents() {
        students.add(new Student("S001", "Arjun Sharma", "arjun@college.edu", "CSE", 2));
        students.add(new Student("S002", "Priya Reddy", "priya@college.edu", "ECE", 3));
        students.add(new Student("S003", "Rahul Kumar", "rahul@college.edu", "MECH", 1));
        students.add(new Student("S004", "Ananya Singh", "ananya@college.edu", "CSE", 4));
        DataStore.saveStudents(students);
    }
}
