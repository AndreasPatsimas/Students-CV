package gr.pada.bolosis.students_cv.services;

import gr.pada.bolosis.students_cv.domain.User;
import gr.pada.bolosis.students_cv.enums.AuthorityType;
import gr.pada.bolosis.students_cv.repositories.UserRepository;
import gr.pada.bolosis.students_cv.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void deleteUser(String username) throws IOException {

        log.info("Delete profile for user {}  process begins", username);

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){

            System.out.println(user.get().getAuthorities().get(0).getDescription());

            if(user.get().getAuthorities().get(0).getDescription().equals(AuthorityType.ROLE_STUDENT.description()))
                MyFileUtils.deleteDirectory(new File("C:/Student-CV/students/" + username));

            userRepository.deleteById(user.get().getId());

            log.info("Delete profile process completed");
        }
    }
}
