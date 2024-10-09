package ru.hogwarts.school.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

@RequestMapping("/student")
@RestController
public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if(student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping()
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
//        if(updatedStudent == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);

        return ResponseEntity.ok().build();
    }



    @GetMapping()
    public List<Student> getStudents() {
        return studentService.findAllStudents();
    }

    @GetMapping("/age")
    public Collection find (@RequestParam (name = "age") int age) {


        return studentService.colorAge(age);


    }

    @GetMapping("/age/minmax")
    public Collection find (@RequestParam (required = false) int min,
                            @RequestParam (required = false) int max) {

        if(min > 0 && max < 100 && max > min)
            return studentService.findByAgeBetween(min, max);

        return null;


    }


    @GetMapping("/faculty")
    public String find (@RequestParam String name) {

        return studentService.findByName(name).getFaculty().getName();

    }

    @GetMapping("/studentcount")
    public String getStudentCount()
    {
        return Integer.toString(studentService.getCountByStudent());
    }

    @GetMapping("/avrage")
    public String getStudentAvrAge()
    {
        return Integer.toString(studentService.getAvrAgeByStudent());
    }

    @GetMapping("/fivestudents")
    public List<Student> getLastFiveStudents()
    {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/sorted_A")
    public List<String> getAllStudentsNameA()
    {
        List <String> listMames = new ArrayList<>();
        List <Student> allStudents = studentService.findAllStudents();
        listMames = allStudents.stream()
                .map(e -> e.getName().toUpperCase())
                .filter(n -> n.startsWith("A"))
                .sorted()
                .toList();

        return listMames;
    }

    @GetMapping("/avr_age")
    public OptionalDouble getAllStudentsAvrAge()
    {
        List <Student> allStudents = studentService.findAllStudents();
        OptionalDouble avrAge = allStudents.stream()
                .map(e -> e.getAge())
                .mapToInt(Integer::intValue).average();

        return avrAge;
    }

}
