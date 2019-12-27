package gr.pada.bolosis.students_cv.exceptions.authentication;

import gr.pada.bolosis.students_cv.enums.AuthenticationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationErrorResponse {

    private int errorCode;

    private HttpStatus status;

    private String message;

    private AuthenticationStatus authenticationStatus;
}
