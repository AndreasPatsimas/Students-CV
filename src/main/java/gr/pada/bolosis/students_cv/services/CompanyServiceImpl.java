package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.domain.Company;
import gr.pada.bolosis.students_cv.domain.Student;
import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.enums.Department;
import gr.pada.bolosis.students_cv.repositories.CompanyRepository;
import gr.pada.bolosis.students_cv.repositories.StudentRepository;
import gr.pada.bolosis.students_cv.repositories.UserRepository;
import gr.pada.bolosis.students_cv.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
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
    StudentService studentService;

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

        log.info("Save company settings with username: {} process begins", companyDto.getUsername());

        Optional<Company> companyOptional = findCompanyByUsername(companyDto.getUsername());

        companyOptional.ifPresent(company -> {

            company.setCompanyName(companyDto.getCompanyName());
            company.setEmail(companyDto.getEmail());
            company.setUnits(company.getUnits() + companyDto.getUnits());

            companyRepository.save(company);

            log.info("Save student settings process comleted");
        });
    }

    @Override
    public void saveCompanyImage(MultipartFile file, String username) {

        log.info("Upload image {} process begins", file.getOriginalFilename());

        Optional<Company> companyOptional = findCompanyByUsername(username);

        companyOptional.ifPresent(company -> {

            MyFileUtils.emptyDirectory(new File(imagePath + "companies/" + username));

            MyFileUtils.storeFile(file, imagePath + "companies/", username);

            company.setLogoPath(file.getOriginalFilename());

            companyRepository.save(company);

            log.info("Upload image process completed");
        });
    }

    @Override
    public List<StudentDto> getStudentsByDepartmentAndWorkExperience(short department, boolean workExperience) {

        log.info("Search students in department {} process begins", Department.fromValue(department));

        List<Student> students = studentRepository.findStudentByDepartmentAndWorkExperience(department,
                workExperience == true ? (short) 1 : (short) 0);

        List<StudentDto> studentDtos = new ArrayList<>(students.size());

        students.forEach(student -> studentDtos.add(conversionService.convert(student, StudentDto.class)));

        log.info("Search students process completed");

        return studentDtos;
    }

    @Override
    public StudentDto getStudentByEmail(String email) {
        log.info("Fetch student with email: {} process begins", email);

        Optional<Student> student = studentRepository.findStudentByEmail(email);

        if ((student.isPresent()))
            log.info("Fetch student process completed");

        else
            throw new RuntimeException("Student not found");

        return conversionService.convert(student.get(), StudentDto.class);
    }

    @Override
    public Resource downloadStudentCvByCompany(Long units, String studentUsername, String fileName) {

        if(units >= 1L)
            return studentService.downloadStudentCvAsResource(studentUsername, fileName);
        else
            throw new RuntimeException("No units to download a cv.");
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
