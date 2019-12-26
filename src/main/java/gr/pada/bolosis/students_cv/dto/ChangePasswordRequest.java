package gr.pada.bolosis.students_cv.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ChangePasswordRequest {

    private String username;

    private String oldPassword;

    private String newPassword;
}
