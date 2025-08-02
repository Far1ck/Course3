package ru.hogwards.school.service;

import org.springframework.stereotype.Service;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repository.FacultyRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FacultyService {
    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }

    public List<Faculty> findFacultyByNameOrByColor(String query) {
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }

    public List<Student> getAllStudentsOfTheFacultyById(Long id) {
        return facultyRepository.findById(id).orElseThrow(NoSuchElementException::new).getStudents();
    }
}
