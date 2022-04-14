package com.ucieda.api.rest.controller;

import com.ucieda.api.rest.entity.Book;
import com.ucieda.api.rest.entity.Library;
import com.ucieda.api.rest.repository.BookRepository;
import com.ucieda.api.rest.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<Page<Book>> listBook(Pageable pageable) {
        return ResponseEntity.ok(bookRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book) {
        Optional<Library> libraryOptional = libraryRepository.findById(book.getLibrary().getId());

        if(!libraryOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        book.setLibrary(libraryOptional.get());
        Book savedBook = bookRepository.save(book);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(uri).body(savedBook);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @Valid @RequestBody Book book) {

        Optional<Library> libraryOptional = libraryRepository.findById(book.getLibrary().getId());

        if(!libraryOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        book.setLibrary(libraryOptional.get());
        book.setId(optionalBook.get().getId());
        bookRepository.save(book);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        bookRepository.delete(optionalBook.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(optionalBook.get());

    }
}
