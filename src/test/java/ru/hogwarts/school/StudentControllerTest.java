package ru.hogwarts.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.agent.VirtualMachine;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    List<Student> savedStudents;

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup(){

        Faculty faculty = new Faculty();
        faculty.setId(777L);

        Student student1 = new Student(1L, "jhgjhgj", 18);
        student1.setFaculty(faculty);

        Student student2 = new Student(2L, "ddffdfaa", 19);
        student2.setFaculty(faculty);

        List<Student> studentList = List.of(student1, student2);
        savedStudents = studentRepository.saveAll(studentList);

    }

    @Test
    void testGetStudent() throws JSONException, JsonProcessingException{
        String expected = mapper.writeValueAsString(savedStudents.get(0));

        ResponseEntity <String> response = restTemplate.getForEntity("http://localhost:" + port + "/student/" + savedStudents.get(0).getId(),
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    void testGetStudents() throws JSONException, JsonProcessingException{
        ResponseEntity<List<Student>> response = restTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        List<Student> students = Objects.requireNonNull(response.getBody()).stream().toList();
        assertTrue(students.containsAll(savedStudents));



    }

    @Test
    void addStudent() throws JSONException, JsonProcessingException{
        Student student1 = new Student(3L, "dddddd", 18);
        student1.setFaculty(savedStudents.get(0).getFaculty());

        ResponseEntity<Student> response = restTemplate.postForEntity("/student", student1, Student.class);

        String expected = mapper.writeValueAsString(student1);
        //assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student1, response.getBody());

    }

    @Test
    void deleteStudent(){
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("/student/" + savedStudents.get(0).getId(), HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }

    @Test
    void editStudent(){
        Student student = new Student();
        student.setName("gjdfnjgnd");
        student.setAge(21);
        HttpEntity<Student> entity = new HttpEntity<>(student);
        student.setId(savedStudents.get(0).getId());
        student.setFaculty(savedStudents.get(0).getFaculty());

        ResponseEntity<Student> response = this.restTemplate.exchange("/student" , HttpMethod.PUT, entity, Student.class);


        assertEquals(student, response.getBody());
    }



}
