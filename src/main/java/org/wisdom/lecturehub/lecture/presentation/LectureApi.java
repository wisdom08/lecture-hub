package org.wisdom.lecturehub.lecture.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wisdom.lecturehub.lecture.application.LectureFacade;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequestMapping("/lecture")
@RestController
public class LectureApi {

    private final LectureFacade lectureFacade;

    public LectureApi(LectureFacade lectureFacade) {
        this.lectureFacade = lectureFacade;
    }

    @PostMapping
    public void apply(@RequestBody ApiDto.Request applyRequest) {
        lectureFacade.apply(applyRequest.toLectureFacadeDto());
    }

    @GetMapping
    public ResponseEntity<Map<LocalDate, List<ApiDto.response>>> getAvailableLectures() {
        return ResponseEntity.ok(lectureFacade.getAvailableLectures());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ApiDto.response>> getAppliedLectures(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(lectureFacade.getAppliedLecturesBy(userId));
    }
}
