package ru.hogwards.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repository.StudentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method \"createStudent\"");
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        logger.info("Was invoked method \"getStudentById\"");
        logger.warn("This method may return null");
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method \"updateStudent\"");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method \"deleteStudent\"");
        studentRepository.deleteById(id);
    }

    public List<Student> filterByAge(int age) {
        logger.info("Was invoked method \"filterByAge\"");
        return studentRepository.findAllByAge(age);
    }

    public List<Student> filterByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method \"filterByAgeBetween\"");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getStudentFacultyById(Long id) {
        logger.info("Was invoked method \"getStudentFacultyById\"");
        logger.warn("There may be no student with such ID");
        return studentRepository.findById(id).orElseThrow(NoSuchElementException::new).getFaculty();
    }

    public int getNumberOfStudents() {
        logger.info("Was invoked method \"getNumberOfStudents\"");
        return studentRepository.getNumberOfStudents();
    }

    public double getAverageAgeOfStudents() {
        logger.info("Was invoked method \"getAverageAgeOfStudents\"");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method \"getLastFiveStudents\"");
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getStudentsStartingWithLetter(String letter) {
        return studentRepository.findAll().stream()
                .parallel()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith(letter))
                .sorted()
                .toList();
    }

    public Double getAverageAgeOfStudents_Stream() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0.0);
    }
}
