package gr.pada.bolosis.students_cv.controllers;

import gr.pada.bolosis.students_cv.exceptions.authorization.AuthorizationErrorResponse;
import gr.pada.bolosis.students_cv.exceptions.authorization.AuthorizationFailedException;
import gr.pada.bolosis.students_cv.services.UserService;
import gr.pada.bolosis.students_cv.utils.AuthorizeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @DeleteMapping(value = "/delete/profile/{username}")
    public ResponseEntity<?> deleteUserProfile(@PathVariable("username") String username, Principal principal)
            throws Exception {

        log.info("Delete profile for user {}", username);

        AuthorizeUtils.authorizeRequest(username, principal);

        userService.deleteUser(username);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(HttpStatus.NO_CONTENT);
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
