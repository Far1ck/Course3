package ru.hogwards.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwards.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByColor(String color);
}
