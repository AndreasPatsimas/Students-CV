package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.dto.*;
import gr.pada.bolosis.students_cv.enums.AuthenticationStatus;
import gr.pada.bolosis.students_cv.exceptions.AuthenticationErrorResponse;
import gr.pada.bolosis.students_cv.exceptions.AuthenticationFailedException;
import gr.pada.bolosis.students_cv.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createAuthenticateToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        log.info("Authenticate user {}", authenticationRequest.getUsername());

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ChangePasswordResponse changePassword(@RequestBody ChangePasswordRequest request) throws Exception {
        log.info("Change password process started for user {}", request.getUsername());

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
