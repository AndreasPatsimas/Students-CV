package gr.pada.bolosis.students_cv.dto;

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
