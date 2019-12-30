package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.exceptions.authorization.AuthorizationErrorResponse;
import gr.pada.bolosis.students_cv.exceptions.authorization.AuthorizationFailedException;
import gr.pada.bolosis.students_cv.services.StudentService;
import gr.pada.bolosis.students_cv.utils.AuthorizeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@CrossOrigin(origins = "*")
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
//https://howtodoinjava.com/spring-restful/http-options-request-handler/
//https://stackoverflow.com/questions/45347718/spring-restful-handling-options-request
//https://www.google.com/search?sxsrf=ACYBGNTxsRcNOvrJVC76mxT47wtS_jDUbg%3A1577741465485&source=hp&ei=mWwKXsPFG9rBmwWluZGwDQ&q=request+method+options+and+rest+api+call+with+spring&oq=request+method+options+and+rest+api+call+with+sp&gs_l=psy-ab.3.1.33i160l3.6045.31640..32862...25.0..0.198.8389.0j54....2..0....1..gws-wiz.....10..35i362i39j0i131j0i322i131j0i322j0j0i22i30j0i22i10i30j33i22i29i30j35i39i19j35i39j0i10j0i19j0i22i30i19j33i21.Rhc3tJrPfXE
    @PutMapping(value = "/settings", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveStudentSettings(@RequestBody StudentDto studentDto,
                                                 Principal principal) throws Exception{

        log.info("Save settings of student {}", studentDto.getUsername());

        AuthorizeUtils.authorizeRequest(studentDto.getUsername(), principal);

        studentService.saveStudentSettings(studentDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("SAVED");
    }

    @PostMapping("/uploadFile/{username}")
    public ResponseEntity<?> uploadStudentCv(@RequestParam("file") MultipartFile file,
                                             @PathVariable("username") String username, Principal principal) {

        log.info("Upload file for user {}", username);

        AuthorizeUtils.authorizeRequest(username, principal);

        studentService.uploadStudentCv(file, username);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("UPLOADED");
    }

    @GetMapping("/downloadFile/{username}/{fileName:.+}")
    public ResponseEntity<Resource> downloadStudentCv(@PathVariable("username") String username,
                                                      @PathVariable String fileName,
                                                      HttpServletRequest request, Principal principal) {

        log.info("Download cv of user {}", username);

        AuthorizeUtils.authorizeRequest(username, principal);

        Resource resource = studentService.downloadStudentCvAsResource(username, fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType(request, resource)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/saveImage/{username}")
    public ResponseEntity<?> saveStudentImage(@RequestParam("file") MultipartFile file,
                                             @PathVariable("username") String username, Principal principal) {

        log.info("Upload file for user {}", username);

        AuthorizeUtils.authorizeRequest(username, principal);

        studentService.saveStudentImage(file, username);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("UPLOADED");
    }

    private String contentType(HttpServletRequest request, Resource resource){

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return contentType;
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
