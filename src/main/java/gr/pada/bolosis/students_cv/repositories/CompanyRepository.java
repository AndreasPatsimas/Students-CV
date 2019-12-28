package gr.pada.bolosis.students_cv.repositories;

import gr.pada.bolosis.students_cv.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findCompanyByUserId(Long userId);
}
