package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarService avatarService;
    @MockBean
    StudentRepository studentRepository;


    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void testFaculty() throws Exception{

        final String name = "gggg";
        final String color = "red";
        final long id = 2;
        Collection<Student> students = null;

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        //faculty.setStudents(students);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        facultyObject.put("students", students);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findByNameIgnoreCase(name)).thenReturn(faculty);
        when(facultyRepository.findByColorIgnoreCase(color)).thenReturn(faculty);
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        when(facultyRepository.findAll()).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color?color=" + color)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());





    }





}
