package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.dto.UserDto;
import gr.pada.bolosis.students_cv.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConversionService conversionService;

    @Override
    public void register(UserDto userDto) {

        log.info("Registration processs begins");

        userRepository.save(conversionService.convert(userDto, User.class));

        log.info("Registration processs completed");
    }

}
