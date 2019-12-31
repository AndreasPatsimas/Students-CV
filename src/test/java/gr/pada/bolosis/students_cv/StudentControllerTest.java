package gr.pada.bolosis.students_cv;

import gr.pada.bolosis.students_cv.dto.StudentDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {"spring.application.name=StudentControllerTest",
        "spring.jmx.default-domain=StudentControllerTest"})
public class StudentControllerTest extends BasicWiremockTest {

    private static final String USERNAME = "sotiris";

    private static final String EMAIL = "sotirinio@hotmail.com";

    private static final String DESCRIPTION = "I am very good at football!!!";

    private static final Long MOBILE_PHONE = 6986803782L;

    private static final boolean WORK_EXPERIENCE = true;

    private static final String UPLOAD_FILE_NAME = "erg.txt";

    private static final String UPLOAD_FILE_NAME_1 = "mysql-init.txt";

    private static final String SAVE_IMAGE_NAME = "sotiris.jpg";

    private static final String DOWNLOAD_FILE_NAME = "erg.txt";

    private static final String DOWNLOAD_FILE_NAME_1 = "mysql-init.txt";

    private static final Principal principal = () -> USERNAME;


    @Test
    public void getStudent() throws Exception {

        this.mockMvc.perform(get("/student/profile/{username}", USERNAME)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void saveStudentSettings() throws Exception {

        StudentDto studentDto = StudentDto.builder()
                .username(USERNAME)
                .email(EMAIL)
                .mobilePhone(MOBILE_PHONE)
                .workExperience(WORK_EXPERIENCE)
                .description(DESCRIPTION)
                .build();

        this.mockMvc.perform(
                put(CONTEXT_PATH + "/student/settings").contextPath(CONTEXT_PATH)
                        .content(asJsonString(studentDto))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void uploadStudentCv() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", UPLOAD_FILE_NAME_1,
                "multipart/form-data", is);

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/student/uploadFile/{username}", USERNAME)
                .file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isAccepted()).andReturn();
    }

    @Test
    public void downloadStudentCv() throws Exception {

        this.mockMvc.perform(
                get(CONTEXT_PATH + "/student/downloadFile/{username}/{fileName:.+}", USERNAME, DOWNLOAD_FILE_NAME_1)
                        .contextPath(CONTEXT_PATH)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE));
    }

    @Ignore
    @Test
    public void saveStudentImage() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", SAVE_IMAGE_NAME,
                "multipart/form-data", is);

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/student/saveImage/{username}", USERNAME)
                .file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isAccepted()).andReturn();
    }
}
