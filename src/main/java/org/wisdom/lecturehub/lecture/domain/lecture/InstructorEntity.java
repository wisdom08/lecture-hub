package org.wisdom.lecturehub.lecture.domain.lecture;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "instructor")
@Entity
public class InstructorEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(nullable = false)
    private String name;

    private String description;
}
