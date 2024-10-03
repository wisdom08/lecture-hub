package org.wisdom.lecturehub.lecture.infra.lecture;

import lombok.val;
import org.springframework.stereotype.Repository;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailEntity;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureDetailRepository;
import org.wisdom.lecturehub.lecture.domain.lecture.LectureInfo;

import java.time.LocalDate;
import java.util.List;

@Repository
public class LectureDetailRepositoryImpl implements LectureDetailRepository {

    private final LectureDetailJpaRepository repository;

    public LectureDetailRepositoryImpl(LectureDetailJpaRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<LectureInfo> findAllAvailableLectures() {
        val availableLectures = repository.findByLectureDateAfter(LocalDate.now());
        return availableLectures.stream().map(LectureDetailEntity::toLectureInto).toList();
    }

    @Override
    public LectureDetailEntity findLectureDetailBy(int lectureDetailId) {
        return repository.findById(lectureDetailId);
    }

    @Override
    public void decrementCapacity(int lectureDetailId) {
        repository.decrementCapacity(lectureDetailId);
    }
}
