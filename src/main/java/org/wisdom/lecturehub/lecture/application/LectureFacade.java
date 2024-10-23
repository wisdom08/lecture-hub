package org.wisdom.lecturehub.lecture.application;

import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wisdom.lecturehub.lecture.domain.enrollment.EnrollmentService;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailEntity;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailService;
import org.wisdom.lecturehub.lecture.presentation.ApiDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Component
public class LectureFacade {

    private final LectureDetailService lectureDetailService;
    private final EnrollmentService enrollmentService;

    public LectureFacade(LectureDetailService lectureDetailService, EnrollmentService enrollmentService) {
        this.lectureDetailService = lectureDetailService;
        this.enrollmentService = enrollmentService;
    }

    @Transactional
    public void apply(FacadeDto.request lectureDto) {
        enrollmentService.validateAlreadyApplied(lectureDto.userId(), lectureDto.lectureDetailId());
        val lectureDetail = lectureDetailService.getLecturesDetailBy(lectureDto.lectureDetailId());
        LectureDetailEntity updateLectureDetail = lectureDetailService.decrementCapacity(lectureDetail);
        enrollmentService.apply(lectureDto.userId(), updateLectureDetail);
    }

    public Map<LocalDate, List<ApiDto.response>> getAvailableLectures() {
        val availableLectures = lectureDetailService.getAvailableLectures();
        return availableLectures.stream().collect(
                groupingBy(FacadeDto.response::lectureDate,
                mapping(FacadeDto.response::toApiResponse, toList()))
        );
    }

    public List<ApiDto.response> getAppliedLecturesBy(int userId) {
        return enrollmentService.getAppliedLecturesBy(userId);
    }
}
