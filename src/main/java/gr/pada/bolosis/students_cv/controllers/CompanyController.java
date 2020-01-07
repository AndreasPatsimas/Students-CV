package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.exceptions.authorization.AuthorizationErrorResponse;
import gr.pada.bolosis.students_cv.exceptions.authorization.AuthorizationFailedException;
import gr.pada.bolosis.students_cv.services.CompanyService;
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
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping(value = "/profile/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompanyDto getCompany(@PathVariable("username") String username, Principal principal) throws Exception {

        log.info("Fetch company with username: {} ", username);

        AuthorizeUtils.authorizeRequest(username, principal);

        return companyService.getCompanyByUsername(username);
    }

    @PutMapping(value = "/settings", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveCompanySettings(@RequestBody CompanyDto companyDto,
                                                 Principal principal) throws Exception{

        log.info("Save settings of company {}", companyDto.getUsername());

        AuthorizeUtils.authorizeRequest(companyDto.getUsername(), principal);

        companyService.saveCompanySettings(companyDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED);
    }

    @PostMapping("/saveImage/{username}")
    public ResponseEntity<?> saveCompanyImage(@RequestParam("file") MultipartFile file,
                                              @PathVariable("username") String username, Principal principal) {

        log.info("Upload file for user {}", username);

        AuthorizeUtils.authorizeRequest(username, principal);

        companyService.saveCompanyImage(file, username);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/search/students/{username}/{department}/{workExperience}"
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<StudentDto> searchStudents(@PathVariable("username") String username,
                                           @PathVariable("department") short department,
                                           @PathVariable("workExperience") boolean workExperience,
                                           Principal principal) throws Exception {

        log.info("Fetch company with username: {} ", username);

        AuthorizeUtils.authorizeRequest(username, principal);

        return companyService.getStudentsByDepartmentAndWorkExperience(department, workExperience);
    }

    @GetMapping(value = "/student/profile/{username}/{email}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StudentDto getStudent(@PathVariable("username") String username, @PathVariable("email") String email,
                                 Principal principal) throws Exception {

        log.info("Fetch student with email: {} ", email);

        AuthorizeUtils.authorizeRequest(username, principal);

        return companyService.getStudentByEmail(email);
    }

    @GetMapping("/downloadFile/{username}/{studentUsername}/{units}/{fileName:.+}")
    public ResponseEntity<Resource> downloadStudentCv(@PathVariable("username") String username,
                                                      @PathVariable("studentUsername") String studentUsername,
                                                      @PathVariable("units") Long units,
                                                      @PathVariable String fileName,
                                                      HttpServletRequest request, Principal principal) {

        log.info("Download cv of student {}", studentUsername);

        AuthorizeUtils.authorizeRequest(username, principal);

        Resource resource = companyService.downloadStudentCvByCompany(username, units, studentUsername, fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType(request, resource)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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
