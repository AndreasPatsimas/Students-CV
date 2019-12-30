package gr.pada.bolosis.students_cv.convert.cv;

import gr.pada.bolosis.students_cv.domain.Cv;
import gr.pada.bolosis.students_cv.dto.CvDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CvDtoToCvConverter implements Converter<CvDto, Cv> {

    @Override
    public Cv convert(CvDto cvDto) {

        return Cv.builder()
                .fileName(cvDto.getFileName())
                .fileDownloadUri(cvDto.getFileDownloadUri())
                .fileType(cvDto.getFileType())
                .size(cvDto.getSize())
                .timeInsert(Instant.now())
                .build();
    }
}
