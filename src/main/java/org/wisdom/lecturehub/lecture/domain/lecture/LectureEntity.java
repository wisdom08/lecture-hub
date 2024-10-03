package org.wisdom.lecturehub.lecture.domain.lecture;

import jakarta.persistence.*;
import lombok.Getter;
import org.wisdom.lecturehub.lecture.infra.config.BaseTimeEntity;

@Getter
@Entity
@Table(name = "lecture")
public class LectureEntity extends BaseTimeEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @JoinColumn(name = "instructor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private InstructorEntity instructor;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int capacity;
}

