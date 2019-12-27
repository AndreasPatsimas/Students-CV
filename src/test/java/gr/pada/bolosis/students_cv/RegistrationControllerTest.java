package gr.pada.bolosis.students_cv;

import gr.pada.bolosis.students_cv.dto.CompanyDto;
import gr.pada.bolosis.students_cv.dto.StudentDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {"spring.application.name=RegistrationControllerTest",
        "spring.jmx.default-domain=RegistrationControllerTest"})
public class RegistrationControllerTest extends BasicWiremockTest {

    //Student credits
    private static final String USERNAME = "andriana";

    private static final String PASSWORD = "anyfi";

    private static final String EMAIL = "andri@gmail.com";

    private static final String FIRSTNAME = "Andriana";

    private static final String LASTNAME = "Bolosi";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
    private static String date = "1991-07-17";
    private static final LocalDate DOB = LocalDate.parse(date,  formatter);

    private static final Long MOBILE_PHONE = 6978327820L;

    private static final short DEPARTMENT = (short)3;

    private static final boolean WORK_EXPERIENCE = false;

    //company credits
    private static final String USERNAME_COMPANY = "icap";

    private static final String PASSWORD_COMPANY = "icap123";

    private static final String EMAIL_COMPANY = "tasos@icap.gr";

    private static final String COMPANY_NAME = "ICAP AE";

    @Ignore
    @Test
    public void createStudent() throws Exception {

        StudentDto studentDto = StudentDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .email(EMAIL)
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .dateOfbirth(DOB)
                .mobilePhone(MOBILE_PHONE)
                .department(DEPARTMENT)
                .workExperience(WORK_EXPERIENCE)
                .build();

        this.mockMvc.perform(
                post(CONTEXT_PATH + "/register/student").contextPath(CONTEXT_PATH)
                        .content(asJsonString(studentDto))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void createCompany() throws Exception {

        CompanyDto companyDto = CompanyDto.builder()
                .username(USERNAME_COMPANY)
                .password(PASSWORD_COMPANY)
                .email(EMAIL_COMPANY)
                .companyName(COMPANY_NAME)
                .build();

        this.mockMvc.perform(
                post(CONTEXT_PATH + "/register/company").contextPath(CONTEXT_PATH)
                        .content(asJsonString(companyDto))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
