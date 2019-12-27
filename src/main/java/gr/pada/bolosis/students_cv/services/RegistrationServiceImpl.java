package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.domain.Company;
import gr.pada.bolosis.students_cv.domain.Student;
import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.exceptions.registration.RegistrationFailedException;
import gr.pada.bolosis.students_cv.repositories.CompanyRepository;
import gr.pada.bolosis.students_cv.repositories.StudentRepository;
import gr.pada.bolosis.students_cv.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ConversionService conversionService;

    @Override
    public void registerStudent(StudentDto studentDto) {

        log.info("Registration process for student {} begins", studentDto.getUsername());

        try{

            studentRepository.save(conversionService.convert(studentDto, Student.class));

            MyFileUtils.createMultipleDirectories("C:/Student-CV/students/"
                    + studentDto.getUsername() + "/cv");

//            MyFileUtils.createMultipleDirectories("C:/Student-CV/students/"
//                    + studentDto.getUsername() + "/image");
        }
        catch (Exception e){
            throw new RegistrationFailedException("Registration process failed.", e);
        }

        log.info("Registration process completed");
    }

    @Override
    public void registerCompany(CompanyDto companyDto) {

        log.info("Registration process for company {} begins", companyDto.getUsername());

        try{

            companyRepository.save(conversionService.convert(companyDto, Company.class));
        }
        catch (Exception e){

            throw new RegistrationFailedException("Registration process failed.", e);
        }

        log.info("Registration process completed");
    }
}
