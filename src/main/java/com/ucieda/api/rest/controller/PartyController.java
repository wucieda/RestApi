package com.ucieda.api.rest.controller;

import com.ucieda.api.rest.entity.Party;
import com.ucieda.api.rest.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/party")
public class PartyController {
    @Autowired
    private PartyRepository partyRepository;

    @GetMapping
    public ResponseEntity<Collection<Party>> getParties() {
        return new ResponseEntity<>(partyRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Party> getPartyById(@PathVariable Integer id) {
        Party party = partyRepository.findById(id).orElseThrow();
        if (null != party) {
            return new ResponseEntity<>(partyRepository.findById(id).orElseThrow(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveParty(@RequestBody Party party) {
        return new ResponseEntity<>(partyRepository.save(party), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Integer id) {
        partyRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
