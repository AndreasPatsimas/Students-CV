package gr.pada.bolosis.students_cv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String username;

    private String password;

    private String email;

    private String companyName;

    private Long units;

    private String logoPath;
}
