package gr.pada.bolosis.students_cv.dto;

import gr.pada.bolosis.students_cv.enums.AuthenticationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwt;

    private AuthenticationStatus authenticationStatus;
}
