package gr.pada.bolosis.students_cv;

import gr.pada.bolosis.students_cv.dto.authenticate.AuthenticationRequest;
import gr.pada.bolosis.students_cv.dto.authenticate.ChangePasswordRequest;
import gr.pada.bolosis.students_cv.dto.authenticate.ForgotPasswordRequest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {"spring.application.name=AuthenticationControllerTest",
        "spring.jmx.default-domain=AuthenticationControllerTest"})
public class AuthenticationControllerTest extends BasicWiremockTest {

    private static final String USERNAME = "sotiris";

    private static final String PASSWORD = "fenomeno";

    private static final String WRONG_PASSWORD = "fenomen";

    private static final String EMAIL = "sotirinio@hotmail.com";

    @Test
    public void authenticateSuccess() throws Exception {

        AuthenticationRequest authenticationRequest = AuthenticationRequest
                .builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        this.mockMvc.perform(
                post(CONTEXT_PATH + "/authenticate").contextPath(CONTEXT_PATH)
                        .content(asJsonString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void authenticateFailed() throws Exception {

        AuthenticationRequest authenticationRequest = AuthenticationRequest
                .builder()
                .username(USERNAME)
                .password(WRONG_PASSWORD)
                .build();

        this.mockMvc.perform(
                post(CONTEXT_PATH + "/authenticate").contextPath(CONTEXT_PATH)
                        .content(asJsonString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void changePasswordSuccess() throws Exception {

        Principal principal = () -> USERNAME;

        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
                .username(USERNAME)
                .oldPassword(PASSWORD)
                .newPassword(PASSWORD)
                .build();

        this.mockMvc.perform(
                post(CONTEXT_PATH + "/authenticate/changePassword").contextPath(CONTEXT_PATH)
                        .content(asJsonString(changePasswordRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Ignore
    @Test
    public void forgotPasswordSuccess() throws Exception {

        ForgotPasswordRequest forgotPasswordRequest = ForgotPasswordRequest.builder()
                .username(USERNAME)
                .email(EMAIL)
                .build();

        this.mockMvc.perform(
                post(CONTEXT_PATH + "/authenticate/forgotPassword").contextPath(CONTEXT_PATH)
                        .content(asJsonString(forgotPasswordRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
}
