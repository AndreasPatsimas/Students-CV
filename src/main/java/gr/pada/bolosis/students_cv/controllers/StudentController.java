package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://127.0.0.1:5501")
@RestController
@Slf4j
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping(value = "/profile/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StudentDto getStudent(@PathVariable("username") String username) throws Exception {

        log.info("Fetch student with username: {} ", username);

        return studentService.getStudentByUsername(username);
    }
}
