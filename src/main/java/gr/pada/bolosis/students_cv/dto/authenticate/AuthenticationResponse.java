package gr.pada.bolosis.students_cv.dto.authenticate;

import gr.pada.bolosis.students_cv.enums.AuthenticationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwt;

    private String username;

    private List<GrantedAuthority> authorities;

    private AuthenticationStatus authenticationStatus;
}
