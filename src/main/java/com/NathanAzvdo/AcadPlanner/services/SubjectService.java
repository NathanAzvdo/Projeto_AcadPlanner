package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.*;
import com.NathanAzvdo.AcadPlanner.exceptions.BusinessException;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.CompletedSubjectRepository;
import com.NathanAzvdo.AcadPlanner.repositories.InProgressSubjectRepository;
import com.NathanAzvdo.AcadPlanner.repositories.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final CompletedSubjectRepository completedSubjectsRepository;

    private final InProgressSubjectRepository inProgressSubjectRepository;

    public SubjectService(SubjectRepository subjectRepository, CompletedSubjectRepository completedSubjectsRepository
            , InProgressSubjectRepository inProgressSubjectRepository) {
        this.subjectRepository = subjectRepository;
        this.completedSubjectsRepository = completedSubjectsRepository;
        this.inProgressSubjectRepository = inProgressSubjectRepository;
    }

    public void newSubjectInProgress(Long subjectId, Long userId) {

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() ->
                new InvalidIdException("Matéria não encontrada com Id: " + subjectId));

        if (verifyCurrentSubject(userId, subjectId)) {
            throw new BusinessException("Você já está cursando essa matéria " + subject.getName() + ".");
        }

        for (Subject prerequisite : subject.getPrerequisites()) {
            boolean conclusion = completedSubjectsRepository
                    .existsByCompletedSubjectsId_UserIdAndCompletedSubjectsId_SubjectId(userId, prerequisite.getId());
            if (!conclusion) {
                throw new BusinessException("Você precisa concluir " + prerequisite.getName() +
                        " antes de cursar " + subject.getName() + ".");
            }
        }

        InProgressSubjects inProgressSubjects = new InProgressSubjects();
        inProgressSubjects.setUserId(userId);
        inProgressSubjects.setSubjectId(subject.getId());
        inProgressSubjects.setStartDate(LocalDate.now());

        inProgressSubjectRepository.save(inProgressSubjects);
    }

    @Transactional
    public void completeSubject(Long subjectId, User user) {
        if (subjectId == null) {
            throw new InvalidIdException("ID da matéria não pode ser nulo");
        }

        Long userId = user.getId();

        if (userId == null) {
            throw new BusinessException("Não foi possível identificar o usuário");
        }

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() ->
                new InvalidIdException("Matéria não encontrada com Id: " + subjectId));

        if (!verifyCurrentSubject(userId, subjectId)) {
            throw new BusinessException("Você não está cursando essa matéria " + subject.getName() + ".");
        }

        CompletedSubjectsId completedSubjectsId = new CompletedSubjectsId(userId, subjectId);

        CompletedSubject completedSubject = new CompletedSubject();
        completedSubject.setCompletedSubjectsId(completedSubjectsId);
        completedSubject.setUser(user);
        completedSubject.setSubject(subject);
        completedSubject.setCompletionDate(LocalDate.now());

        inProgressSubjectRepository.deleteByUserIdAndSubjectId(userId, subjectId);
        completedSubjectsRepository.save(completedSubject);
    }

    public List<Subject> findSubjectsByCurso(Long cursoId) {
        List<Subject> subjects = subjectRepository.findByCoursesId(cursoId);
        if (subjects.isEmpty()) {
           throw new EmptyListException("Nenhuma matéria encontrada.");
        }
        return subjects;

    }

    public List<CompletedSubject> completedSubjects(Long userId) {
            List<CompletedSubject> completedSubject = completedSubjectsRepository.findByUserId(userId);
            if (completedSubject.isEmpty()) {
                throw new EmptyListException("Nenhuma matéria concluída encontrada.");
            }
            return completedSubject;
    }

    public List<Subject> inProgressSubject(Long userId) {
            List<InProgressSubjects> inProgressSubjects = inProgressSubjectRepository.findByUserId(userId);
            if (inProgressSubjects.isEmpty()) {
                throw new EmptyListException("Nenhuma matéria em andamento encontrada.");
            }
            List<Long> subjectsId = inProgressSubjects.stream()
                    .map(InProgressSubjects::getSubjectId)
                    .toList();
            return subjectRepository.findAllById(subjectsId);


    }

    public List<Subject> findAvailableSubjects(User user) {
            Long userId = user.getId();
            Long courseId = user.getCourse().getId();

            List<Subject> subjectsByCourse = subjectRepository.findByCoursesId(courseId);

            Set<Long> completedIds = completedSubjectsRepository.findByUserId(userId).stream()
                    .map(completedSubject -> completedSubject.getCompletedSubjectsId().getSubjectId())
                    .collect(Collectors.toSet());

            Set<Long> inProgressIds = inProgressSubjectRepository.findByUserId(userId).stream()
                    .map(InProgressSubjects::getSubjectId)
                    .collect(Collectors.toSet());

            List<Subject> availables = subjectsByCourse.stream()
                    .filter(subject -> !completedIds.contains(subject.getId()))
                    .filter(subject -> !inProgressIds.contains(subject.getId()))
                    .filter(subject -> {
                        Set<Long> preRequisitosIds = subject.getPrerequisites().stream()
                                .map(Subject::getId)
                                .collect(Collectors.toSet());

                        if (preRequisitosIds.isEmpty()) {
                            return true;
                        }

                        return completedIds.containsAll(preRequisitosIds);
                    })
                    .toList();

            if (availables.isEmpty()) {
                throw new EmptyListException("Nenhuma matéria disponível para cursar no momento.");
            }

            return availables;


    }


    public Subject findById(Long id) {
            return subjectRepository.findById(id)
                    .orElseThrow(() -> new InvalidIdException("Matéria não encontrada para o ID: " + id));

    }

    public List<Subject> getPreRequisitos(Long subjectId) {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new RuntimeException("Matéria não encontrada"));

            if (subject.getPrerequisites().isEmpty()) {
                throw new EmptyListException("Nenhum pré-requisito encontrado para name matéria. ");
            }
            return subject.getPrerequisites();
    }

    private boolean verifyCurrentSubject(Long id, Long subjectId){
            boolean inProgressSubject = inProgressSubjectRepository.existsByUserIdAndSubjectId(id, subjectId);
            if (inProgressSubject) {
                return true;
            }
            return false;
    }
}