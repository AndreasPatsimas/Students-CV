package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

    void register(UserDto userDto);
}
