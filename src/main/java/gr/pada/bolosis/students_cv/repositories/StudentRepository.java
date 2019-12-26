package gr.pada.bolosis.students_cv.repositories;

import gr.pada.bolosis.students_cv.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
