package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.dto.UserDto;
import gr.pada.bolosis.students_cv.exceptions.registration.RegistrationErrorResponse;
import gr.pada.bolosis.students_cv.exceptions.registration.RegistrationFailedException;
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
    public ResponseEntity<?> createStudent(@RequestBody StudentDto studentDto) throws Exception{

        log.info("Register student {}", studentDto.getUsername());

        registrationService.registerStudent(studentDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping(value = "/company", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createCompany(@RequestBody CompanyDto companyDto) throws Exception{

        log.info("Register company {}", companyDto.getUsername());

        registrationService.registerCompany(companyDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<RegistrationErrorResponse> exceptionHandler(Exception ex) {

        RegistrationErrorResponse authenticationErrorResponse = RegistrationErrorResponse.builder()
                .errorCode(HttpStatus.NOT_ACCEPTABLE.value())
                .status(HttpStatus.NOT_ACCEPTABLE)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<RegistrationErrorResponse>(authenticationErrorResponse, HttpStatus.NOT_ACCEPTABLE);
    }
}
