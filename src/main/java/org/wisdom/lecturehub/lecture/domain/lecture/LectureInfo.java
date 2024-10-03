package org.wisdom.lecturehub.lecture.domain.lecture;

import lombok.Builder;
import org.wisdom.lecturehub.lecture.application.FacadeDto;

import java.time.LocalDate;

@Builder
public record LectureInfo (int lectureId, String title, String instructorName, LocalDate lectureDate){

    public FacadeDto.response toFacadeDto() {
        return FacadeDto.response.builder()
                .lectureId(lectureId)
                .title(title)
                .instructorName(instructorName)
                .lectureDate(lectureDate)
                .build();
    }
}
