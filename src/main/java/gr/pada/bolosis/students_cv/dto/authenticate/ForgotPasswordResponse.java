package gr.pada.bolosis.students_cv.dto.authenticate;

import gr.pada.bolosis.students_cv.enums.ForgotPasswordStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ForgotPasswordResponse {

    private ForgotPasswordStatus forgotPasswordStatus;

    private String resetKeyPassword;
}
