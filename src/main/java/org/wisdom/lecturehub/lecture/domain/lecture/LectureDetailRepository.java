package org.wisdom.lecturehub.lecture.domain.lecture;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureDetailRepository {

    List<LectureInfo> findAllAvailableLectures();

    LectureDetailEntity findBy(int lectureDetailId);
}
