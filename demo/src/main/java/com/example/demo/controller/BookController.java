package com.example.demo.controller;

import com.example.demo.model.Book;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;




@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    // Constructor to add 3 sample books
    public BookController() {
        books.add(new Book("Clean Code", "Robert Martin", "978-0132350884", 1L, 2008));
        books.add(new Book("Effective Java", "Joshua Bloch", "978-0134685991", 2L, 2018));
        books.add(new Book("Spring in Action", "Craig Walls", "978-1617294945", 3L, 2018));
    }

    // GET /api/books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(books); // 200
    }

    // GET /api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {

        for (Book book : books) {
            if (book.getId().equals(id)) {
                return ResponseEntity.ok(book); // 200
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }

    // GET /api/books/search?title=...
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchByTitle(@RequestParam String title) {

        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }

        return ResponseEntity.ok(result); // 200
    }

    // POST /api/books
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book); // 201
    }

    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        for (Book book : books) {
            if (book.getId().equals(id)) {
                books.remove(book);
                return ResponseEntity.noContent().build(); // 204
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }
}
