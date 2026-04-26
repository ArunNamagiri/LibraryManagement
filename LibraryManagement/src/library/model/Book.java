package library.model;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookId;
    private String title;
    private String author;
    private String genre;
    private int totalCopies;
    private int availableCopies;

    public Book(String bookId, String title, String author, String genre, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    // Getters
    public String getBookId()         { return bookId; }
    public String getTitle()          { return title; }
    public String getAuthor()         { return author; }
    public String getGenre()          { return genre; }
    public int    getTotalCopies()    { return totalCopies; }
    public int    getAvailableCopies(){ return availableCopies; }

    // Setters
    public void setTitle(String title)   { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre)   { this.genre = genre; }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    public void decrementCopies() {
        if (availableCopies > 0) availableCopies--;
    }

    public void incrementCopies() {
        if (availableCopies < totalCopies) availableCopies++;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-30s %-20s %-15s Copies: %d/%d",
                bookId, title, author, genre, availableCopies, totalCopies);
    }
}
