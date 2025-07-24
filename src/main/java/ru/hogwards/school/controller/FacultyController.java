package ru.hogwards.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.service.FacultyService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty result;
        try {
            result = facultyService.getFacultyById(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty result = facultyService.createFaculty(faculty);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty result = facultyService.updateFaculty(faculty);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color")
    public ResponseEntity<List<Faculty>> filterFacultiesByColor(@RequestParam String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.filterByColor(color));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<Faculty> findFacultyByNameOrColor(@RequestParam String query) {
        if (query != null && !query.isBlank()) {
            return ResponseEntity.ok(facultyService.findFacultyByNameOrByColor(query, query));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getAllStudentsOfTheFacultyById(@PathVariable Long id) {
        List<Student> result;
        try {
            result = facultyService.getAllStudentsOfTheFacultyById(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
