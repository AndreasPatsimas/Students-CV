package gr.pada.bolosis.students_cv;

import gr.pada.bolosis.students_cv.dto.CompanyDto;
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
@TestPropertySource(properties = {"spring.application.name=CompanyControllerTest",
        "spring.jmx.default-domain=CompanyControllerTest"})
public class CompanyControllerTest extends BasicWiremockTest {

    private static final String USERNAME = "icap";

    private static final Principal principal = () -> USERNAME;

    private static final String EMAIL = "tasos@icap.gr";

    private static final String STUDENT_EMAIL = "andreas-patsim@hotmail.com";

    private static final String COMPANY_NAME = "ICAP AE";

    private static final Long UNITS = 10L;

    private static final String SAVE_IMAGE_NAME = "icap.jfif";

    private static final boolean WORK_EXPERIENCE = true;

    private static final short DEPARTMENT = (short) 6;

    private static final String STUDENT_USERNAME = "billy";

    private static final String DOWNLOAD_FILE_NAME = "erg.txt";

    @Test
    public void getCompany() throws Exception {

        this.mockMvc.perform(get("/company/profile/{username}", USERNAME)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void saveCompanySettings() throws Exception {

        CompanyDto companyDto = CompanyDto.builder()
                .username(USERNAME)
                .companyName(COMPANY_NAME)
                .email(EMAIL)
                .units(UNITS)
                .build();

        this.mockMvc.perform(
                put(CONTEXT_PATH + "/company/settings").contextPath(CONTEXT_PATH)
                        .content(asJsonString(companyDto))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Ignore
    @Test
    public void saveCompanyImage() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", SAVE_IMAGE_NAME,
                "multipart/form-data", is);

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/company/saveImage/{username}", USERNAME)
                .file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isAccepted()).andReturn();
    }

    @Test
    public void searchStudents() throws Exception {

        this.mockMvc.perform(get("/company/search/students/{username}/{department}/{workExperience}",
                USERNAME, DEPARTMENT, WORK_EXPERIENCE)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getStudent() throws Exception {

        this.mockMvc.perform(get("/company/student/profile/{username}/{email}", USERNAME, STUDENT_EMAIL)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void downloadStudentCv() throws Exception {

        this.mockMvc.perform(
                get(CONTEXT_PATH + "/company/downloadFile/{username}/{studentUsername}/{fileName:.+}",
                        USERNAME, STUDENT_USERNAME, DOWNLOAD_FILE_NAME)
                        .contextPath(CONTEXT_PATH)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE));
    }
}
