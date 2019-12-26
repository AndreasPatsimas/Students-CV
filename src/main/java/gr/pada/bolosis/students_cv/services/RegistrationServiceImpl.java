package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.domain.Student;
import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.dto.UserDto;
import gr.pada.bolosis.students_cv.repositories.StudentRepository;
import gr.pada.bolosis.students_cv.repositories.UserRepository;
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
    ConversionService conversionService;

    @Override
    public void registerStudent(StudentDto studentDto) {

        log.info("Registration process for student {} begins", studentDto.getUsername());

        studentRepository.save(conversionService.convert(studentDto, Student.class));

        log.info("Registration process completed");
    }
}
