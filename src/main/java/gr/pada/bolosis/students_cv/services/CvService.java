package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.dto.CvDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CvService {

    CvDto uploadCv(MultipartFile file, String username);

    Resource loadCvAsResource(String username, String fileName);
}
