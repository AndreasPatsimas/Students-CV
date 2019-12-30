package gr.pada.bolosis.students_cv.repositories;

import gr.pada.bolosis.students_cv.domain.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {

    @Transactional
    @Modifying
    void deleteCvByStudentId(Long studentId);
}
