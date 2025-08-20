package ru.hogwards.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.service.StudentService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student result = studentService.getStudentById(id);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student result = studentService.createStudent(student);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student result = studentService.updateStudent(student);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<Student>> filterStudentsByAge(@PathVariable int age) {
        return ResponseEntity.ok(studentService.filterByAge(age));
    }

    @GetMapping("/age")
    public ResponseEntity<List<Student>> filterStudentsByAgeBetween(@RequestParam int minAge,
                                                                    @RequestParam int maxAge) {
        return ResponseEntity.ok(studentService.filterByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getStudentFacultyById(@PathVariable Long id) {
        Faculty result = studentService.getStudentFacultyById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count")
    public int getNumberOfStudents() {
        return studentService.getNumberOfStudents();
    }

    @GetMapping("/age/average")
    public double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/lastfive")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/A")
    public ResponseEntity<List<String>> getStudentsStartingWithA() {
        return ResponseEntity.ok(studentService.getStudentsStartingWithA());
    }

    @GetMapping("/averageage")
    public ResponseEntity<Double> getAverageAgeOfStudents_Stream() {
        return ResponseEntity.ok(studentService.getAverageAgeOfStudents_Stream());
    }
}
