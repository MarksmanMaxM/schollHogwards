package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    public Collection<Student> findByAgeBetween(int min, int max);
    public Student findByName(String name);
    public Student getById(Long id);
    public Collection<Student> findAllByFaculty_Name(String name);

    @Query(value = "SELECT COUNT(name) FROM student  AS amount ", nativeQuery = true)
    int getCountByStudent();

    @Query(value = "SELECT AVG(age) FROM student  AS avrage ", nativeQuery = true)
    int getAvrAgeByStudent();

    @Query(value = "SELECT * FROM student GROUP BY id OFFSET (SELECT COUNT(name) FROM student)-5 ", nativeQuery = true)
    List<Student> getLastFiveStudents();

}
