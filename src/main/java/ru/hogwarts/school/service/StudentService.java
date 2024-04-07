package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collections;
import java.util.HashMap;
@Service
public class StudentService {
    private HashMap<Long, Student> students = new HashMap<>();
    private Long id = 0L;

    public Student createStudent(Student student) {
        ++id;
        this.students.put(id, student);
        return student;
    }

    public Student getStudent(Long studentId) {
        return students.get(studentId);
    }

    public Student updateStudent(Long studentId, Student student) {
        students.put(studentId, student);
        return student;
    }

    public Student deleteStudent(Long studentId) {
        return students.remove(studentId);
    }


    public Object findAllStudents() {
        return Collections.unmodifiableCollection(Collections.singleton(students));
    }

    public HashMap<Long, Student> colorAge(int age) {
        HashMap<Long, Student> studentsPerAge = new HashMap<>();
        for (Long i: students.keySet()) {
            if (students.get(i).getAge() == age) {
                studentsPerAge.put((long) i, students.get(i));
            }
        }
        return studentsPerAge;
    }
}
