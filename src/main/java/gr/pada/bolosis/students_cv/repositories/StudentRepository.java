package gr.pada.bolosis.students_cv.repositories;

import gr.pada.bolosis.students_cv.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM students WHERE user_id = :userId ",
            nativeQuery = true)
    Student findStudentByUserId(@Param("userId") Long userId);
}
