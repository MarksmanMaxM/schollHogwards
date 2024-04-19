package ru.hogwarts.school.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/faculty")
@RestController
public class FacultyController {

    private final FacultyService facultyService;


    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping()
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

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
       facultyService.deleteFaculty(id);
       return ResponseEntity.ok().build();
    }

    @GetMapping("/color")
    public List<Faculty> find (String color) {
        return facultyService.colorFind(color);
    }
//    public ResponseEntity<HashMap<Long, Faculty>> getFacultyPerColor(@PathVariable String color) {
////        HashMap<Long, Faculty> faculties = new HashMap<>();
////        faculties.putAll((Map<? extends Long, ? extends Faculty>) facultyService.findAllFaculties());
////        faculties.putAll((Map<? extends Long, ? extends Faculty>) facultyService.findAllFaculties());
////        HashMap<Long, Faculty> facultiesPerColor = new HashMap<>();
////        for (Long i: faculties.keySet()) {
////            if (faculties.get(i).getColor().equals(color)) {
////                facultiesPerColor.put((long) i, faculties.get(i));
////            }
////        }
//
//        if (facultyService.colorFind(color) == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(facultyService.colorFind(color));
//     }


    @GetMapping()
    public List<Faculty> getFaculties() {
        return facultyService.findAllFaculties();
    }
}
