package org.wisdom.lecturehub.lecture.infra.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wisdom.lecturehub.lecture.domain.enrollment.EnrollmentEntity;

import java.util.List;

public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentEntity, Integer> {

    @Query("SELECT e FROM EnrollmentEntity e JOIN FETCH e.lectureDetail ld JOIN FETCH ld.lecture l JOIN FETCH l.instructor WHERE e.userId = :userId")
    List<EnrollmentEntity> findAllByUserIdWithInstructor(@Param("userId") int userId);
}
