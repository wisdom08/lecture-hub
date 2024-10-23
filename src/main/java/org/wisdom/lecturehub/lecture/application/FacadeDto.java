package org.wisdom.lecturehub.lecture.application;

import lombok.Builder;
import org.wisdom.lecturehub.lecture.presentation.ApiDto;

import java.time.LocalDate;

public record FacadeDto(){

    @Builder
    public record request (int userId, int lectureId, int lectureDetailId){}

    @Builder
    public record response(int lectureId, String title, String instructorName, LocalDate lectureDate){
        public ApiDto.response toApiResponse() {
            return ApiDto.response.builder()
                    .lectureId(lectureId)
                    .title(title)
                    .instructorName(instructorName)
                    .lectureDate(lectureDate)
                    .build();
        }
    }
}
