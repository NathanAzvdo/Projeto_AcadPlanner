package com.NathanAzvdo.AcadPlanner.repositories;

import com.NathanAzvdo.AcadPlanner.entities.InProgressSubjects;
import com.NathanAzvdo.AcadPlanner.entities.InProgressSubjectId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InProgressSubjectRepository extends JpaRepository<InProgressSubjects, InProgressSubjectId> {

    boolean existsByUserIdAndSubjectId(Long id, Long subjectId);

    List<InProgressSubjects> findByUserId(Long userId);

    @Modifying
    @Transactional
    void deleteByUserIdAndSubjectId(Long id, Long subjectId);
}
