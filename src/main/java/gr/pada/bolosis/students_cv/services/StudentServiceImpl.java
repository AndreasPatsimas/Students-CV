package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.domain.Cv;
import gr.pada.bolosis.students_cv.domain.Student;
import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.CvDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import gr.pada.bolosis.students_cv.repositories.CvRepository;
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
import java.util.Optional;

@Service
@Slf4j
@PropertySource({ "classpath:application.properties" })
public class StudentServiceImpl implements StudentService {

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
    public StudentDto getStudentByUsername(String username) {

        log.info("Fetch student with username: {} process begins", username);

        Optional<Student> student = findStudentByUsername(username);

        log.info("Fetch student process completed");

        return conversionService.convert(student.get(), StudentDto.class);
    }

    @Override
    public void saveStudentSettings(StudentDto studentDto) {

        log.info("Save student settings with username: {} process begins", studentDto.getUsername());

        Optional<Student> studentOptional = findStudentByUsername(studentDto.getUsername());

        studentOptional.ifPresent(student -> {

            student.setEmail(studentDto.getEmail());
            student.setDescription(studentDto.getDescription());
            student.setMobilePhone(studentDto.getMobilePhone());
            student.setWorkExperience(studentDto.isWorkExperience() == true ? (short) 1 : (short) 0);

            studentRepository.save(student);

            log.info("Save student settings process comleted");
        });
    }

    @Override
    public CvDto uploadStudentCv(MultipartFile file, String username) {

        CvDto cvDto = cvService.uploadCv(file, username);

        Cv cv = conversionService.convert(cvDto, Cv.class);

        Optional<Student> studentOptional = findStudentByUsername(username);

        studentOptional.ifPresent(student -> {

            cv.setStudent(student);

            cvRepository.deleteCvByStudentId(student.getId());

            cvRepository.save(cv);

            log.info("Upload file process completed for user {}", username);
        });

        return cvDto;
    }

    @Override
    public Resource downloadStudentCvAsResource(String username, String fileName) {

        return cvService.loadCvAsResource(username, fileName);
    }

    @Override
    public void saveStudentImage(MultipartFile file, String username) {

        log.info("Upload image {} process begins", file.getOriginalFilename());

        Optional<Student> studentOptional = findStudentByUsername(username);

        studentOptional.ifPresent(student -> {

            MyFileUtils.emptyDirectory(new File(imagePath + "students/" + username));

            MyFileUtils.storeFile(file, imagePath + "students/", username);

            student.setImagePath(file.getOriginalFilename());

            studentRepository.save(student);

            log.info("Upload image process completed");
        });
    }

    private Optional<Student> findStudentByUsername(String username){

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){

            Optional<Student> student = studentRepository.findStudentByUserId(user.get().getId());

            if(student.isPresent()){

                return student;
            }
        }

        user.orElseThrow(() -> new UsernameNotFoundException("Not found student with username " + username));

        return null;
    }
}
