package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

    void registerStudent(StudentDto studentDto);

    void registerCompany(CompanyDto companyDto);
}
