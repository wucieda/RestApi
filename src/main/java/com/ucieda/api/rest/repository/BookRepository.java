package com.ucieda.api.rest.repository;

import com.ucieda.api.rest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
