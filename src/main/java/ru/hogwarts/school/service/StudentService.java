package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId).get();
    }

    public Student updateStudent(Student student) {
        Student oldStudent = studentRepository.findById(student.getId()).orElseThrow();
        oldStudent.setName(student.getName());
        oldStudent.setAge(student.getAge());

        if (student.getFaculty() != null) {
            Faculty faculty = facultyRepository.findById(student.getFaculty().getId()).orElseThrow();
            oldStudent.setFaculty(faculty);
        }


        return studentRepository.save(oldStudent);
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

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);

    }

    public Student findByName(String name){

        return studentRepository.findByName(name);
    }

    public int getCountByStudent()
    {
        return studentRepository.getCountByStudent();
    }

    public int getAvrAgeByStudent()
    {
        return studentRepository.getAvrAgeByStudent();
    }

    public List<Student> getLastFiveStudents()
    {
        return studentRepository.getLastFiveStudents();
    }

}
