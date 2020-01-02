package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.dto.CvDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StudentService {

    StudentDto getStudentByUsername(String username);

    void saveStudentSettings(StudentDto studentDto);

    CvDto uploadStudentCv(MultipartFile file, String username);

    Resource downloadStudentCvAsResource(String username, String fileName);

    void saveStudentImage(MultipartFile file, String username);
}
