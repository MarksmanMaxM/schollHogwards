package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId).get();
    }

    public Student updateStudent(Long studentId, Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }


    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> colorAge(int age) {
        List<Student> studentsPerAge = new ArrayList<>();
        List<Student> students = findAllStudents();
        for (Student student : students) {
            if (age == student.getAge()) {
                studentsPerAge.add(student);
            }
        }
        return studentsPerAge;
    }
}
