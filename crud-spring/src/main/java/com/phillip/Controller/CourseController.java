package com.phillip.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.phillip.model.Course;
import com.phillip.repository.CouseRepository;

import lombok.AllArgsConstructor;

@RestController

// endpoint que vai ficar exposto, é no @RequestMapping que conseguimos obter informações do que queremos passar.

@RequestMapping ("api/courses")

// aqui é criado automaticamente o construtor usando o lombok.
@AllArgsConstructor

public class CourseController {
    private final CouseRepository courseRepository;    

    // aqui é onde pega o doGet por tras dos panos para pegar os dados da minha api.

    @GetMapping
    public List<Course> list() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id) {
        return courseRepository.findById(id)
            .map(recordFound -> ResponseEntity.ok().body(recordFound))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course create(@RequestBody Course record) {
        return courseRepository.save(record);      
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, 
        @RequestBody Course course) {
        return courseRepository.findById(id) 
        .map(recordFound -> {
            recordFound.setName(course.getName());
            recordFound.setCategory(course.getCategory());
            Course updated = courseRepository.save(recordFound);
            return ResponseEntity.ok().body(updated);
        })
        .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            return courseRepository.findById(id)
            .map(recordFound -> {
                courseRepository.deleteById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
        }
    }
    

