package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CompanyService {

    CompanyDto getCompanyByUsername(String username);

    void saveCompanySettings(CompanyDto companyDto);

    void saveCompanyImage(MultipartFile file, String username);

    List<StudentDto> getStudentsByDepartmentAndWorkExperience(short department, boolean workExperience);

    StudentDto getStudentByEmail(String email);

    Resource downloadStudentCvByCompany(String username, Long units, String studentUsername, String fileName);
}
