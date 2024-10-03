package org.wisdom.lecturehub.lecture.infra.lecture;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailEntity;

import java.time.LocalDate;
import java.util.List;

public interface LectureDetailJpaRepository extends JpaRepository<LectureDetailEntity, Integer> {

    @EntityGraph(attributePaths = {"lecture", "lecture.instructor"})
    List<LectureDetailEntity> findByLectureDateAfter(LocalDate now);

    @EntityGraph(attributePaths = {"lecture"})
    LectureDetailEntity findById(int lectureDetailId);

    @Transactional
    @Modifying
    @Query("UPDATE lecture_detail ld SET ld.currentCapacity = ld.currentCapacity-1 WHERE ld.id = :lectureDetailId")
    int decrementCapacity(int lectureDetailId);
}
