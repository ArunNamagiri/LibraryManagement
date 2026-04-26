package library;

import library.model.Book;
import library.model.IssuedRecord;
import library.model.Student;
import library.service.LibraryService;
import library.util.ConsoleHelper;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final LibraryService service = new LibraryService();
    private static final Scanner        sc      = new Scanner(System.in);

    public static void main(String[] args) {
        printWelcome();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1  -> bookMenu();
                case 2  -> studentMenu();
                case 3  -> transactionMenu();
                case 4  -> reportMenu();
                case 0  -> { running = false; ConsoleHelper.printInfo("Goodbye! 📚"); }
                default -> ConsoleHelper.printError("Invalid option.");
            }
        }
        sc.close();
    }

    // ─── MENUS ────────────────────────────────────────────────────────────────

    private static void printMainMenu() {
        ConsoleHelper.printHeader("📚  LIBRARY MANAGEMENT SYSTEM  📚");
        System.out.println("  1. Book Management");
        System.out.println("  2. Student Management");
        System.out.println("  3. Issue / Return Books");
        System.out.println("  4. Reports");
        System.out.println("  0. Exit");
        ConsoleHelper.printDivider();
    }

    private static void bookMenu() {
        boolean back = false;
        while (!back) {
            ConsoleHelper.printHeader("BOOK MANAGEMENT");
            System.out.println("  1. View All Books");
            System.out.println("  2. Search Books");
            System.out.println("  3. Add New Book");
            System.out.println("  4. Remove Book");
            System.out.println("  0. Back");
            ConsoleHelper.printDivider();
            int c = readInt("Enter choice: ");
            switch (c) {
                case 1 -> viewAllBooks();
                case 2 -> searchBooks();
                case 3 -> addBook();
                case 4 -> removeBook();
                case 0 -> back = true;
                default -> ConsoleHelper.printError("Invalid option.");
            }
        }
    }

    private static void studentMenu() {
        boolean back = false;
        while (!back) {
            ConsoleHelper.printHeader("STUDENT MANAGEMENT");
            System.out.println("  1. View All Students");
            System.out.println("  2. Search Students");
            System.out.println("  3. Add New Student");
            System.out.println("  4. Remove Student");
            System.out.println("  0. Back");
            ConsoleHelper.printDivider();
            int c = readInt("Enter choice: ");
            switch (c) {
                case 1 -> viewAllStudents();
                case 2 -> searchStudents();
                case 3 -> addStudent();
                case 4 -> removeStudent();
                case 0 -> back = true;
                default -> ConsoleHelper.printError("Invalid option.");
            }
        }
    }

    private static void transactionMenu() {
        boolean back = false;
        while (!back) {
            ConsoleHelper.printHeader("ISSUE / RETURN BOOKS");
            System.out.println("  1. Issue Book to Student");
            System.out.println("  2. Return Book");
            System.out.println("  3. View Active Issues");
            System.out.println("  0. Back");
            ConsoleHelper.printDivider();
            int c = readInt("Enter choice: ");
            switch (c) {
                case 1 -> issueBook();
                case 2 -> returnBook();
                case 3 -> viewActiveIssues();
                case 0 -> back = true;
                default -> ConsoleHelper.printError("Invalid option.");
            }
        }
    }

    private static void reportMenu() {
        boolean back = false;
        while (!back) {
            ConsoleHelper.printHeader("REPORTS");
            System.out.println("  1. All Issue Records");
            System.out.println("  2. Overdue Books");
            System.out.println("  3. Student's Issue History");
            System.out.println("  0. Back");
            ConsoleHelper.printDivider();
            int c = readInt("Enter choice: ");
            switch (c) {
                case 1 -> allRecords();
                case 2 -> overdueRecords();
                case 3 -> studentHistory();
                case 0 -> back = true;
                default -> ConsoleHelper.printError("Invalid option.");
            }
        }
    }

    // ─── BOOK ACTIONS ─────────────────────────────────────────────────────────

    private static void viewAllBooks() {
        ConsoleHelper.printHeader("ALL BOOKS");
        List<Book> list = service.getAllBooks();
        if (list.isEmpty()) { ConsoleHelper.printInfo("No books found."); return; }
        System.out.printf("%-10s %-30s %-20s %-15s %s%n",
                "ID", "Title", "Author", "Genre", "Copies (Avail/Total)");
        ConsoleHelper.printDivider();
        list.forEach(System.out::println);
        ConsoleHelper.printInfo("Total books: " + list.size());
    }

    private static void searchBooks() {
        ConsoleHelper.printHeader("SEARCH BOOKS");
        String kw = readString("Enter keyword (title/author/genre/id): ");
        List<Book> results = service.searchBooks(kw);
        if (results.isEmpty()) { ConsoleHelper.printError("No books matched."); return; }
        results.forEach(System.out::println);
        ConsoleHelper.printInfo("Found: " + results.size());
    }

    private static void addBook() {
        ConsoleHelper.printHeader("ADD NEW BOOK");
        String id     = readString("Book ID       : ");
        String title  = readString("Title         : ");
        String author = readString("Author        : ");
        String genre  = readString("Genre         : ");
        int    copies = readInt("Total Copies  : ");
        Book book = new Book(id, title, author, genre, copies);
        if (service.addBook(book))
            ConsoleHelper.printSuccess("Book added successfully!");
        else
            ConsoleHelper.printError("Book ID already exists.");
    }

    private static void removeBook() {
        ConsoleHelper.printHeader("REMOVE BOOK");
        String id = readString("Enter Book ID: ");
        if (service.removeBook(id))
            ConsoleHelper.printSuccess("Book removed.");
        else
            ConsoleHelper.printError("Book not found.");
    }

    // ─── STUDENT ACTIONS ──────────────────────────────────────────────────────

    private static void viewAllStudents() {
        ConsoleHelper.printHeader("ALL STUDENTS");
        List<Student> list = service.getAllStudents();
        if (list.isEmpty()) { ConsoleHelper.printInfo("No students found."); return; }
        System.out.printf("%-12s %-20s %-25s %-10s %s%n",
                "ID", "Name", "Email", "Branch", "Year / Books");
        ConsoleHelper.printDivider();
        list.forEach(System.out::println);
        ConsoleHelper.printInfo("Total students: " + list.size());
    }

    private static void searchStudents() {
        ConsoleHelper.printHeader("SEARCH STUDENTS");
        String kw = readString("Enter keyword (name/id/branch): ");
        List<Student> results = service.searchStudents(kw);
        if (results.isEmpty()) { ConsoleHelper.printError("No students matched."); return; }
        results.forEach(System.out::println);
        ConsoleHelper.printInfo("Found: " + results.size());
    }

    private static void addStudent() {
        ConsoleHelper.printHeader("ADD NEW STUDENT");
        String id     = readString("Student ID : ");
        String name   = readString("Name       : ");
        String email  = readString("Email      : ");
        String branch = readString("Branch     : ");
        int    year   = readInt("Year       : ");
        Student student = new Student(id, name, email, branch, year);
        if (service.addStudent(student))
            ConsoleHelper.printSuccess("Student registered successfully!");
        else
            ConsoleHelper.printError("Student ID already exists.");
    }

    private static void removeStudent() {
        ConsoleHelper.printHeader("REMOVE STUDENT");
        String id = readString("Enter Student ID: ");
        if (service.removeStudent(id))
            ConsoleHelper.printSuccess("Student removed.");
        else
            ConsoleHelper.printError("Student not found.");
    }

    // ─── TRANSACTION ACTIONS ──────────────────────────────────────────────────

    private static void issueBook() {
        ConsoleHelper.printHeader("ISSUE BOOK");
        String studentId = readString("Student ID : ");
        String bookId    = readString("Book ID    : ");
        String result    = service.issueBook(studentId, bookId);
        if (result.startsWith("SUCCESS")) {
            ConsoleHelper.printSuccess("Book issued! Record ID: " + result.split(":")[1]);
        } else {
            ConsoleHelper.printError(result);
        }
    }

    private static void returnBook() {
        ConsoleHelper.printHeader("RETURN BOOK");
        String recordId = readString("Enter Record ID (e.g. REC0001): ");
        String result   = service.returnBook(recordId);
        if (result.equals("SUCCESS")) {
            ConsoleHelper.printSuccess("Book returned. No fine.");
        } else if (result.startsWith("FINE")) {
            ConsoleHelper.printInfo("Book returned. Fine: " + result.split(":")[1]);
        } else {
            ConsoleHelper.printError(result);
        }
    }

    private static void viewActiveIssues() {
        ConsoleHelper.printHeader("ACTIVE ISSUES");
        List<IssuedRecord> list = service.getAllActiveRecords();
        if (list.isEmpty()) { ConsoleHelper.printInfo("No active issues."); return; }
        list.forEach(System.out::println);
        ConsoleHelper.printInfo("Active issues: " + list.size());
    }

    // ─── REPORT ACTIONS ───────────────────────────────────────────────────────

    private static void allRecords() {
        ConsoleHelper.printHeader("ALL ISSUE RECORDS");
        List<IssuedRecord> list = service.getAllRecords();
        if (list.isEmpty()) { ConsoleHelper.printInfo("No records yet."); return; }
        list.forEach(System.out::println);
        ConsoleHelper.printInfo("Total records: " + list.size());
    }

    private static void overdueRecords() {
        ConsoleHelper.printHeader("OVERDUE BOOKS");
        List<IssuedRecord> list = service.getOverdueRecords();
        if (list.isEmpty()) { ConsoleHelper.printSuccess("No overdue books!"); return; }
        list.forEach(System.out::println);
        ConsoleHelper.printError("Overdue count: " + list.size());
    }

    private static void studentHistory() {
        ConsoleHelper.printHeader("STUDENT ISSUE HISTORY");
        String studentId = readString("Enter Student ID: ");
        List<IssuedRecord> list = service.getRecordsByStudent(studentId);
        if (list.isEmpty()) { ConsoleHelper.printInfo("No records for this student."); return; }
        list.forEach(System.out::println);
        ConsoleHelper.printInfo("Total records: " + list.size());
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────

    private static String readString(String prompt) {
        System.out.print(ConsoleHelper.BOLD + prompt + ConsoleHelper.RESET);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(ConsoleHelper.BOLD + prompt + ConsoleHelper.RESET);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                ConsoleHelper.printError("Please enter a valid number.");
            }
        }
    }

    private static void printWelcome() {
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD);
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║      COLLEGE LIBRARY MANAGEMENT SYSTEM   ║");
        System.out.println("  ║            Built with Java OOP            ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
        System.out.println(ConsoleHelper.RESET);
    }
}
