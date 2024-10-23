package org.wisdom.lecturehub.lecture.domain.enrollment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailEntity;
import org.wisdom.lecturehub.lecture.infra.config.BaseTimeEntity;
import org.wisdom.lecturehub.lecture.presentation.ApiDto;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "enrollment", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "lecture_detail_id"})})
@Entity
public class EnrollmentEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(nullable = false)
    private int userId;

    @JoinColumn(name = "lecture_detail_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LectureDetailEntity lectureDetail;

    public ApiDto.response toApiResponse() {
        return ApiDto.response.builder()
                .lectureId(lectureDetail.getLecture().getId())
                .title(lectureDetail.getLecture().getTitle())
                .instructorName(lectureDetail.getLecture().getInstructor().getName())
                .lectureDate(lectureDetail.getLectureDate())
                .build();
    }
}
