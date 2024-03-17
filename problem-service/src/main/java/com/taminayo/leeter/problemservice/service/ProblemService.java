package com.taminayo.leeter.problemservice.service;

import com.taminayo.leeter.problemservice.dto.ProblemRequest;
import com.taminayo.leeter.problemservice.dto.ProblemResponse;
import com.taminayo.leeter.problemservice.model.Problem;
import com.taminayo.leeter.problemservice.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ResponseEntity<String> createProblem(List<ProblemRequest> problemRequests) {

        for (ProblemRequest problemRequest : problemRequests) {
            var res = this.validateProblem(problemRequest);
            if (res.getStatusCode().equals(HttpStatus.BAD_REQUEST)) continue;
            Problem problem = Problem.builder()
                    .frontendQuestionId(problemRequest.getFrontendQuestionId())
                    .titleSlug(problemRequest.getTitleSlug())
                    .build();
            problemRepository.save(problem);
        }
        return new ResponseEntity<>("problems are saved", HttpStatus.CREATED);
    }

    public ResponseEntity<List<ProblemResponse>> getAllProblems() {
        List<Problem> problems = problemRepository.findAll();
        return new ResponseEntity<>(problems.stream()
                .map(this::mapToProblemResponse)
                .toList(), HttpStatus.OK);
    }

    public ResponseEntity<ProblemResponse> getProblemById(int frontendQuestionId) {
        Optional<Problem> problem = problemRepository.findByFrontendQuestionId(frontendQuestionId);
        if (problem.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(mapToProblemResponse(problem.get()), HttpStatus.OK);
    }

    private ProblemResponse mapToProblemResponse(Problem problem) {
        return ProblemResponse.builder()
                .frontendQuestionId(problem.getFrontendQuestionId())
                .titleSlug(problem.getTitleSlug())
                .build();
    }

    private ResponseEntity<String> validateProblem(ProblemRequest problemRequest) {
        Optional<Problem> prob = problemRepository.findById(problemRequest.getFrontendQuestionId());
        return prob.isPresent() ? new ResponseEntity<>("already exists", HttpStatus.BAD_REQUEST) : new ResponseEntity<>("valid", HttpStatus.OK);
    }

    public ResponseEntity<List<ProblemResponse>> getProblemsByRequest(List<ProblemRequest> problemRequests) {
        List<ProblemResponse> problemResponses = new ArrayList<>();
        for (ProblemRequest problemRequest : problemRequests) {
            Problem problem = problemRepository.findByFrontendQuestionId(problemRequest.getFrontendQuestionId())
                    .orElse(null);
            if (problem == null) continue;
            problemResponses.add(mapToProblemResponse(problem));
        }
        return new ResponseEntity<>(problemResponses, HttpStatus.OK);
    }
}
