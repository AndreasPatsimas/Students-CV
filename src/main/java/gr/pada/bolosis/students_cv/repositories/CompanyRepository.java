package gr.pada.bolosis.students_cv.repositories;

import gr.pada.bolosis.students_cv.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
