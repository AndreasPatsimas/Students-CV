package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.domain.Company;
import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.repositories.CompanyRepository;
import gr.pada.bolosis.students_cv.repositories.CvRepository;
import gr.pada.bolosis.students_cv.repositories.StudentRepository;
import gr.pada.bolosis.students_cv.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@PropertySource({ "classpath:application.properties" })
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CvRepository cvRepository;

    @Autowired
    CvService cvService;

    @Autowired
    ConversionService conversionService;

    @Value("${image.path}")
    private String imagePath;

    @Override
    public CompanyDto getCompanyByUsername(String username) {
        log.info("Fetch company with username: {} process begins", username);

        Optional<Company> company = findCompanyByUsername(username);

        log.info("Fetch company process completed");

        return conversionService.convert(company.get(), CompanyDto.class);
    }

    @Override
    public void saveCompanySettings(CompanyDto companyDto) {

    }

    @Override
    public void saveCompanyImage(MultipartFile file, String username) {

    }

    @Override
    public List<StudentDto> getStudentsByDepartmentAndWorkExperience(short department, boolean workExperience) {
        return null;
    }

    @Override
    public StudentDto getStudentByEmail(String email) {
        return null;
    }

    private Optional<Company> findCompanyByUsername(String username){

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){

            Optional<Company> company = companyRepository.findCompanyByUserId(user.get().getId());

            if(company.isPresent()){

                return company;
            }
        }

        user.orElseThrow(() -> new UsernameNotFoundException("Not found company with username " + username));

        return null;
    }
}
