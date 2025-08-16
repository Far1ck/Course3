package ru.hogwards.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repository.FacultyRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method \"createFaculty\"");
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method \"getFacultyById\"");
        logger.warn("This method may return null");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method \"updateFaculty\"");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("Was invoked method \"deleteFaculty\"");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterByColor(String color) {
        logger.info("Was invoked method \"filterByColor\"");
        return facultyRepository.findAllByColor(color);
    }

    public List<Faculty> findFacultyByNameOrByColor(String query) {
        logger.info("Was invoked method \"findFacultyByNameOrByColor\"");
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }

    public List<Student> getAllStudentsOfTheFacultyById(Long id) {
        logger.info("Was invoked method \"getAllStudentsOfTheFacultyById\"");
        logger.warn("There may be no faculty with such ID");
        return facultyRepository.findById(id).orElseThrow(NoSuchElementException::new).getStudents();
    }
}
