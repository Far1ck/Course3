package ru.hogwards.school.service;

import org.springframework.stereotype.Service;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repository.StudentRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> filterByAge(int age) {
        return studentRepository.findAllByAge(age);
    }

    public List<Student> filterByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getStudentFacultyById(Long id) {
        return studentRepository.findById(id).orElseThrow(NoSuchElementException::new).getFaculty();
    }

    public Faculty getStudentFacultyByName(String name) {
        Student result = studentRepository.findByNameContainsIgnoreCase(name);
        if (result == null) {
            return null;
        }
        return result.getFaculty();
    }
}
