package com.ucieda.api.rest.repository;

import com.ucieda.api.rest.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
    Collection<Person> findAll();
}
