package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

@Service
public class FacultyService {
    private HashMap<Long, Faculty> faculties = new HashMap<>();
    private Long id = 0L;
    public Faculty createFaculty(Faculty faculty) {
        ++id;
        this.faculties.put(id, faculty);
        return faculty;
    }


    public Faculty getFaculty(Long facultyId) {
        return faculties.get(facultyId);
    }

    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        faculties.put(facultyId, faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long facultyId) {
        return faculties.remove(facultyId);
    }

    public Object findAllFaculties() {
        return Collections.unmodifiableCollection(Collections.singleton(faculties));
    }

    public HashMap<Long, Faculty> colorFind(String color) {
        HashMap<Long, Faculty> facultiesPerColor = new HashMap<>();
                for (Long i: faculties.keySet()) {
            if (faculties.get(i).getColor().equals(color)) {
                facultiesPerColor.put((long) i, faculties.get(i));
            }
        }
        return facultiesPerColor;
    }

}
