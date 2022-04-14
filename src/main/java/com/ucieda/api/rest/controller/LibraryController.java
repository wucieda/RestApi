package com.ucieda.api.rest.controller;

import com.ucieda.api.rest.entity.Library;
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
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping
    public ResponseEntity<Page<Library>> listLibrary(Pageable pageable) {
        return ResponseEntity.ok(libraryRepository.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Library> saveLibrary(@Valid @RequestBody Library library) {
        Library savedLibrary = libraryRepository.save(library);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(savedLibrary.getId()).toUri();
        return ResponseEntity.created(uri).body(savedLibrary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> updateLibrary(@PathVariable Integer id, @Valid @RequestBody Library library) {
        Optional<Library> optionalLibrary = libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        library.setId(optionalLibrary.get().getId());
        libraryRepository.save(library);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Library> deleteLibrary(@PathVariable Integer id) {
        Optional<Library> optionalLibrary = libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        libraryRepository.delete(optionalLibrary.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getLibrary(@PathVariable Integer id) {
        Optional<Library> optionalLibrary = libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(optionalLibrary.get());

    }
}
