package gr.pada.bolosis.students_cv.convert.student;

import gr.pada.bolosis.students_cv.domain.Cv;
import gr.pada.bolosis.students_cv.domain.Student;
import gr.pada.bolosis.students_cv.dto.CvDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {

    @Override
    public StudentDto convert(Student student) {
        return StudentDto.builder()
                .username(student.getUser().getUsername())
                .firstname(student.getFirstname())
                .lastname(student.getLastname())
                .email(student.getEmail())
                .mobilePhone(student.getMobilePhone())
                .dateOfbirth(student.getBirthday())
                .department(student.getDepartment())
                .workExperience(student.getWorkExperience() == (short) 1 ? true : false)
                .description(student.getDescription())
                .imagePath(student.getImagePath())
                .cv(buildCv(student.getCv()))
                .build();
    }

    private CvDto buildCv(Cv cv){

        if (cv != null)
            return CvDto.builder()
                    .fileName(cv.getFileName())
                    .fileDownloadUri(cv.getFileDownloadUri())
                    .fileType(cv.getFileType())
                    .size(cv.getSize())
                    .build();
        else
            return null;
    }
}
