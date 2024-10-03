package org.wisdom.lecturehub.lecture.domain.lecture;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wisdom.lecturehub.lecture.application.FacadeDto;

import java.util.List;

@Service
public class LectureDetailService {

    private final LectureDetailRepository lectureDetailRepository;

    public LectureDetailService(LectureDetailRepository lectureDetailRepository) {
        this.lectureDetailRepository = lectureDetailRepository;
    }

    @Transactional(readOnly = true)
    public List<FacadeDto.response> getAvailableLectures() {
        val availableLectures = lectureDetailRepository.findAllAvailableLectures();
        return availableLectures.stream().map(LectureInfo::toFacadeDto).toList();
    }

    @Transactional(readOnly = true)
    public LectureDetailEntity getLecturesDetailBy(int lectureDetailId) {
        return lectureDetailRepository.findLectureDetailBy(lectureDetailId);
    }

    @Transactional
    public LectureDetailEntity decrementCapacity(LectureDetailEntity lectureDetail) {
        LectureDetailEntity.validCapacity(lectureDetail.getCurrentCapacity());
        lectureDetailRepository.decrementCapacity(lectureDetail.getId());
        return lectureDetail.toLectureDetailDecremented();
    }

}
