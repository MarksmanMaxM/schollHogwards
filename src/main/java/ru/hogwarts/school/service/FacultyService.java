package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
         this.facultyRepository.save(faculty);
        return faculty;
    }


    public Faculty getFaculty(Long facultyId) {
        return facultyRepository.findById(facultyId).get();
    }

    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        facultyRepository.save(faculty);
        return faculty;
    }

    public void deleteFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    public List<Faculty>  findAllFaculties() {
        return facultyRepository.findAll();
    }

    public List<Faculty> colorFind(String color) {

        List<Faculty> facultiesPerColor = new ArrayList<>();
        List<Faculty> faculties = findAllFaculties();
        for (Faculty faculty : faculties) {
            if (color.equals(faculty.getColor())) {
                facultiesPerColor.add(faculty);
            }
        }
        return facultiesPerColor;
    }

}
