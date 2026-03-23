package com.NathanAzvdo.AcadPlanner.repositories;

import com.NathanAzvdo.AcadPlanner.entities.CompletedSubject;
import com.NathanAzvdo.AcadPlanner.entities.CompletedSubjectsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompletedSubjectRepository extends JpaRepository<CompletedSubject, CompletedSubjectsId> {
    List<CompletedSubject> findByUserId(Long userId);

    boolean existsByCompletedSubjectsId_UserIdAndCompletedSubjectsId_SubjectId(Long id, Long id1);
}
