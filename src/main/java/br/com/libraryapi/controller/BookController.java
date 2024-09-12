package br.com.libraryapi.controller;


import br.com.libraryapi.dto.AddBookDTO;
import br.com.libraryapi.dto.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<BookDTO> books = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id){
        BookDTO book = books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
        if (book == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody AddBookDTO newBookDTO){
        int newId = books.size() + 1;
        BookDTO book= new BookDTO();
        book.setId(newId);
        book.setName(newBookDTO.getName());
        books.add(book);
        return ResponseEntity.status(201).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO){
        BookDTO existingBook = books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
        if (existingBook == null){
            return ResponseEntity.notFound().build();
        }
        existingBook.setName(bookDTO.getName());
        return ResponseEntity.ok(existingBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable int id){
        boolean removed = books.removeIf(book -> book.getId() == id);
        if (!removed){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

