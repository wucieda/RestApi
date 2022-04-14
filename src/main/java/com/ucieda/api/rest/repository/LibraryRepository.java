package com.ucieda.api.rest.repository;

import com.ucieda.api.rest.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Integer> {
}
