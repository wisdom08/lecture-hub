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
        enrollmentRepository.save(lectureDetail.toEnrollmentEntity(userId, lectureDetail));
    }

    @Transactional(readOnly = true)
    public List<ApiDto.response> getAppliedLecturesBy(int userId) {
        val enrollments = enrollmentRepository.findByUserId(userId);
        return enrollments.stream().map(EnrollmentEntity::toApiResponse).toList();
    }
}
