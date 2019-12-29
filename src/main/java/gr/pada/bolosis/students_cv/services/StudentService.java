package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.dto.StudentDto;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    StudentDto getStudentByUsername(String username);

    void saveStudentSettings(StudentDto studentDto);
}
