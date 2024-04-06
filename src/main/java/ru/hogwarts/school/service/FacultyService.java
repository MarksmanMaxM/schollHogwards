package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

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

    public Collection<HashMap <Long, Faculty>> findAllFaculties() {
        return Collections.unmodifiableCollection(Collections.singleton(faculties));
    }

}
