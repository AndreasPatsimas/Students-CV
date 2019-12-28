package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.domain.Student;
import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.repositories.StudentRepository;
import gr.pada.bolosis.students_cv.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConversionService conversionService;

    @Override
    public StudentDto getStudentByUsername(String username) {

        log.info("Fetch student with username: {} process begins", username);

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){

            Optional<Student> student = studentRepository.findStudentByUserId(user.get().getId());

            if(student.isPresent()){

                log.info("Fetch student process completed");

                return conversionService.convert(student.get(), StudentDto.class);
            }
        }

        user.orElseThrow(() -> new UsernameNotFoundException("Not found " + username));

        return null;
    }
}
