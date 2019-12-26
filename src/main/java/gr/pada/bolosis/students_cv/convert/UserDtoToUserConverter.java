package gr.pada.bolosis.students_cv.convert;

import gr.pada.bolosis.students_cv.domain.Authority;
import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.UserDto;
import gr.pada.bolosis.students_cv.enums.AuthorityType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto userDto) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .active((short) 1)
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
