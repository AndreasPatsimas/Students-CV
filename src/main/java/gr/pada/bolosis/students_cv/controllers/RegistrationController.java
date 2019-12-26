package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.dto.UserDto;
import gr.pada.bolosis.students_cv.services.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5501/")
@RestController
@Slf4j
@RequestMapping(value = "/register")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping(value = "/student", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createUser(@RequestBody StudentDto studentDto) throws Exception{

        log.info("Register student {}", studentDto.getUsername());

        registrationService.registerStudent(studentDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
