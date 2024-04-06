package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("faculty")
@RestController
public class FacultyController {

    private final FacultyService facultyService;


    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping()
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty.getId(), faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty deleteFaculty = facultyService.deleteFaculty(id);
        if (deleteFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deleteFaculty);
    }

    @GetMapping("/color")
    public ResponseEntity<HashMap<Long, Faculty>> getFacultyPerColor(@PathVariable String color) {
        HashMap<Long, Faculty> faculties = (HashMap<Long, Faculty>) facultyService.findAllFaculties();
        HashMap<Long, Faculty> facultiesPerColor = new HashMap<>();
        for (int i = 0; i < faculties.size(); i++) {
            if (faculties.get(i).getColor().equals(color)) {
                facultiesPerColor.get(faculties.get(i));
            }
        }

        if (facultiesPerColor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultiesPerColor);
    }
}
