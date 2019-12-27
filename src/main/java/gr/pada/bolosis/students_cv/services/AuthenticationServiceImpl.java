package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.domain.Student;
import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.*;
import gr.pada.bolosis.students_cv.enums.*;
import gr.pada.bolosis.students_cv.exceptions.authentication.AuthenticationFailedException;
import gr.pada.bolosis.students_cv.repositories.*;
import gr.pada.bolosis.students_cv.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@PropertySource(value = "classpath:application.properties")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

    @Value("${email.address.from}")
    private String mailSender;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws Exception {

        log.info("Authentication processs begins");

        authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        log.info("Authentication processs completed");

        return AuthenticationResponse.builder()
                .jwt(jwt)
                .username(userDetails.getUsername())
                .authorities((List<GrantedAuthority>) userDetails.getAuthorities())
                .authenticationStatus(AuthenticationStatus.AUTHENTICATION_SUCCEEDED)
                .build();
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) {

        log.info("Change password processs begins");

        authenticate(new UsernamePasswordAuthenticationToken(changePasswordRequest.getUsername(),
                changePasswordRequest.getOldPassword()));

        Optional<User> user = userRepository.findByUsername(changePasswordRequest.getUsername());

        if(user.isPresent()){

            saveBcryptedPassword(changePasswordRequest.getNewPassword(), user.get());

            log.info("Change password processs completed");

            return ChangePasswordResponse.builder()
                    .changePasswordStatus(ChangePasswordStatus.SUCCEEDED)
                    .build();
        }

        return  ChangePasswordResponse.builder()
                .changePasswordStatus(ChangePasswordStatus.FAILED)
                .build();
    }

    @Override
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {

        log.info("Forgot password processs begins");

        Optional<User> user = userRepository.findByUsername(forgotPasswordRequest.getUsername());

        if(user.isPresent()){

            validateEmail(user.get(), forgotPasswordRequest.getEmail());

            String alphanumeric = UUID.randomUUID().toString();

            saveBcryptedPassword(alphanumeric, user.get());

            mailService.sendMessage(mailSender, forgotPasswordRequest.getEmail(), "New Password",
                    emailText(alphanumeric));

            log.info("Forgot password processs completed");

            return ForgotPasswordResponse.builder()
                    .forgotPasswordStatus(ForgotPasswordStatus.USERNAME_VERIFIED)
                    .resetKeyPassword(alphanumeric)
                    .build();
        }

        return ForgotPasswordResponse.builder()
                .forgotPasswordStatus(ForgotPasswordStatus.USERNAME_NOT_VERIFIED)
                .build();
    }

    private void authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){

        try {

            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (BadCredentialsException e){

            throw new AuthenticationFailedException("Incorrect username or password", e);
        }
    }

    private void saveBcryptedPassword(String password, User user){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    private String emailText(String password){

        StringBuilder sb = new StringBuilder();

        sb.append("Your new password is ");
        sb.append(password + ". ");
        sb.append("After succesful login, you can change your password as you prefer.");

        return sb.toString();
    }

    private boolean validateEmail(User user, String email){

        Student student = studentRepository.findStudentByUserId(user.getId());

        System.out.println("----------------------dhjlvhfjvddjlvdlvdl "+student.getEmail());

        return false;
    }
}
