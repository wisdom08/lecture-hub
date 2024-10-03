package org.wisdom.lecturehub.lecture.presentation;

import lombok.Builder;
import org.wisdom.lecturehub.lecture.application.FacadeDto;

import java.time.LocalDate;

public record ApiDto(){

    public record Request(int userId, int lectureDetailId){
        public FacadeDto.request toLectureFacadeDto() {
            return FacadeDto.request.builder()
                    .userId(userId)
                    .lectureDetailId(lectureDetailId)
                    .build();
        }

    }

    @Builder
    public record response(int lectureId, String title, String instructorName, LocalDate lectureDate) {
    }
}
