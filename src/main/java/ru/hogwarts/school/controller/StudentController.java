package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;

@RequestMapping("student")
@RestController
public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
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
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student.getId(), student);
        if(updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student deleteStudent = studentService.deleteStudent(id);
        if (deleteStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deleteStudent);
    }

    @GetMapping("/age")
    public ResponseEntity<HashMap<Long, Student>> getStudentPerAge(@PathVariable int age) {
        HashMap<Long, Student> students = (HashMap<Long, Student>) studentService.findAllStudents();
        HashMap<Long, Student> studentsPerAge = new HashMap<>();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getAge() == age) {
                studentsPerAge.get(students.get(i));
            }
        }

        if (studentsPerAge == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsPerAge);
    }
}
