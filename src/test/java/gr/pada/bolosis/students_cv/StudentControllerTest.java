package gr.pada.bolosis.students_cv;

import gr.pada.bolosis.students_cv.dto.StudentDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
