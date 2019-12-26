package gr.pada.bolosis.students_cv;

import gr.pada.bolosis.students_cv.dto.UserDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {"spring.application.name=RegistrationControllerTest",
        "spring.jmx.default-domain=RegistrationControllerTest"})
public class RegistrationControllerTest extends BasicWiremockTest {

    private static final String USERNAME = "andriana";

    private static final String PASSWORD = "anyfi";

    private static final String EMAIL = "andri@gmail.com";

    @Ignore
    @Test
    public void createUser() throws Exception {

        UserDto userDto = UserDto
                .builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        this.mockMvc.perform(
                post(CONTEXT_PATH + "/register").contextPath(CONTEXT_PATH)
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
