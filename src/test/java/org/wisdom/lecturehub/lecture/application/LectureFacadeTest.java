package org.wisdom.lecturehub.lecture.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wisdom.lecturehub.lecture.domain.enrollment.EnrollmentService;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailEntity;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailService;
import org.wisdom.lecturehub.lecture.presentation.ApiDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LectureFacadeTest {


    @InjectMocks
    private LectureFacade lectureFacade;

    @Mock
    private LectureDetailService lectureDetailService;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private ApiDto.response apiResponse;

    @Mock
    private FacadeDto.response facadeResponse;

    @Test
    void 특강_신청이_성공한다() {
        // given
        int userId = 1;
        int lectureDetailId = 10;
        LectureDetailEntity lectureDetailEntity = new LectureDetailEntity();
        when(lectureDetailService.getLecturesDetailBy(lectureDetailId)).thenReturn(new LectureDetailEntity());

        // when
        lectureFacade.apply(FacadeDto.request.builder()
                .userId(userId)
                .lectureDetailId(lectureDetailId)
                .build());

        // then
        verify(lectureDetailService, only()).getLecturesDetailBy(lectureDetailId);
        verify(enrollmentService, only()).apply(userId, lectureDetailEntity);
    }

    @Test
    void 신청_가능한_특강을_조회한다() {
        // given
        LocalDate lectureDate = LocalDate.of(2024, 10, 4);
        when(facadeResponse.toApiResponse()).thenReturn(apiResponse);
        when(facadeResponse.lectureDate()).thenReturn(lectureDate);
        when(lectureDetailService.getAvailableLectures()).thenReturn(List.of(facadeResponse));

        // when
        Map<LocalDate, List<ApiDto.response>> result = lectureFacade.getAvailableLectures();

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(lectureDate)).containsExactly(apiResponse);
        verify(lectureDetailService, only()).getAvailableLectures();
    }

    @Test
    void 신청한_특강을_조회한다() {
        // given
        int userId = 1;
        when(enrollmentService.getAppliedLecturesBy(userId)).thenReturn(List.of(apiResponse));

        // When
        List<ApiDto.response> result = lectureFacade.getAppliedLecturesBy(userId);

        // Then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst()).isEqualTo(apiResponse);
        verify(enrollmentService, times(1)).getAppliedLecturesBy(userId);
    }

    @Test
    void 신청한_특강이_없을때는_빈_배열을_반환한다() {
        // given
        int userId = 1;

        // when
        when(enrollmentService.getAppliedLecturesBy(userId)).thenReturn(Collections.emptyList());
        List<ApiDto.response> result = lectureFacade.getAppliedLecturesBy(userId);

        // then
        assertThat(result.size()).isEqualTo(0);
        verify(enrollmentService, times(1)).getAppliedLecturesBy(userId);
    }

}