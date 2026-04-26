package library.model;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentId;
    private String name;
    private String email;
    private String branch;
    private int year;
    private int booksIssued;
    private static final int MAX_BOOKS = 3;

    public Student(String studentId, String name, String email, String branch, int year) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.booksIssued = 0;
    }

    // Getters
    public String getStudentId()  { return studentId; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getBranch()     { return branch; }
    public int    getYear()       { return year; }
    public int    getBooksIssued(){ return booksIssued; }

    // Setters
    public void setName(String name)     { this.name = name; }
    public void setEmail(String email)   { this.email = email; }
    public void setBranch(String branch) { this.branch = branch; }
    public void setYear(int year)        { this.year = year; }

    public boolean canIssueBook() {
        return booksIssued < MAX_BOOKS;
    }

    public void incrementBooksIssued() {
        if (booksIssued < MAX_BOOKS) booksIssued++;
    }

    public void decrementBooksIssued() {
        if (booksIssued > 0) booksIssued--;
    }

    @Override
    public String toString() {
        return String.format("%-12s %-20s %-25s %-10s Year:%d  Books Issued:%d/%d",
                studentId, name, email, branch, year, booksIssued, MAX_BOOKS);
    }
}
