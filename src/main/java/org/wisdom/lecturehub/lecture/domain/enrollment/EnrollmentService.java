package org.wisdom.lecturehub.lecture.domain.enrollment;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailEntity;
import org.wisdom.lecturehub.lecture.presentation.ApiDto;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }


    @Transactional
    public void apply(int userId, LectureDetailEntity lectureDetail) {
        val enrollmentEntity = lectureDetail.toEnrollmentEntity(userId, lectureDetail);
        enrollmentRepository.save(enrollmentEntity);
    }

    @Transactional(readOnly = true)
    public List<ApiDto.response> getAppliedLecturesBy(int userId) {
        val enrollments = enrollmentRepository.findByUserId(userId);
        return enrollments.stream().map(EnrollmentEntity::toApiResponse).toList();
    }

    @Transactional(readOnly = true)
    public void validateAlreadyApplied(int userId, int lectureDetailId) {
        enrollmentRepository.findByUserIdAndLectureDetailId(userId, lectureDetailId)
            .ifPresent(enrollmentEntity -> {
                throw new IllegalArgumentException("이미 신청한 특강입니다");
            }
        );
    }
}
