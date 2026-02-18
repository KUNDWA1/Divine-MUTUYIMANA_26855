package com.example.demo.model;

public class Book{
    private String title;
    private String author;
    private String isbn;
    private Long id;
    private int PublicationYear;

    // Default Constructor (required for JSON deserialization)
    public Book() {}

    // Constructor
    public Book(String title, String author, String isbn, Long id, int publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.id = id;
        this.PublicationYear = publicationYear;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public int getPublicationYear() {
        return PublicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.PublicationYear = publicationYear;
    }
}
