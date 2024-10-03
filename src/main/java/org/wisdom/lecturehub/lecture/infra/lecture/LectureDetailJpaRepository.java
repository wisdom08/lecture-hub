package org.wisdom.lecturehub.lecture.infra.lecture;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailEntity;

import java.time.LocalDate;
import java.util.List;

public interface LectureDetailJpaRepository extends JpaRepository<LectureDetailEntity, Integer> {

    @EntityGraph(attributePaths = {"lecture", "lecture.instructor"})
    List<LectureDetailEntity> findByLectureDateAfter(LocalDate now);
}
