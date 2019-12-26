package gr.pada.bolosis.students_cv.convert;

import gr.pada.bolosis.students_cv.domain.Authority;
import gr.pada.bolosis.students_cv.domain.Student;
import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.enums.AuthorityType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDtoToStudentConverter implements Converter<StudentDto, Student> {

    @Override
    public Student convert(StudentDto studentDto) {
        return Student.builder()
                .email(studentDto.getEmail())
                .firstname(studentDto.getFirstname())
                .lastname(studentDto.getLastname())
                .department(studentDto.getDepartment())
                .workExperience(studentDto.isWorkExperience() == true ? (short)1 : (short)0)
                .birthday(studentDto.getDateOfbirth())
                .description(studentDto.getDescription())
                .imagePath(studentDto.getImagePath())
                .user(buildUser(studentDto.getUsername(), studentDto.getPassword()))
                .build();
    }

    private User buildUser(String username, String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .active((short) 1)
                .timeInsert(Instant.now())
                .authorities(buildAuthorities())
                .build();
    }

    private List<Authority> buildAuthorities(){

        List<Authority> authorities = new ArrayList<>();

        authorities.add(Authority.builder()
                .id(1L)
                .description(AuthorityType.ROLE_STUDENT.description())
                .build());

        return authorities;
    }
}
