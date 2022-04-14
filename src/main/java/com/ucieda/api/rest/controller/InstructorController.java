package com.ucieda.api.rest.controller;

import com.ucieda.api.rest.entity.Instructor;
import com.ucieda.api.rest.exception.ResourceNotFoundException;
import com.ucieda.api.rest.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping
    public List<Instructor> getInstructors() {
        return instructorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructorDetails(@PathVariable Integer id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Instructor with id" + id + "not found"));

        return ResponseEntity.ok().body(instructor);

    }

    @PostMapping
    public Instructor saveInstructor(@Valid @RequestBody Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Integer id,
                                                       @Valid @RequestBody Instructor instructorRequest) {
        return instructorRepository.findById(id).map(instructor -> {
            instructor.setName(instructorRequest.getName());
            instructor.setLastName(instructorRequest.getLastName());
            instructor.setEmail(instructorRequest.getEmail());
            return ResponseEntity.ok(instructorRepository.save(instructor));
        }).orElseThrow(() -> new ResourceNotFoundException("Instructor with id" + id + "not found"));
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteInstructor(@PathVariable Integer id) {
        return instructorRepository.findById(id).map(instructor -> {
            instructorRepository.delete(instructor);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Instructor deleted", Boolean.TRUE);
            return response;
        }).orElseThrow(() -> new ResourceNotFoundException("Instructor with id" + id + "not found"));
    }
}
