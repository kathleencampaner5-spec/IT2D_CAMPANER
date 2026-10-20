package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String status;

    public Book(int id, String title, String author, String isbn, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.status = status;
    }

    public Book(String title, String author, String isbn, String status) {
        this(0, title, author, isbn, status);
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getStatus() { return status; }
}
