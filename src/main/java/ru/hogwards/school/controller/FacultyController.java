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
        Faculty result = facultyService.getFacultyById(id);
        if (result == null) {
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
        return ResponseEntity.ok(facultyService.filterByColor(color));
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> findFacultyByNameOrColor(@RequestParam String query) {
        return ResponseEntity.ok(facultyService.findFacultyByNameOrByColor(query));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getAllStudentsOfTheFacultyById(@PathVariable Long id) {
        List<Student> result = facultyService.getAllStudentsOfTheFacultyById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/name/longest")
    public ResponseEntity<String> getTheLongestNameOfAFaculty() {
        String result = facultyService.getTheLongestNameOfAFaculty();
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }
}
