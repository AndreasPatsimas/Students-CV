package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.dto.authenticate.*;
import gr.pada.bolosis.students_cv.enums.AuthenticationStatus;
import gr.pada.bolosis.students_cv.exceptions.authentication.*;
import gr.pada.bolosis.students_cv.services.AuthenticationService;
import gr.pada.bolosis.students_cv.utils.AuthorizeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping(value = "/authenticate")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createAuthenticateToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        log.info("Authenticate user {}", authenticationRequest.getUsername());

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ChangePasswordResponse changePassword(@RequestBody ChangePasswordRequest request, Principal principal)
            throws Exception {

        log.info("Change password process started for user {}", request.getUsername());

        AuthorizeUtils.authorizeRequest(request.getUsername(), principal);

        return authenticationService.changePassword(request);
    }

    @PostMapping(value = "/forgotPassword", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ForgotPasswordResponse forgotPassword(@RequestBody ForgotPasswordRequest request) throws Exception {

        log.info("Forgot password process started fo user{} ", request.getUsername());

        return authenticationService.forgotPassword(request);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<AuthenticationErrorResponse> exceptionHandler(Exception ex) {

        AuthenticationErrorResponse authenticationErrorResponse = AuthenticationErrorResponse.builder()
                .errorCode(HttpStatus.NOT_ACCEPTABLE.value())
                .status(HttpStatus.NOT_ACCEPTABLE)
                .message(ex.getMessage())
                .authenticationStatus(AuthenticationStatus.AUTHENTICATION_FAILED)
                .build();

        return new ResponseEntity<AuthenticationErrorResponse>(authenticationErrorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

}
