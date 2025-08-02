package ru.hogwards.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwards.school.controller.FacultyController;
import ru.hogwards.school.controller.StudentController;
import ru.hogwards.school.model.Faculty;
import ru.hogwards.school.model.Student;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private StudentController studentController;

	@Autowired
	private FacultyController facultyController;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() throws Exception {
		Assertions.assertThat(studentController).isNotNull();
		Assertions.assertThat(facultyController).isNotNull();
	}

	@Test
	public void getStudentTest() throws Exception {
		Student student = new Student();
		student.setName("getStudentTest");
		student.setAge(33);
		student = this.restTemplate.postForObject("http://localhost:" + port + "/student",
				student, Student.class);
		Long id = student.getId();

		ResponseEntity<Student> response = this.restTemplate.getForEntity("http://localhost:" + port + "/student/" + id, Student.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Student receivedStudent = response.getBody();
		Assertions.assertThat(receivedStudent).isNotNull();
		Assertions.assertThat(receivedStudent.getId()).isEqualTo(id);
		Assertions.assertThat(receivedStudent.getName()).isEqualTo(student.getName());
		Assertions.assertThat(receivedStudent.getAge()).isEqualTo(student.getAge());
	}

	@Test
	public void createStudentTest() throws Exception {
		Student student = new Student();
		student.setName("createStudentTest");
		student.setAge(33);

		ResponseEntity<Student> response = this.restTemplate.postForEntity("http://localhost:" + port + "/student",
				student, Student.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Student createdStudent = response.getBody();
		Assertions.assertThat(createdStudent).isNotNull();
		Assertions.assertThat(createdStudent.getName()).isEqualTo(student.getName());
		Assertions.assertThat(createdStudent.getAge()).isEqualTo(student.getAge());
	}

	@Test
	public void updateStudentTest() throws Exception {
		Student student = new Student();
		student.setName("123");
		student.setAge(33);
		student = this.restTemplate.postForObject("http://localhost:" + port + "/student",
				student, Student.class);
		student.setName("getStudentTest");

		ResponseEntity<Student> response = this.restTemplate.exchange(
				"http://localhost:" + port + "/student",
				HttpMethod.PUT,
				new HttpEntity<>(student),
				Student.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Student updatedStudent = response.getBody();
		Assertions.assertThat(updatedStudent).isNotNull();
		Assertions.assertThat(updatedStudent.getName()).isEqualTo(student.getName());
		Assertions.assertThat(updatedStudent.getId()).isEqualTo(student.getId());
	}

	@Test
	public void deleteStudentTest() throws Exception {
		Student student = new Student();
		student.setName("deleteStudentTest");
		student.setAge(33);
		student = this.restTemplate.postForObject("http://localhost:" + port + "/student",
				student, Student.class);
		Long id = student.getId();

		this.restTemplate.delete("http://localhost:" + port + "/student/" + id);
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + id, Student.class)).isNull();
	}

	@Test
	public void filterStudentByAgeTest() throws Exception {
		int age = 1;

		ResponseEntity<List<Student>> response = restTemplate.exchange(
				"http://localhost:" + port + "/student/age/" + age,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Student>>(){});

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).isNotNull();
	}

	@Test
	public void filterStudentsByAgeBetweenTest() throws Exception {
		int minAge = 1;
		int maxAge = 2;

		ResponseEntity<List<Student>> response = restTemplate.exchange(
				"http://localhost:" + port + "/student/age?minAge=" + minAge + "&maxAge=" + maxAge,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Student>>(){});

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).isNotNull();
	}

	@Test
	public void getStudentFacultyByIdTest() throws Exception {
		Student student = new Student();
		student.setName("getStudentFacultyByIdTest");
		student.setAge(33);
		student = this.restTemplate.postForObject("http://localhost:" + port + "/student",
				student, Student.class);
		Long id = student.getId();

		ResponseEntity<Faculty> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/student/" + id + "/faculty",
				Faculty.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).isNull();
	}

	@Test
	public void getFacultyTest() throws Exception {
		Faculty faculty = new Faculty();
		faculty.setName("getFacultyTest");
		faculty.setColor("purple");
		faculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty",
				faculty, Faculty.class);
		Long id = faculty.getId();

		ResponseEntity<Faculty> response = this.restTemplate.getForEntity("http://localhost:" + port + "/faculty/" + id, Faculty.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Faculty receivedFaculty = response.getBody();
		Assertions.assertThat(receivedFaculty).isNotNull();
		Assertions.assertThat(receivedFaculty.getId()).isEqualTo(id);
		Assertions.assertThat(receivedFaculty.getName()).isEqualTo(faculty.getName());
		Assertions.assertThat(receivedFaculty.getColor()).isEqualTo(faculty.getColor());
	}

	@Test
	public void createFacultyTest() throws Exception {
		Faculty faculty = new Faculty();
		faculty.setName("createFacultyTest");
		faculty.setColor("purple");

		ResponseEntity<Faculty> response = this.restTemplate.postForEntity("http://localhost:" + port + "/faculty",
				faculty, Faculty.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Faculty createdFaculty = response.getBody();
		Assertions.assertThat(createdFaculty).isNotNull();
		Assertions.assertThat(createdFaculty.getName()).isEqualTo(faculty.getName());
		Assertions.assertThat(createdFaculty.getColor()).isEqualTo(faculty.getColor());
	}

	@Test
	public void updateFacultyTest() throws Exception {
		Faculty faculty = new Faculty();
		faculty.setName("123");
		faculty.setColor("purple");
		faculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty",
				faculty, Faculty.class);
		faculty.setName("updateFacultyTest");

		ResponseEntity<Faculty> response = this.restTemplate.exchange(
				"http://localhost:" + port + "/faculty",
				HttpMethod.PUT,
				new HttpEntity<>(faculty),
				Faculty.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Faculty updatedFaculty = response.getBody();
		Assertions.assertThat(updatedFaculty).isNotNull();
		Assertions.assertThat(updatedFaculty.getName()).isEqualTo(faculty.getName());
		Assertions.assertThat(updatedFaculty.getId()).isEqualTo(faculty.getId());
	}

	@Test
	public void deleteFacultyTest() throws Exception{
		Faculty faculty = new Faculty();
		faculty.setName("deleteFacultyTest");
		faculty.setColor("purple");
		faculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty",
				faculty, Faculty.class);
		Long id = faculty.getId();

		this.restTemplate.delete("http://localhost:" + port + "/faculty/" + id);
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + id, Faculty.class)).isNull();
	}

	@Test
	public void filterFacultiesByColorTest() throws Exception {
		String color = "purple";

		ResponseEntity<List<Faculty>> response = restTemplate.exchange(
				"http://localhost:" + port + "/faculty/color?color=" + color,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Faculty>>(){});

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).isNotNull();
	}

	@Test
	public void findFacultyByNameOrColorTest() throws Exception {
		String query = "purple";
		Faculty faculty = new Faculty();
		faculty.setName("findFacultyByNameOrColorTest");
		faculty.setColor("purple");
		faculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty",
				faculty, Faculty.class);

		ResponseEntity<List<Faculty>> response = restTemplate.exchange(
				"http://localhost:" + port + "/faculty?query=" + query,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Faculty>>(){});

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).isNotNull();
	}

	@Test
	public void getAllStudentsOfTheFacultyByIdTest() throws Exception {
		Faculty faculty = new Faculty();
		faculty.setName("getAllStudentsOfTheFacultyByIdTest");
		faculty.setColor("black");
		faculty = this.restTemplate.postForObject("http://localhost:" + port + "/faculty",
				faculty, Faculty.class);
		Long id = faculty.getId();

		ResponseEntity<List<Student>> response = restTemplate.exchange(
				"http://localhost:" + port + "/faculty/" + id + "/students",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Student>>(){});

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).isNotNull();
	}
}
