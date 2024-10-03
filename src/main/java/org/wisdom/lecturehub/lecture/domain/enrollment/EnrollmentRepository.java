package org.wisdom.lecturehub.lecture.domain.enrollment;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository {
    void save(EnrollmentEntity enrollment);
    List<EnrollmentEntity> findByUserId(int userId);
}
