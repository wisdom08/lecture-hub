package org.wisdom.lecturehub.lecture.infra.enrollment;

import org.springframework.stereotype.Repository;
import org.wisdom.lecturehub.lecture.domain.enrollment.EnrollmentEntity;
import org.wisdom.lecturehub.lecture.domain.enrollment.EnrollmentRepository;

import java.util.List;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    private final EnrollmentJpaRepository repository;


    public EnrollmentRepositoryImpl(EnrollmentJpaRepository enrollmentJpaRepository) {
        this.repository = enrollmentJpaRepository;
    }

    @Override
    public void save(EnrollmentEntity enrollment) {
        repository.save(enrollment);
    }

    @Override
    public List<EnrollmentEntity> findByUserId(int userId) {
        return repository.findAllByUserIdWithInstructor(userId);
    }
}
