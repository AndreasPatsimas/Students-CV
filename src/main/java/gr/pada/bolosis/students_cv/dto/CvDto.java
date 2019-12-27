package gr.pada.bolosis.students_cv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CvDto {

    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private Long size;
}
