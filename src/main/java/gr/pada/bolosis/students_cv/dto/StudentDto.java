package gr.pada.bolosis.students_cv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private Long mobilePhone;

    private LocalDate dateOfbirth;

    private short department;

    private boolean workExperience;

    private String description;

    private String imagePath;
}
