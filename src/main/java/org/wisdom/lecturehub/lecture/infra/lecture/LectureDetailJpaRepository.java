package org.wisdom.lecturehub.lecture.infra.lecture;


import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailEntity;

import java.time.LocalDate;
import java.util.List;

public interface LectureDetailJpaRepository extends JpaRepository<LectureDetailEntity, Integer> {

    @EntityGraph(attributePaths = {"lecture", "lecture.instructor"})
    List<LectureDetailEntity> findByLectureDateAfter(LocalDate now);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"lecture"})
    LectureDetailEntity findById(int lectureDetailId);

    @Transactional
    @Modifying
    @Query("UPDATE lecture_detail ld SET ld.currentCapacity = ld.currentCapacity-1 WHERE ld.id = :lectureDetailId")
    void decrementCapacity(int lectureDetailId);
}
