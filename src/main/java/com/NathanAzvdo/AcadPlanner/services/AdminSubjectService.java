package com.NathanAzvdo.AcadPlanner.services;

import com.NathanAzvdo.AcadPlanner.entities.Subject;
import com.NathanAzvdo.AcadPlanner.exceptions.BusinessException;
import com.NathanAzvdo.AcadPlanner.exceptions.EmptyListException;
import com.NathanAzvdo.AcadPlanner.exceptions.InvalidIdException;
import com.NathanAzvdo.AcadPlanner.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSubjectService {

    private final SubjectRepository subjectRepository;

    public AdminSubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject save(Subject newSubject) {
            if (newSubject.getName() == null || newSubject.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("O name da matéria não pode ser nulo ou vazio");
            }

            if (newSubject.getName().length() > 100) {
                throw new IllegalArgumentException("O name da matéria não pode exceder 100 caracteres");
            }
            if (newSubject.getCredits() <= 0) {
                throw new IllegalArgumentException("O número de créditos deve ser maior que zero");
            }
            if (newSubject.getCourses() == null || newSubject.getCourses().isEmpty()) {
                throw new IllegalArgumentException("A matéria deve estar associada name pelo menos um course");
            }
            return subjectRepository.save(newSubject);
    }
    public void deleteById(Long id) {
        try {
            if (!subjectRepository.existsById(id)) {
                throw new InvalidIdException("Matéria não encontrada para o ID: " + id);
            }
            subjectRepository.deleteById(id);
        }catch (BusinessException e){
                throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public List<Subject> findAll() {
        try {
            List<Subject> subjects = subjectRepository.findAll();
            if (subjects.isEmpty()) {
                throw new EmptyListException("Nenhuma matéria encontrada.");
            }
            return subjects;
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public Subject updateSubject(Subject subject, Long id) {
        try {
            Subject currentSubject = subjectRepository.findById(id)
                    .orElseThrow(() -> new InvalidIdException("Matéria não encontrada para o ID: " + id));

            updateValidFields(currentSubject, subject);

            return subjectRepository.save(currentSubject);
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public Subject addPreRequisite(Long subjectId, Long preRequisiteId) {
        try {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new RuntimeException("Matéria não encontrada para o ID: " + subjectId));

            Subject preRequisito = subjectRepository.findById(preRequisiteId)
                    .orElseThrow(() -> new RuntimeException("Pré-requisito não encontrado para o ID: " + preRequisiteId));

            subject.getPrerequisites().add(preRequisito);
            return subjectRepository.save(subject);
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    public Subject removePreRequisito(Long subjectId, Long preRequisiteId) {
        try {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new RuntimeException("Matéria não encontrada para o ID: " + subjectId));

            Subject preRequisite = subjectRepository.findById(preRequisiteId)
                    .orElseThrow(() -> new RuntimeException("Pré-requisito não encontrado para o ID: " + preRequisiteId));

            subject.getPrerequisites().remove(preRequisite);
            return subjectRepository.save(subject);
        }catch (BusinessException e){
            throw new BusinessException("Houve um erro, tente mais tarde.");
        }
    }

    private void updateValidFields(Subject currentSubject, Subject newSubject) {
        currentSubject.setName(newSubject.getName() != null ? newSubject.getName() : currentSubject.getName());
        currentSubject.setDescription(newSubject.getDescription() != null ? newSubject.getDescription() : currentSubject.getDescription());
        currentSubject.setCredits(newSubject.getCredits() != 0 ? newSubject.getCredits() : currentSubject.getCredits());
        currentSubject.setCourses(!newSubject.getCourses().isEmpty() ? newSubject.getCourses() : currentSubject.getCourses());
    }
}
