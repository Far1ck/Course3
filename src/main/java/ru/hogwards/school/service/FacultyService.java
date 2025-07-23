package ru.hogwards.school.service;

import org.springframework.stereotype.Service;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repository.FacultyRepository;

import java.util.List;

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
        return facultyRepository.findById(id).get();
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

    public Faculty findFacultyByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Faculty findFacultyByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public List<Student> getAllStudentsOfTheFacultyById(Long id) {
        return facultyRepository.findById(id).get().getStudents();
    }
}
