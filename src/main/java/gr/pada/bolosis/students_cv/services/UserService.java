package gr.pada.bolosis.students_cv.services;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface UserService {

    void deleteUser(String username) throws IOException;
}
