package gr.pada.bolosis.students_cv.convert.company;

import gr.pada.bolosis.students_cv.domain.Company;
import gr.pada.bolosis.students_cv.dto.CompanyDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyToCompanyDtoConverter implements Converter<Company, CompanyDto> {

    @Override
    public CompanyDto convert(Company company) {

        return CompanyDto.builder()
                .username(company.getUser().getUsername())
                .email(company.getEmail())
                .companyName(company.getCompanyName())
                .units(company.getUnits())
                .logoPath(company.getLogoPath())
                .build();
    }
}
