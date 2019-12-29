package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.exceptions.authorization.AuthorizationErrorResponse;
import gr.pada.bolosis.students_cv.exceptions.authorization.AuthorizationFailedException;
import gr.pada.bolosis.students_cv.services.StudentService;
import gr.pada.bolosis.students_cv.utils.AuthorizeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("http://127.0.0.1:5501")
@RestController
@Slf4j
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping(value = "/profile/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StudentDto getStudent(@PathVariable("username") String username, Principal principal) throws Exception {

        log.info("Fetch student with username: {} ", username);

        AuthorizeUtils.authorizeRequest(username, principal);

        return studentService.getStudentByUsername(username);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<AuthorizationErrorResponse> exceptionHandler(Exception ex) {

        AuthorizationErrorResponse authorizationErrorResponse = AuthorizationErrorResponse.builder()
                .errorCode(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<AuthorizationErrorResponse>(authorizationErrorResponse, HttpStatus.FORBIDDEN);
    }
}
