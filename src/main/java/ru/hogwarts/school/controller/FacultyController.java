package ru.hogwarts.school.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Stream;

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
    public List<Faculty> find (@RequestParam (name = "color") String color) {
        return facultyService.colorFind(color);
    }


    @GetMapping()
    public List<Faculty> getFaculties(@RequestParam (required = false) String color,
                                      @RequestParam (required = false) String name) {
        List<Faculty> list = new ArrayList<>();

        if(color != null && !color.isBlank())
        {
            list.add(facultyService.findByColorIgnoreCase(color));
            return list;

        }

        if(name != null && !name.isBlank())
        {
            list.add(facultyService.findByNameIgnoreCase1(name));
            return list;

        }

        return facultyService.findAllFaculties();
    }

    @GetMapping("/students")
    public Collection<Student> findStudents (@RequestParam (name = "name") String name) {
        return facultyService.findByNameIgnoreCase(name);
    }

    @GetMapping("/longnamefaculty")
    public String longNameFaculty () {
        List <Faculty> listFaculties = facultyService.findAllFaculties();
        List <String> namesFaculties = new ArrayList<>();
        namesFaculties = listFaculties.stream()
                .map(Faculty::getName)
                .toList();

        String max = Collections.max(namesFaculties, Comparator.comparing(s -> s.length()));

        return max;
    }

    @GetMapping("/sum_res")
    public Integer sumRes ()
    {
        int sum = Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );

        return sum;
    }

}
