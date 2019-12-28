package gr.pada.bolosis.students_cv.convert.company;

import gr.pada.bolosis.students_cv.domain.Authority;
import gr.pada.bolosis.students_cv.domain.Company;
import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.enums.AuthorityType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyDtoToCompanyConverter implements Converter<CompanyDto, Company> {

    @Override
    public Company convert(CompanyDto companyDto) {

        return Company.builder()
                .email(companyDto.getEmail())
                .companyName(companyDto.getCompanyName())
                .units(companyDto.getUnits())
                .logoPath(companyDto.getLogoPath())
                .user(buildUser(companyDto.getUsername(), companyDto.getPassword()))
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
                .id(2L)
                .description(AuthorityType.ROLE_COMPANY.description())
                .build());

        return authorities;
    }
}
