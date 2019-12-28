package gr.pada.bolosis.students_cv.dto.authenticate;

import gr.pada.bolosis.students_cv.enums.ChangePasswordStatus;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ChangePasswordResponse {

    private ChangePasswordStatus changePasswordStatus;
}
