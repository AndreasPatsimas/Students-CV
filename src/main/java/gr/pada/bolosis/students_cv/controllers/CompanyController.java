package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.services.CompanyService;
import gr.pada.bolosis.students_cv.utils.AuthorizeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
}
