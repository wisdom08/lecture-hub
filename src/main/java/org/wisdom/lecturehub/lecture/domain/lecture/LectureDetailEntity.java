package org.wisdom.lecturehub.lecture.domain.lecture;

import jakarta.persistence.*;
import lombok.Getter;
import org.wisdom.lecturehub.lecture.domain.enrollment.EnrollmentEntity;
import org.wisdom.lecturehub.lecture.infra.config.BaseTimeEntity;

import java.time.LocalDate;

@Getter
@Entity(name = "lecture_detail")
@Table
public class LectureDetailEntity extends BaseTimeEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;


    @JoinColumn(name = "lecture_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LectureEntity lecture;

    private LocalDate lectureDate;

    public LectureInfo toLectureInto() {
        return LectureInfo.builder()
                .lectureId(lecture.getId())
                .title(lecture.getTitle())
                .instructorName(lecture.getInstructor().getName())
                .lectureDate(lectureDate)
                .build();
    }

    public EnrollmentEntity toEnrollmentEntity(int userId, LectureDetailEntity lectureDetailEntity) {
        return EnrollmentEntity.builder()
                .userId(userId)
                .lectureDetail(lectureDetailEntity)
                .build();
    }
}
