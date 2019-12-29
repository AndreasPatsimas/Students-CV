package gr.pada.bolosis.students_cv;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {"spring.application.name=UserControllerTest",
        "spring.jmx.default-domain=UserControllerTest"})
public class UserControllerTest extends BasicWiremockTest {

    private static final String USERNAME = "billy";

    private static final Principal principal = () -> USERNAME;

    @Ignore
    @Test
    public void deleteUserProfile() throws Exception {

        this.mockMvc.perform(delete("/user/delete/profile/{username}", USERNAME)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().contentType("application/json"));
    }
}
