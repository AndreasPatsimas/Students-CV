package gr.pada.bolosis.students_cv.exceptions.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationErrorResponse {

    private int errorCode;

    private HttpStatus status;

    private String message;
}
