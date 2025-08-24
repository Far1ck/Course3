package ru.hogwards.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;
import ru.hogwards.school.repository.StudentRepository;
import ru.hogwards.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerWithMockTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void getStudentTest() throws Exception{
        Long id = 1L;
        Student student = new Student();
        student.setId(id);
        student.setName("Harry Potter");
        student.setAge(12);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void createStudentTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");
        student.setAge(12);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student.getName());
        studentObject.put("age", student.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void updateStudentTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");
        student.setAge(12);

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", student.getId());
        studentObject.put("name", student.getName());
        studentObject.put("age", student.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void filterStudentsByAgeTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");
        student.setAge(12);
        List<Student> students = List.of(student);
        doAnswer(inv -> students).when(studentRepository).findAllByAge(anyInt());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/" + student.getAge())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void filterStudentsByAgeBetweenTest() throws Exception {
        int minAge = 11;
        int maxAge = 13;
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");
        student.setAge(12);
        List<Student> students = List.of(student);
        doAnswer(inv -> students).when(studentRepository).findByAgeBetween(minAge, maxAge);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?minAge=" + minAge + "&maxAge=" + maxAge)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentFacultyByIdTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Gryffindor");
        faculty.setColor("red");
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");
        student.setAge(12);
        student.setFaculty(faculty);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student.getId() + "/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void getStudentsNameParallelTest() throws Exception {
        Student student1 = new Student(1L, "1", 1);
        Student student2 = new Student(2L, "2", 2);
        Student student3 = new Student(3L, "3", 3);
        Student student4 = new Student(4L, "4", 4);
        List<Student> students = List.of(student1,student2, student3, student4);
        doAnswer(inv -> students).when(studentRepository).findAll();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/print-parallel"))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsNameSynchronizedTest() throws Exception {
        Student student1 = new Student(1L, "1", 1);
        Student student2 = new Student(2L, "2", 2);
        Student student3 = new Student(3L, "3", 3);
        Student student4 = new Student(4L, "4", 4);
        List<Student> students = List.of(student1,student2, student3, student4);
        doAnswer(inv -> students).when(studentRepository).findAll();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/print-synchronized"))
                .andExpect(status().isOk());
    }
}
