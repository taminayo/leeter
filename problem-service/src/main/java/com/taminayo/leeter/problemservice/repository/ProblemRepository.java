package com.taminayo.leeter.problemservice.repository;

import com.taminayo.leeter.problemservice.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    Optional<Problem> findByFrontendQuestionId(Integer frontendQuestionId);
    Optional<Problem> findByTitleSlug(String titleSlug);
}
