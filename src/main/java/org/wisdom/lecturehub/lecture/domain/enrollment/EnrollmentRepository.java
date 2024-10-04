package org.wisdom.lecturehub.lecture.domain.enrollment;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository {
    void save(EnrollmentEntity enrollment);
    List<EnrollmentEntity> findByUserId(int userId);
    Optional<EnrollmentEntity> findByUserIdAndLectureDetailId(int userId, int lectureDetailId);
}
