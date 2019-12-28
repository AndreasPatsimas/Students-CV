package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.dto.authenticate.*;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws Exception;

    ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest);

    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
}
