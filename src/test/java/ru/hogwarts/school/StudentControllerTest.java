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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import javax.print.DocFlavor;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Student student = new Student();
        student.setAge(18);
        student.setName("jhgjhgj");

        Student student2 = new Student();
        student2.setAge(19);
        student2.setName("ddffdfaa");

        List<Student> studentList = List.of(student, student2);
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
        List<Student> students = response.getBody().stream().collect(Collectors.toList());
        assertEquals(savedStudents, students);


    }

    @Test
    void addStudent() throws JSONException, JsonProcessingException{
        Student student1 = new Student();
        student1.setAge(18);
        student1.setName("dddddd");

        String expected = mapper.writeValueAsString(student1);
        ResponseEntity<String> response = restTemplate.postForEntity("/student", student1, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);
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

        ResponseEntity<Student> response = this.restTemplate.exchange("/student/" + savedStudents.get(0).getId(), HttpMethod.PUT, entity, Student.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(student, response.getBody());
    }



}
