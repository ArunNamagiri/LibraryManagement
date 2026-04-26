package library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class IssuedRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String recordId;
    private String studentId;
    private String bookId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean returned;
    private static final int LOAN_DAYS = 14;
    private static final double FINE_PER_DAY = 2.0;

    public IssuedRecord(String recordId, String studentId, String bookId) {
        this.recordId   = recordId;
        this.studentId  = studentId;
        this.bookId     = bookId;
        this.issueDate  = LocalDate.now();
        this.dueDate    = issueDate.plusDays(LOAN_DAYS);
        this.returned   = false;
    }

    // Getters
    public String     getRecordId()   { return recordId; }
    public String     getStudentId()  { return studentId; }
    public String     getBookId()     { return bookId; }
    public LocalDate  getIssueDate()  { return issueDate; }
    public LocalDate  getDueDate()    { return dueDate; }
    public LocalDate  getReturnDate() { return returnDate; }
    public boolean    isReturned()    { return returned; }

    public void markReturned() {
        this.returned    = true;
        this.returnDate  = LocalDate.now();
    }

    public long getDaysOverdue() {
        LocalDate checkDate = returned ? returnDate : LocalDate.now();
        long overdue = ChronoUnit.DAYS.between(dueDate, checkDate);
        return Math.max(0, overdue);
    }

    public double getFineAmount() {
        return getDaysOverdue() * FINE_PER_DAY;
    }

    @Override
    public String toString() {
        String status = returned
                ? "RETURNED (" + returnDate + ")"
                : (getDaysOverdue() > 0 ? "OVERDUE by " + getDaysOverdue() + " days" : "ACTIVE");
        return String.format("%-12s  Student:%-10s  Book:%-10s  Issued:%s  Due:%s  Status:%s  Fine:₹%.1f",
                recordId, studentId, bookId, issueDate, dueDate, status, getFineAmount());
    }
}
