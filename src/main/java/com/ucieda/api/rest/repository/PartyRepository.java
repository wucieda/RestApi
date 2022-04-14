package com.ucieda.api.rest.repository;

import com.ucieda.api.rest.entity.Party;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PartyRepository extends CrudRepository<Party, Integer> {

    Collection<Party> findAll();
}
