package org.wisdom.lecturehub.lecture.domain.lecture;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.wisdom.lecturehub.lecture.domain.enrollment.EnrollmentEntity;
import org.wisdom.lecturehub.lecture.infra.config.BaseTimeEntity;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private int currentCapacity;

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

    public static void validCapacity(int currentCapacity) {
        if (currentCapacity <= 0) {
            throw new IllegalArgumentException("특강 신청 가능한 자리가 없습니다");
        }
    }

    public LectureDetailEntity toLectureDetailDecremented() {
        return LectureDetailEntity.builder()
                .id(id)
                .currentCapacity(currentCapacity - 1)
                .lecture(lecture)
                .lectureDate(lectureDate)
                .build();
    }

}
